package com.carrental.controller;

import com.carrental.dto.BookACarDto;
import com.carrental.dto.CarDto;
import com.carrental.dto.SearchCarDto;
import com.carrental.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final OllamaChatModel chatModel;


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
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

        // Fetch data from the database
        List<CarDto> availableCars = customerService.getAllCars();

        // Format the data into a string
        StringBuilder carInfo = new StringBuilder("Here are some available cars:\n");
        for (CarDto car : availableCars) {
            carInfo.append("- ").append(car.getBrand()).append(" ").append(car.getName())
                    .append(", Price: ").append(car.getPrice()).append(" per day\n");
        }

        String prePrompt = "Jesteś asystentem w wypożyczalni samochodów o nazwie 'Wypożyczalnia samochodów BFM'. Możesz pomagać w sprawach związanych z wypożyczaniem samochodów. Nie można z Tobą negocjować cen" +
                "Tutaj informacje o samochodach:" +
                carInfo.toString() +
                "Firma jest zlokalizowana w Krakowie przy ul. Długa 1A, oferuje możliwość dostarczenia samochodu do Balic, na terenie Krakowa, oraz okolicznych miast w promieniu 50km, tel. kontaktowy 123-456-789" +
                "Przykładowe pytania do Ciebie oraz odpowiedzi:" +
                "'Pytanie: Jakie dokumenty są potrzebne, aby wypożyczyć samochód w Wypożyczalni samochodów BFM?\n" +
                "Odpowiedź: Dzień dobry! W Wypożyczalni samochodów BFM do wypożyczenia pojazdu potrzebne jest ważne prawo jazdy, dowód osobisty lub paszport oraz karta płatnicza na nazwisko osoby wynajmującej. Zapraszamy do kontaktu, jeśli ma Pan/Pani dodatkowe pytania!\n" +
                "Pytanie: Czy w Wypożyczalni samochodów BFM można wypożyczyć samochód z automatyczną skrzynią biegów?\n" +
                "Odpowiedź: Oczywiście! W Wypożyczalni samochodów BFM oferujemy szeroki wybór pojazdów, w tym modele z automatyczną skrzynią biegów. Prosimy o kontakt, aby sprawdzić dostępność i zarezerwować odpowiedni samochód dla Pana/Pani.\n" +
                "Pytanie: Jak długo można wypożyczyć samochód w Wypożyczalni samochodów BFM?\n" +
                "Odpowiedź: Witamy serdecznie! W Wypożyczalni samochodów BFM oferujemy elastyczne okresy wynajmu – od jednego dnia do nawet kilku miesięcy. Prosimy o podanie preferowanego terminu, a my z przyjemnością przygotujemy dla Pana/Pani najlepszą ofertę!'" +
                "Pytanie: ";

        return Map.of("generation", this.chatModel.call(prePrompt  + message));
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

}
