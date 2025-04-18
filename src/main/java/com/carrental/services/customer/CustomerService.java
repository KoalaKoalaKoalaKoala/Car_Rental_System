package com.carrental.services.customer;

import com.carrental.dto.BookCarDto;
import com.carrental.dto.CarDto;


import java.util.List;

public interface CustomerService {

    List<CarDto> getAllCars();

    boolean bookACar(BookCarDto bookCarDto);

    CarDto getCarById(Long carId);
}
