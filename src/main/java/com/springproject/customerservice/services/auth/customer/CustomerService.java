package com.springproject.customerservice.services.auth.customer;

import com.springproject.customerservice.dto.BookCarDto;
import com.springproject.customerservice.dto.CarDto;
import com.springproject.customerservice.dto.CarListDto;
import com.springproject.customerservice.dto.SearchCarDto;

import java.util.List;

public interface CustomerService {

    List<CarDto> getAllCars();

    boolean bookACar(BookCarDto bookCarDto);

    CarDto getCarById(Long carId);

    List<BookCarDto> getBookingByUserId(Long userId);

    CarListDto searchCars(SearchCarDto searchCarDto);
}
