package com.facilitipay.api.dto;

import com.facilitipay.api.enums.TypeValue;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddictionRequest {
    private BigDecimal value;
    private TypeValue type;
}
