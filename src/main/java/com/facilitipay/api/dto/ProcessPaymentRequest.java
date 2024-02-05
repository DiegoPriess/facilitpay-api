package com.facilitipay.api.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class ProcessPaymentRequest {
    private List<AddictionRequest> addictionsList;
    private List<DiscountRequest> discountsList;
    private List<IndividualPaymentRequest> individualPaymentsList;
    private BigDecimal paymentPrice;
}
