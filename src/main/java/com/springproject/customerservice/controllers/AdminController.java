package com.springproject.customerservice.controllers;

import com.springproject.customerservice.dto.BookCarDto;
import com.springproject.customerservice.dto.CarDto;
import com.springproject.customerservice.dto.SearchCarDto;
import com.springproject.customerservice.services.auth.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/car")
    public ResponseEntity<?> postCar(@ModelAttribute CarDto carDto) throws IOException {
        boolean success = adminService.postCar(carDto);

        if(success){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars(){
        return ResponseEntity.ok(adminService.getAllCars());
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCars(@PathVariable Long id){
        adminService.deleteCar(id);
        return  ResponseEntity.ok(null);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id){
        CarDto carDto = adminService.getCarById(id);
        return ResponseEntity.ok(carDto);
    }

    @PutMapping("/car/{carId}")
    public ResponseEntity<Void> updateCar(@PathVariable Long carId, @ModelAttribute CarDto carDto) throws IOException {
        try{
            boolean success = adminService.updateCar(carId, carDto);
            if(success) return  ResponseEntity.status(HttpStatus.OK).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/car/bookings")
    public ResponseEntity<List<BookCarDto>>   getBootkings(){
        return ResponseEntity.ok(adminService.getBookings());
    }


    @GetMapping("/car/booking/{bookingId}/{}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId,@PathVariable String status ){
       boolean success= adminService.changeBookingStatus(bookingId,status);
       if(success) return ResponseEntity.ok().build();
       return ResponseEntity.notFound().build();
    }


    @PostMapping("/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto){
        return ResponseEntity.ok(adminService.searchCars(searchCarDto));
    }
}
