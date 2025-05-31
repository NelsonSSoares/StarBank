import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { HomeComponent } from "./home/home/home.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    HomeComponent
],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})

export class AppComponent {
  title = 'FED-StarBank';

  constructor(
    private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    // Initialization logic can go here
  }

}
