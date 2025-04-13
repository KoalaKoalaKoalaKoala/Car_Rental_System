import { Component } from '@angular/core';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent {

  cars: any[] = [];

  

  constructor(private adminService: AdminService) {

  }

  ngOnInit() {
    this.getAllCars();
  }


  getAllCars() {
    this.adminService.getAllCars().subscribe((res) => {
      console.log(res);
    res.forEach((element: any) => {
      element.processedImage = 'data:image/jpeg;base64,' + element.returnedImage;
      this.cars.push(element);
    });
  })
  }
}
