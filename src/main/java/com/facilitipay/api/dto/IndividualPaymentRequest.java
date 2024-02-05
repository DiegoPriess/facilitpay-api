package com.facilitipay.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualPaymentRequest {
    private Long idUser;
    private Boolean isUserResponsible;
    private BigDecimal price;
}
