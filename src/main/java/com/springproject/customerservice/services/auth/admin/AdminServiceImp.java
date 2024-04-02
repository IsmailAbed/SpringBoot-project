package com.springproject.customerservice.services.auth.admin;

import com.springproject.customerservice.dto.BookCarDto;
import com.springproject.customerservice.dto.CarDto;
import com.springproject.customerservice.dto.CarListDto;
import com.springproject.customerservice.dto.SearchCarDto;
import com.springproject.customerservice.entity.BookEntity;
import com.springproject.customerservice.entity.CarEntity;
import com.springproject.customerservice.enums.BookCarStatus;
import com.springproject.customerservice.repository.BookACarRepository;
import com.springproject.customerservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements  AdminService{

    private final CarRepository carRepository;
    private final BookACarRepository bookACarRepository;

    @Override
    public boolean postCar(CarDto carDto) throws IOException {
        try{
            CarEntity car = new CarEntity();
            car.setName(carDto.getName());
            car.setBrand(carDto.getBrand());
            car.setColor(carDto.getColor());
            car.setPrice((carDto.getPrice()));
            car.setYear(carDto.getYear());
            car.setType(carDto.getType());
            car.setDescription(carDto.getDescription());
            car.setTransmission(carDto.getTransmission());
            car.setImage(carDto.getImage().getBytes());
            carRepository.save(car);
            return  true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(CarEntity::getCarDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarDto getCarById(Long id) {
        Optional<CarEntity> optionalCarEntity = carRepository.findById(id);
        return optionalCarEntity.map(CarEntity::getCarDto).orElse(null);
    }

    @Override
    public boolean updateCar(Long carId, CarDto carDto) throws IOException {
        //get car from db to update
        Optional<CarEntity> optionalCarEntity = carRepository.findById(carId);

        if (optionalCarEntity.isPresent()) {
            CarEntity existingCar = optionalCarEntity.get();
            if (carDto.getImage() != null)
                existingCar.setImage(carDto.getImage().getBytes());
            existingCar.setPrice(carDto.getPrice());
            existingCar.setYear(carDto.getYear());
            existingCar.setType(carDto.getType());
            existingCar.setDescription(carDto.getDescription());
            existingCar.setTransmission(carDto.getTransmission());
            existingCar.setYear(carDto.getYear());
            existingCar.setName(carDto.getName());
            existingCar.setBrand(carDto.getBrand());

            carRepository.save(existingCar);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<BookCarDto> getBookings() {
        return bookACarRepository.findAll().stream().map(BookEntity::getBookACarDto).collect(Collectors.toList());
    }

    @Override
    public boolean changeBookingStatus(Long bookingId, String status) {
        Optional<BookEntity> optionalBookCar = bookACarRepository.findById(bookingId);

        if(optionalBookCar.isPresent()){
            BookEntity existingBookACar = optionalBookCar.get();
            if(Objects.equals(status, "Approve"))
                existingBookACar.setBookCarStatus(BookCarStatus.APPROVED);
            else
                existingBookACar.setBookCarStatus(BookCarStatus.REJECTED);
              bookACarRepository.save(existingBookACar);
              return  true;
        }

        return false;
    }

    @Override
    public CarListDto searchCars(SearchCarDto searchCarDto) {
        CarEntity car = new CarEntity();
        car.setBrand(searchCarDto.getBrand());
        car.setType(searchCarDto.getType());
        car.setTransmission(searchCarDto.getTransmission());
        car.setColor(searchCarDto.getColor());

        ExampleMatcher exampleMatcher =
                ExampleMatcher.matchingAll()
                        .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<CarEntity> carEntityExample = Example.of(car, exampleMatcher);
        List<CarEntity> carList = carRepository.findAll(carEntityExample);
        CarListDto carListDto = new CarListDto();
        carListDto.setCarDtoList(carList.stream().map(CarEntity::getCarDto).collect(Collectors.toList()));

        return carListDto;
    }

}
