import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerDashboardComponent } from './components/customer-dashboard/customer-dashboard.component';
import { BookCarComponent } from './components/book-car/book-car.component';
import { NgZorroImportsModule } from 'src/app/NgZorroImportsModule';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MyBookingsComponent } from './components/my-bookings/my-bookings.component';
import { SearchCarComponent } from './components/search-car/search-car.component';
import { ChatbotComponent } from './components/chatbot/chatbot.component';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzCardModule } from 'ng-zorro-antd/card';


@NgModule({
  declarations: [
    CustomerDashboardComponent,
    BookCarComponent,
    MyBookingsComponent,
    SearchCarComponent,
    ChatbotComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    CustomerRoutingModule,
    NgZorroImportsModule,
    ReactiveFormsModule,
    FormsModule,
    NzAvatarModule,
    NzCardModule,
  ]
})
export class CustomerModule { }
