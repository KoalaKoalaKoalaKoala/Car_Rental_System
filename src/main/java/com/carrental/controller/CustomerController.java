package com.carrental.controller;

import com.carrental.dto.BookACarDto;
import com.carrental.dto.CarDto;
import com.carrental.dto.SearchCarDto;
import com.carrental.services.chatService.ChatService;
import com.carrental.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ChatService chatService;
    @Value("${spring.ai.ollama.chat.options.model}")
    private String model;

    private final OllamaChatModel chatModel;
    private final List<Map<String, String>> chatHistory = new ArrayList<>();
    private final WebClient webClient = WebClient.create("http://localhost:11434");



    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> carDopList = customerService.getAllCars();
        return ResponseEntity.ok(carDopList);
    }

    @PostMapping("/car/book")
    public ResponseEntity<Void> bookACar(@RequestBody BookACarDto bookACarDto) {
        boolean success = customerService.bookACar(bookACarDto);

        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long carId) {
        CarDto carDto = customerService.getCarById(carId);
        if (carDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(carDto);

    }

    @GetMapping("/car/bookings/{userId}")
    public ResponseEntity<List<BookACarDto>> getBookingsByUserId(@PathVariable Long userId) {
        List<BookACarDto> bookings = customerService.getAllBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }


    @PostMapping("/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto) {
        return ResponseEntity.ok(customerService.searchCars(searchCarDto));
    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = " ") String message) {
        String prePrompt = buildPrePrompt();

        List<Map<String, String>> messages = new ArrayList<>();

        messages.add(Map.of("role", "system", "content", prePrompt));

        messages.addAll(chatHistory);

        messages.add(Map.of("role", "user", "content", message));


        String reply = chatService.callChat(messages, model);
        chatHistory.add(Map.of("role", "user", "content", message));
        chatHistory.add(Map.of("role", "assistant", "content", reply));



        return Map.of("generation", reply);
    }

    @GetMapping("/ai/reset")
    public String restartSession() {
        chatHistory.clear();
        return "Chat session reset.";
    }



    private String buildPrePrompt() {
        List<CarDto> availableCars = customerService.getAllCars();
        StringBuilder carInfo = new StringBuilder("Tutaj są dostępne samochody:\n");
        for (CarDto car : availableCars) {
            carInfo.append("- ").append(car.getBrand()).append(" ").append(car.getName())
                    .append(", Cena: ").append(car.getPrice()).append(" za dzień\n");
        }

        return "Pisz domyślnie w języku polskim, Jesteś asystentem w wypożyczalni samochodów o nazwie 'Wypożyczalnia samochodów BFM'! Możesz pomagać w sprawach związanych z wypożyczaniem samochodów. Nie można z Tobą negocjować cen. Bądź poważny. Odpowiadaj wyczerpująco i podaj informacje konieczne do wypożyczenia."
                + " Tutaj informacje o samochodach:\n" + carInfo
                + "\n Pisz w języku pytającego, poczekaj na pytanie i pomóż wypożyczyć samochód klientami. "
                + "Nie witaj się z piszącym (nie pisz dzień dobry ani nic podobnego, jest to już napisane zanim ktoś do Ciebie napisał). "
                + "Firma jest zlokalizowana w Krakowie przy ul. Długa 1A, oferuje możliwość dostarczenia samochodu do Balic, na terenie Krakowa, oraz okolicznych miast w promieniu 50km, tel. kontaktowy 123-456-789.";
    }

}
