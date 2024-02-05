package com.facilitipay.api.services;


import com.facilitipay.api.models.IndividualPayment;
import com.facilitipay.api.repositories.IndividualPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class IndividualPaymentService {

    @Autowired
    public IndividualPaymentService(final IndividualPaymentRepository repository) {
        this.repository = repository;
    }

    private final IndividualPaymentRepository repository;

    public IndividualPayment create(final IndividualPayment individualPayment) {
        return repository.save(individualPayment);
    }

    public IndividualPayment update(final IndividualPayment individualPayment) {
        Assert.notNull(individualPayment.getId(), "Id deve ser informado");
        Assert.isTrue(this.getById(individualPayment.getId()).isPresent(), "Pagamento individual não encontrado");
        return repository.save(individualPayment);
    }

    public Optional<IndividualPayment> getById(final Long id) {
        return repository.findById(id);
    }

    public Page<IndividualPayment> listPage(final Integer page, final Integer size) {
        return repository.findAll(PageRequest.of(page, size));
    }
}
