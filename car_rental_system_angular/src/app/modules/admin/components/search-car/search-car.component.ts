import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-search-car',
  templateUrl: './search-car.component.html',
  styleUrls: ['./search-car.component.scss']
})
export class SearchCarComponent {

  searchCarFrom!: FormGroup;
  isSpinning: boolean = false;
  listOfOption: Array<{ label: string; value: string }> = [];
  listOfBrands = ["BMW", "AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "LEXUS", "KIA"];
  listOfType = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
  listOfColor = ["Red", "White", "Blue", "Black", "Orange", "Grey", "Silver"];
  listOfTransmission = ["Manual", "Automatic"];
  cars: any[] = [];

  constructor(private fb: FormBuilder, private service: AdminService) {
    this.searchCarFrom = this.fb.group({
      brand: [null], 
      type: [null],
      transmission: [null],
      color: [null],
    });
  }

  searchCar() {
    this.isSpinning = true;
    this.service.searchCar(this.searchCarFrom.value).subscribe((res) => {
      console.log(res);
      res.carDtoList.forEach((element: any) => {
        element.processedImage = 'data:image/jpeg;base64,' + element.returnedImage;
        this.cars.push(element);
      });
      this.isSpinning = false;
    })

  }

}
