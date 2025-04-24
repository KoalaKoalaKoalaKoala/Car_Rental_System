package com.carrental.services.customer;

import com.carrental.dto.BookACarDto;
import com.carrental.dto.CarDto;
import com.carrental.dto.CarDtoListDto;
import com.carrental.dto.SearchCarDto;


import java.util.List;

public interface CustomerService {

    List<CarDto> getAllCars();

    boolean bookACar(BookACarDto bookACarDto);

    CarDto getCarById(Long carId);

    List<BookACarDto> getAllBookingsByUserId(Long userId);

    CarDtoListDto searchCars(SearchCarDto searchCarDto);

}
