package com.facilitipay.api.services;

import com.facilitipay.api.dto.AddictionRequest;
import com.facilitipay.api.dto.DiscountRequest;
import com.facilitipay.api.dto.IndividualPaymentRequest;
import com.facilitipay.api.dto.ProcessPaymentRequest;
import com.facilitipay.api.enums.TypeValue;
import com.facilitipay.api.models.IndividualPayment;
import com.facilitipay.api.models.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessPaymentService {

    @Autowired
    public ProcessPaymentService(final IndividualPaymentService individualPaymentService, final PaymentService paymentService) {
        this.individualPaymentService = individualPaymentService;
        this.paymentService = paymentService;
    }

    private final IndividualPaymentService individualPaymentService;
    private final PaymentService paymentService;

    public void processPayment(final ProcessPaymentRequest request) {
        final Payment payment = Payment.builder().individualPaymentsList(new ArrayList<>()).build();

        request.getIndividualPaymentsList().forEach(individualPayment -> {
            payment.getIndividualPaymentsList().add(createIndividualPayments(individualPayment, request));
        });

        this.paymentService.create(payment);
    }

    public IndividualPayment createIndividualPayments(final IndividualPaymentRequest individualPayment, final ProcessPaymentRequest request) {
        final BigDecimal participation = this.calculateParticipationPercentage(request.getPaymentPrice(), individualPayment.getPrice());
        final BigDecimal calculatedDiscount = this.calculateDiscount(participation, request.getDiscountsList(), request.getPaymentPrice());
        final BigDecimal calculatedAddiction = this.calculateAddiction(participation, request.getAddictionsList(), request.getPaymentPrice());

        return this.individualPaymentService.create(IndividualPayment.builder()
                                                                     .price(calculateTotalPrice(individualPayment.getPrice(),
                                                                                                calculatedAddiction,
                                                                                                calculatedDiscount))
                                                                     .idUser(individualPayment.getIdUser())
                                                                     .isUserResponsible(individualPayment.getIsUserResponsible())
                                                                     .build());
    }

    private BigDecimal calculateTotalPrice(final BigDecimal individualPaymentPrice, final BigDecimal calculatedAddiction, final BigDecimal calculatedDiscount) {
        return individualPaymentPrice.add(calculatedAddiction).subtract(calculatedDiscount);
    }

    private BigDecimal calculateDiscount(final BigDecimal participation,
                                         final List<DiscountRequest> discountsList,
                                         final BigDecimal paymentPrice) {

        BigDecimal[] totalDiscount = {BigDecimal.ZERO};

        discountsList.forEach(discount -> {
            totalDiscount[0] = totalDiscount[0].add(calculateDetails(participation, paymentPrice, discount.getValue(), discount.getType()));
        });

        return totalDiscount[0];
    }

    private BigDecimal calculateAddiction(final BigDecimal participation,
                                          final List<AddictionRequest> addictionList,
                                          final BigDecimal paymentPrice) {

        BigDecimal[] totalDiscount = {BigDecimal.ZERO};

        addictionList.forEach(discount -> {
            totalDiscount[0] = totalDiscount[0].add(this.calculateDetails(participation, paymentPrice, discount.getValue(), discount.getType()));
        });

        return totalDiscount[0];
    }

    public BigDecimal calculateDetails(final BigDecimal participation, final BigDecimal paymentPrice,
                                       final BigDecimal value, final TypeValue type) {

        if (type.equals(TypeValue.PERCENTAGE)) {
            final BigDecimal generalValue = value.multiply(paymentPrice).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
            return generalValue.multiply(participation);
        } else {
            return value.multiply(participation);
        }
    }

    public BigDecimal calculateParticipationPercentage(final BigDecimal paymentPrice, final BigDecimal individualPayment) {
        return individualPayment.multiply(BigDecimal.valueOf(100))
                                .divide(paymentPrice, 2, RoundingMode.HALF_EVEN)
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
    }
}