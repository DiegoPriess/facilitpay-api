package com.facilitipay.api.controllers;

import com.facilitipay.api.dto.ProcessPaymentRequest;
import com.facilitipay.api.services.ProcessPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process-payment")
public class ProcessPaymentController {

    @Autowired
    public ProcessPaymentController(final ProcessPaymentService service) {
        this.service = service;
    }

    private final ProcessPaymentService service;

    @PostMapping
    public void create(@RequestBody ProcessPaymentRequest request) {
       service.processPayment(request);
    }
}