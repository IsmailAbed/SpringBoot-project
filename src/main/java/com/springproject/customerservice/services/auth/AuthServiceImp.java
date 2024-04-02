package com.springproject.customerservice.services.auth;

import com.springproject.customerservice.dto.SignupRequest;
import com.springproject.customerservice.dto.UserDto;
import com.springproject.customerservice.entity.UserEntity;
import com.springproject.customerservice.enums.UserRole;
import com.springproject.customerservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        UserEntity adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount==null){
            UserEntity newAdminAcc = new UserEntity();
            newAdminAcc.setName("admin");
            newAdminAcc.setEmail("admin@test.com");
            newAdminAcc.setPassword(new BCryptPasswordEncoder().encode("admin"));
            newAdminAcc.setUserRole(UserRole.ADMIN);


            userRepository.save(newAdminAcc);
            System.out.println("created successfully");
        }
    }

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(signupRequest.getName());
        userEntity.setEmail(signupRequest.getEmail());
        userEntity.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        userEntity.setUserRole(UserRole.CUSTOMER);
        UserEntity createUserEntity = userRepository.save(userEntity);


        UserDto userDto = new UserDto(); //UserDto is typically used to transfer data between different layers of an application
        // (e.g., from the backend to the frontend)
        userDto.setId(createUserEntity.getId());

        return userDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent(); // law m htyna is present knt hay boolean w hdik optinal  -> conflict
    }


}
