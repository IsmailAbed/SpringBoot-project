package com.springproject.customerservice.repository;

import com.springproject.customerservice.dto.BookCarDto;
import com.springproject.customerservice.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookACarRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAllByUserEntityId(Long userId);

}
