package com.facilitipay.api.services;


import com.facilitipay.api.models.Payment;
import com.facilitipay.api.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    public PaymentService(final PaymentRepository repository) {
        this.repository = repository;
    }

    private final PaymentRepository repository;

    public Payment create(final Payment payment) {
        return repository.save(payment);
    }

    public Payment update(final Payment payment) {
        Assert.notNull(payment.getId(), "Id deve ser informado");
        Assert.isTrue(this.getById(payment.getId()).isPresent(), "Pagamento não encontrado");
        return repository.save(payment);
    }

    public Optional<Payment> getById(final Long id) {
        return repository.findById(id);
    }

    public Page<Payment> listPage(final Integer page, final Integer size) {
        return repository.findAll(PageRequest.of(page, size));
    }
}
