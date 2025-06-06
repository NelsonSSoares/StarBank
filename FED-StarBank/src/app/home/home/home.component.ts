import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { CarouselModule } from 'primeng/carousel';
import { AssetsbarComponent } from "../../../shared/components/assetsbar/assetsbar.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    // Importing Angular modules
    CommonModule,
    // Importing PrimeNG modules
    CardModule,
    ButtonModule,
    ReactiveFormsModule,
    InputTextModule,
    ToastModule,
    CarouselModule,
    AssetsbarComponent
],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
