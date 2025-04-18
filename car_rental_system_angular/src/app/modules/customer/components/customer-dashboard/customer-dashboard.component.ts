import { Component, ViewEncapsulation } from '@angular/core';
import { CustomerService } from '../../service/customer.service';


@Component({
  selector: 'app-customer-dashboard',
  templateUrl: './customer-dashboard.component.html',
  styleUrls: ['./customer-dashboard.component.scss'],
})
export class CustomerDashboardComponent {

  cars: any[] = [];

  constructor(private service: CustomerService) { }


  ngOnInit() {
    this.getAllCars();
  }


  getAllCars() {
    this.cars = [];
    this.service.getAllCars().subscribe((res) => {
      console.log(res);
      res.forEach((element: any) => {
        element.processedImage = 'data:image/jpeg;base64,' + element.returnedImage;
        this.cars.push(element);
      });
    })
  }

}
