import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,

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
