package com.springproject.customerservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springproject.customerservice.dto.BookCarDto;
import com.springproject.customerservice.enums.BookCarStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fromDate;

    private Date toDate;

    private Long days;

    private Long price;

    private BookCarStatus bookCarStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserEntity userEntity;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CarEntity carEntity;


    //later

    public BookCarDto getBookACarDto(){
        BookCarDto bookCarDto = new BookCarDto();
        bookCarDto.setId(id);
        bookCarDto.setDays(days);
        bookCarDto.setBookCarStatus(bookCarStatus);
        bookCarDto.setPrice(price);
        bookCarDto.setToDate(toDate);
        bookCarDto.setFromDate(fromDate);
        bookCarDto.setEmail(userEntity.getEmail());
        bookCarDto.setUserName(userEntity.getUsername());
        bookCarDto.setUserId(userEntity.getId());
        bookCarDto.setCarId(carEntity.getId());

        return bookCarDto;
    }
}
