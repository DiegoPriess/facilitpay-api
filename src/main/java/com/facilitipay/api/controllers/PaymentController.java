package com.facilitipay.api.controllers;

import com.facilitipay.api.models.Payment;
import com.facilitipay.api.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    public PaymentController(final PaymentService service) {
        this.service = service;
    }

    private final PaymentService service;

    @PutMapping
    public ResponseEntity<Payment> update(@RequestBody Payment user) {
        return ResponseEntity.ok(service.update(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.getById(id));
    }

    @GetMapping("/{page}/{size}")
    public Page<Payment> list(@PathVariable Integer page,
                              @PathVariable Integer size) {
        return service.listPage(page, size);
    }
}
