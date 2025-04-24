import { Component } from '@angular/core';
import { AdminService } from '../../service/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-get-bookings',
  templateUrl: './get-bookings.component.html',
  styleUrls: ['./get-bookings.component.scss']
})
export class GetBookingsComponent {
  bookings: any;
  isSpinning = false;

  constructor(private adminService: AdminService,
    private message: NzMessageService,
    // private modal: NzModalService,
    // private router: Router
  ) {
    this.getBookings();
  }


  getBookings() {
    this.isSpinning = true;
    this.adminService.getCarBookings().subscribe((res) => {
      console.log(res);
      this.bookings = res;
      this.isSpinning = false;
    })
  }

  changeBookingStatus(bookingId: number, status: string) {
    this.isSpinning = true;
    console.log("Booking ID: " + bookingId + " Status: " + status);
    this.adminService.changeBookingStatus(bookingId, status).subscribe((res) => {
      this.isSpinning = false;
      console.log(res);
      this.getBookings();
      this.message.success("Booking status changed successfully", {nzDuration: 5000});
    }, (err) => {
      this.isSpinning = false;
      console.log(err);
      this.message.error("Error changing booking status", {nzDuration: 5000});
    })
  }


}
