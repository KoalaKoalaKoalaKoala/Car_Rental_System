package com.carrental.services.customer;

import com.carrental.dto.BookCarDto;
import com.carrental.dto.CarDto;
import com.carrental.entity.BookACar;
import com.carrental.entity.Car;
import com.carrental.entity.User;
import com.carrental.enums.BookCarStatus;
import com.carrental.repository.BookACarRepository;
import com.carrental.repository.CarRepository;
import com.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private  final CarRepository carRepository;
    
    private final UserRepository userRepository;

    private final BookACarRepository bookACarRepository;


    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(toList());
    }


    @Override
    public boolean bookACar(BookCarDto bookCarDto) {
        Optional<Car> carOptional = carRepository.findById(bookCarDto.getCarId());
        Optional<User> userOptional = userRepository.findById(bookCarDto.getUserId());
        if (carOptional.isPresent() && userOptional.isPresent()) {
            Car existingCar = carOptional.get();
            BookACar bookACar = new BookACar();
            bookACar.setUser(userOptional.get());
            bookACar.setCar(existingCar);
            bookACar.setBookCarStatus(BookCarStatus.PENDING);
            long diffInMilliSeconds = bookCarDto.getToDate().getTime() - bookCarDto.getFromDate().getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
            bookACar.setDays(days);
            bookACar.setPrice(existingCar.getPrice() * days);
            bookACarRepository.save(bookACar);
            return true;
        }
        return false;
    }


    @Override
    public CarDto getCarById(Long carId) {
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            return carOptional.get().getCarDto();
        }
        return null;
    }
}
