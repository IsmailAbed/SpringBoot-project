package com.springproject.customerservice.repository;

import com.springproject.customerservice.entity.UserEntity;
import com.springproject.customerservice.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {//long type of primary key

    Optional<UserEntity> findFirstByEmail(String email);

    UserEntity findByUserRole(UserRole userRole);
}
