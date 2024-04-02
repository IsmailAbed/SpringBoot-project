package com.springproject.customerservice.controllers;

import com.springproject.customerservice.dto.BookCarDto;
import com.springproject.customerservice.dto.CarDto;
import com.springproject.customerservice.dto.SearchCarDto;
import com.springproject.customerservice.services.auth.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars(){
        List<CarDto> carDtoList = customerService.getAllCars();
        return ResponseEntity.ok(carDtoList);
    }

    @PostMapping("/car/book")
    public ResponseEntity<Void> bookACar(@RequestBody BookCarDto bookCarDto){
        boolean success = customerService.bookACar(bookCarDto);

        if(success) return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<CarDto> getCarsById(@PathVariable Long carId){
        CarDto carDto = customerService.getCarById(carId);
        if(carDto== null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(carDto);
    }

    @GetMapping("/car/bookings/{userId}")
    public ResponseEntity<List<BookCarDto>> getBookByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(customerService.getBookingByUserId(userId));
    }


    @PostMapping("/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto){
        return ResponseEntity.ok(customerService.searchCars(searchCarDto));
    }

}
