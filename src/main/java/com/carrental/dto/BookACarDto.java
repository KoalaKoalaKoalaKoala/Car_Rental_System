package com.carrental.dto;

import com.carrental.enums.BookCarStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookACarDto {

    private Long id;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Long days;

    private Long price;

    private BookCarStatus bookCarStatus;

    private Long carId;

    private Long userId;

    private String carName;

    private String username;

    private String email;

}
