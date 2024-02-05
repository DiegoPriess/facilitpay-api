package com.facilitipay.api.repositories;

import com.facilitipay.api.models.IndividualPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualPaymentRepository extends JpaRepository<IndividualPayment, Long> {}
