package com.carrental.services.customer;

import com.carrental.dto.BookACarDto;
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

import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    private final BookACarRepository bookACarRepository;


    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }


    @Override
    public boolean bookACar(BookACarDto bookACarDto) {
        Optional<Car> carOptional = carRepository.findById(bookACarDto.getCarId());
        Optional<User> userOptional = userRepository.findById(bookACarDto.getUserId());
        if (carOptional.isPresent() && userOptional.isPresent()) {
            Car existingCar = carOptional.get();
            BookACar bookACar = new BookACar();
            bookACar.setUser(userOptional.get());
            bookACar.setCar(existingCar);
            bookACar.setBookCarStatus(BookCarStatus.PENDING);
            bookACar.setFromDate(bookACarDto.getFromDate());
            bookACar.setToDate(bookACarDto.getToDate());
//            long diffInMilliSeconds = bookACarDto.getToDate().ge - bookACarDto.getFromDate().getTime();
//            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
            long days = ChronoUnit.DAYS.between(bookACarDto.getFromDate(), bookACarDto.getToDate());
            if (days == 0) {
                days = 1L;
            } else if (days < 0) {
                return false;
            }
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
        return carOptional.map(Car::getCarDto).orElse(null);
    }
}
