package com.springproject.customerservice.services.auth;

import com.springproject.customerservice.dto.SignupRequest;
import com.springproject.customerservice.dto.UserDto;

public interface AuthService {

     UserDto createCustomer(SignupRequest signupRequest);

     boolean hasCustomerWithEmail(String email);
}
