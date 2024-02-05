package com.facilitipay.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "individual_payments")
public class IndividualPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="id_user")
    private Long idUser;

    @Column(name="is_user_responsible")
    private Boolean isUserResponsible;

    @Column(name="price")
    private BigDecimal price;
}
