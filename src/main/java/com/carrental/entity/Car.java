package com.carrental.entity;

import com.carrental.dto.CarDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;

@Entity
@Data
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String color;

    private String name;

    private String type;

    private String transmission;

    private String description;

    private Long price;

    private Year year;

    @Column(columnDefinition = "longblob")
    private byte[] image;

    public CarDto getCarDto(){
        CarDto carDto = new CarDto();
        carDto.setId(id);
        carDto.setBrand(brand);
        carDto.setColor(color);
        carDto.setName(name);
        carDto.setType(type);
        carDto.setTransmission(transmission);
        carDto.setDescription(description);
        carDto.setPrice(price);
        carDto.setYear(year);
        carDto.setReturnedImage(image);
        return carDto;
    }

}
