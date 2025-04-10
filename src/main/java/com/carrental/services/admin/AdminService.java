package com.carrental.services.admin;

import com.carrental.dto.CarDto;

import java.io.IOException;

public interface AdminService {

    boolean postCar(CarDto carDto) throws IOException;

}
