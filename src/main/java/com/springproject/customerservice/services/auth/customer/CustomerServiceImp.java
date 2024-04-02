package com.springproject.customerservice.services.auth.customer;

import com.springproject.customerservice.dto.BookCarDto;
import com.springproject.customerservice.dto.CarDto;
import com.springproject.customerservice.dto.CarListDto;
import com.springproject.customerservice.dto.SearchCarDto;
import com.springproject.customerservice.entity.BookEntity;
import com.springproject.customerservice.entity.CarEntity;
import com.springproject.customerservice.entity.UserEntity;
import com.springproject.customerservice.enums.BookCarStatus;
import com.springproject.customerservice.repository.BookACarRepository;
import com.springproject.customerservice.repository.CarRepository;
import com.springproject.customerservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService{

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final BookACarRepository bookACarRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(CarEntity::getCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean bookACar(BookCarDto bookCarDto) {
        Optional<CarEntity> optionalCar = carRepository.findById(bookCarDto.getCarId());
        Optional<UserEntity> optionalUserEntity = userRepository.findById(bookCarDto.getUserId());

        if(optionalCar.isPresent() && optionalUserEntity.isPresent()){
            CarEntity existingCar  = optionalCar.get();

            BookEntity bookEntity = new BookEntity();
            bookEntity.setUserEntity(optionalUserEntity.get());
            bookEntity.setCarEntity(existingCar);
            bookEntity.setBookCarStatus(BookCarStatus.PENDING);

            long diffInMilliSeconds = bookCarDto.getToDate().getTime() - bookCarDto.getFromDate().getTime();
            long days = TimeUnit.MICROSECONDS.toDays(diffInMilliSeconds);
            bookEntity.setDays(days);
            bookEntity.setPrice(existingCar.getPrice() * days);
            bookACarRepository.save(bookEntity);

            return true;
        }
        return false;
    }

    @Override
    public CarDto getCarById(Long carId) {

        Optional<CarEntity> optionalCarEntity= carRepository.findById(carId);
        return optionalCarEntity.map(CarEntity::getCarDto).orElse(null);
    }

    @Override
    public List<BookCarDto> getBookingByUserId(Long userId) {
        return bookACarRepository.findAllByUserEntityId(userId).stream().map(BookEntity::getBookACarDto).collect(Collectors.toList());
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
