package com.springproject.customerservice.services.auth.admin;

import com.springproject.customerservice.dto.BookCarDto;
import com.springproject.customerservice.dto.CarDto;
import com.springproject.customerservice.dto.CarListDto;
import com.springproject.customerservice.dto.SearchCarDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    boolean postCar(CarDto carDto) throws IOException;

    List<CarDto> getAllCars();

    void deleteCar(Long id);

    CarDto getCarById(Long id);

    boolean updateCar(Long carId, CarDto carDto) throws IOException;

    List<BookCarDto> getBookings();

    boolean changeBookingStatus(Long bookingId, String status);


    CarListDto searchCars(SearchCarDto searchCarDto);
}
