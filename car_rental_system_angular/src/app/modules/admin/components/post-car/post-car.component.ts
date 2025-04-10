import { Component } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-post-car',
  templateUrl: './post-car.component.html',
  styleUrls: ['./post-car.component.scss']
})
export class PostCarComponent {

  postCarFrom!: FormGroup;
  isSpinning: boolean = false;
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  listOfOption: Array<{ label: string; value: string }> = [];
  listOfBrands = ["BMW", "AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "LEXUS", "KIA"];
  listOfType = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
  listOfColor = ["Red", "White", "Blue", "Black", "Orange", "Grey", "Silver"];
  listOfTransmission = ["Manual", "Automatic"];

  constructor(private fb: FormBuilder) {

  }

  ngOnInit() {
    this.postCarFrom = this.fb.group({
      name: [null, Validators.required],
      brand: [null, Validators.required],
      type: [null, Validators.required],
      color: [null, Validators.required],
      transmission: [null, Validators.required],
      price: [null, Validators.required],
      description: [null, Validators.required],
      year: [null, Validators.required]
    })
  }

  postCar() {
    console.log(this.postCarFrom.value);
    const formData: FormData = new FormData();
    formData.append('img', this.selectedFile!);
    formData.append('brand', this.postCarFrom.get('brand')!.value);
    formData.append('name', this.postCarFrom.get('name')!.value);
    formData.append('type', this.postCarFrom.get('type')!.value);
    formData.append('color', this.postCarFrom.get('color')!.value);
    formData.append('year', this.postCarFrom.get('year')!.value);
    formData.append('transmission', this.postCarFrom.get('transmission')!.value);
    formData.append('description', this.postCarFrom.get('description')!.value);
    formData.append('price', this.postCarFrom.get('price')!.value);
    console.log(formData);
  }


  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.previewImage();

  }

  previewImage() {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    };
    reader.readAsDataURL(this.selectedFile!);
  }


}
