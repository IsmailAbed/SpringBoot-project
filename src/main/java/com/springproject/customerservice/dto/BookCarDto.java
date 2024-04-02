package com.springproject.customerservice.dto;

import com.springproject.customerservice.enums.BookCarStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookCarDto {

    private Long id;

    private Date fromDate;

    private Date toDate;

    private Long days;

    private Long price;

    private BookCarStatus bookCarStatus;

    private Long carId;

    private Long userId;


    //we added them later (framework properties)
    private String userName;

    private String email;
}
