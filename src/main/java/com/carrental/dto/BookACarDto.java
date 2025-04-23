package com.carrental.dto;

import com.carrental.enums.BookCarStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

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

    private String username;

    private String email;

}
