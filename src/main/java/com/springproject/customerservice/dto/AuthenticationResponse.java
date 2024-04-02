package com.springproject.customerservice.dto;

import com.springproject.customerservice.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private UserRole userRole;
    private Long userId;
}
