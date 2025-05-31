import { Component, Input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { last } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    CardModule,
    ButtonModule,
    ReactiveFormsModule,
    InputTextModule,
    ToastModule
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  loginCard = true;



  signupForm = this.formBuilder.group({
    name: ['', Validators.required],
    lastName: ['', Validators.required],
    cpf: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(11)]],
    phone: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(11)]],
    email: ['', [Validators.required, Validators.email]],
    photo: [''],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(6)]]
  });

  loginForm = this.formBuilder.group({
    email: ['', Validators.required, Validators.email],
    password: ['' , Validators.required]
  });

  constructor(
    private formBuilder: FormBuilder
  ) {

  }

  ngOnInit() {

  }

  onSubmitLoginForm(): void{
    console.log('Login Form Submitted', this.loginForm.value);

  }

  onSubmitSignupForm(){
    console.log('Signup Form Submitted', this.signupForm.value);

  }

}
