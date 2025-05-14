import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from 'src/app/auth/services/storage/storage.service';

const BASIC_URL = ["http://localhost:8080"];

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }


  getAllCars(): Observable<any> {
    return this.http.get(BASIC_URL + "/api/customer/cars", { headers: this.createAuthorizationHeader() });
  }

  getCarById(carId: number): Observable<any> {
    return this.http.get(BASIC_URL + "/api/customer/car/" + carId, { headers: this.createAuthorizationHeader() });
  }

  bookACar(bookACarDto: any): Observable<any> {
    return this.http.post(BASIC_URL + "/api/customer/car/book", bookACarDto, { headers: this.createAuthorizationHeader() });
  }

  getBookingsByUserId(): Observable<any> {
    return this.http.get(BASIC_URL + "/api/customer/car/bookings/" + StorageService.getUserId(), { headers: this.createAuthorizationHeader() });
  }

    searchCar(searchCarDto: any): Observable<any> {
  
      return this.http.post(BASIC_URL + "/api/customer/car/search", searchCarDto,
        {headers: this.createAuthorizationHeader()
  
        }
      );
    }

    generate(message: string): Observable<any> {
      const url = `${BASIC_URL}/api/customer/ai/generate?message=${encodeURIComponent(message)}`;
      return this.http.get(url, { headers: this.createAuthorizationHeader() });
    }

    restartSession(): Observable<any> {
      const url = `${BASIC_URL}/api/customer/ai/reset`;
      return this.http.get(url, { headers: this.createAuthorizationHeader(), responseType: 'text' });
    }

  createAuthorizationHeader(): HttpHeaders {
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set('Authorization', 'Bearer ' + StorageService.getToken());
  }


}
