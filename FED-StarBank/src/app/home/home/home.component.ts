import { Component, Input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,

    // Importing PrimeNG modules
    CardModule,
    ButtonModule,
    ReactiveFormsModule,
    InputTextModule,
    ToastModule,

  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',

})
export class HomeComponent {

  loginCard = true;

  constructor(
    private formBuilder: FormBuilder
  ) {}


  signupForm = this.formBuilder.group({
    name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    lastName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    cpf: ['', [Validators.required, Validators.minLength(11) , Validators.maxLength(11)]],
    phone: ['', [Validators.required,Validators.minLength(8),  Validators.maxLength(11)]],
    email: ['', [Validators.required, Validators.email]],
    photo: [''],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(6)]]
  });

  loginForm = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['' , [Validators.required]]
  });



  ngOnInit() {

  }

  onSubmitLoginForm(): void{
    console.log('Login Form Submitted', this.loginForm.value);

  }

  onSubmitSignupForm(){
    console.log('Signup Form Submitted', this.signupForm.value);

  }

}
