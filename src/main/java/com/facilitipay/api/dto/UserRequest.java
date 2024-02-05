package com.facilitipay.api.dto;

import lombok.Getter;

@Getter
public class UserRequest {
    private Long id;
    private String name;
    private String email;
    private String password;
}
