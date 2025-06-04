import { UserService } from './../../services/user/user.service';
import { Component, Input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';
import { SignupUserRequest } from '../../../models/interfaces/user/SignupUserRequest';
import { HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
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
    private formBuilder: FormBuilder,
    private userService: UserService,
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

  onSubmitSignupForm(): void{
    console.log('Signup Form Submitted', this.signupForm.value);
    if(this.signupForm.valid && this.signupForm.value ){
        this.userService.signupUser(this.signupForm.value as SignupUserRequest)
        .subscribe({
          next: (response) =>{
            if(response){
              alert('User registered successfully!'
                + '\nName: ' + response.name
                + '\nLast Name: ' + response.lastName
                + '\nEmail: ' + response.email
                + '\nCPF: ' + response.cpf
                + '\nPhone: ' + response.phone
              );
              //this.toastService.add({severity: 'success', summary: 'Success', detail: 'User registered successfully!'});
              this.loginCard = true; // Switch to login card after successful signup
            }
          },
          error: (error) => {
            console.error('Error during signup:', error);
            //this.toastService.add({severity: 'error', summary: 'Error', detail: 'Failed to register user.'});
          }
        })

    }
  }

}
