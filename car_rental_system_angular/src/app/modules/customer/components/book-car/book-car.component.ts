import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from 'src/app/auth/services/storage/storage.service';
import { NzMessageService } from 'ng-zorro-antd/message';
@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrls: ['./book-car.component.scss']
})
export class BookCarComponent {

  carId: number = this.activatedRoute.snapshot.params["id"];
  car: any;
  processedImage: any;
  validateForm!: FormGroup;
  isSpinning: boolean = false;
  dateFormat: string = "dd-MM-yyyy";


  constructor(private service: CustomerService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private message: NzMessageService,
    private fb: FormBuilder) { }


  ngOnInit() {
    this.validateForm = this.fb.group({
      toDate: [null, Validators.required],
      fromDate: [null, Validators.required],
    });
    this.getCarById();
  }

  getCarById() {
    this.service.getCarById(this.carId).subscribe((res) => {
      console.log(res);
      this.processedImage = 'data:image/jpeg;base64,' + res.returnedImage;
      this.car = res;
    });
  }
  bookACar(data: any) {
     console.log(data);
     this.isSpinning = true;
     let bookACarDto = {
       toDate: data.toDate,
       fromDate: data.fromDate,
       userId: StorageService.getUserId(),
       carId: this.carId
     }
     if (bookACarDto.toDate < bookACarDto.fromDate) {
       this.message.error("To date should be greater than from date", { nzDuration: 5000 });
       this.isSpinning = false;
       return;
     }
     console.log(data.toDate, data.fromDate);

     console.log(data.toDate + 1, data.fromDate);
     

     
     this.service.bookACar(bookACarDto).subscribe((res) => {
       console.log(res);
       this.message.success("Car booking requet submitted successfully", {nzDuration: 5000});
       this.router.navigateByUrl('/customer/dashboard');
       this.isSpinning = false;
     }, error => {
       this.message.error("Something went wrong", {nzDuration: 5000});
       this.isSpinning = false;
     })
   }


   
}
