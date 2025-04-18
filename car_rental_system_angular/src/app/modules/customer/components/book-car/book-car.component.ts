import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrls: ['./book-car.component.scss']
})
export class BookCarComponent {

  carId: number = this.activatedRoute.snapshot.params['id'];
  car: any;
  processedImage: any;


  constructor(private service: CustomerService, private activatedRoute: ActivatedRoute) { }


  ngOnInit() {
    this.getCarById();
  }

  getCarById() {
    this.service.getCarById(this.carId).subscribe((res) => {
      console.log(res);
      this.car = res;
      this.processedImage = 'data:image/jpeg;base64,' + res.returnedImage;

    });
  }
}
