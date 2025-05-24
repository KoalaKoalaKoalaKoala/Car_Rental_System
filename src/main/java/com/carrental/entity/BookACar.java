package com.carrental.entity;

import com.carrental.dto.BookACarDto;
import com.carrental.enums.BookCarStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
public class BookACar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Long days;

    private Long price;

    private BookCarStatus bookCarStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Car car;


    public BookACarDto getBookACarDto() {
        BookACarDto bookACarDto = new BookACarDto();
        bookACarDto.setId(this.id);
        bookACarDto.setFromDate(this.fromDate);
        bookACarDto.setToDate(this.toDate);
        bookACarDto.setDays(this.days);
        bookACarDto.setPrice(this.price);
        bookACarDto.setBookCarStatus(this.bookCarStatus);
        bookACarDto.setCarId(this.car.getId());
        bookACarDto.setUserId(this.user.getId());
        bookACarDto.setUsername(this.user.getUsername());
        bookACarDto.setEmail(this.user.getEmail());
        return bookACarDto;
    }

}
