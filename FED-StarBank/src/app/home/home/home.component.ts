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
import { AuthRequest } from '../../../models/interfaces/auth/AuthRequest';
import { CookieService } from 'ngx-cookie-service';
import { MessageService } from 'primeng/api';


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
    //NGX Cookie Service

  ],
  providers: [MessageService],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',

})
export class HomeComponent {

  loginCard = true;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private cookieService: CookieService,
    private messageService: MessageService
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

    if(this.loginForm.valid && this.loginForm.value ){
        this.userService.authUser(this.loginForm.value as AuthRequest)
        .subscribe({
          next: (response) =>{
            if(response){
              this.cookieService.set('USER_TOKEN', response?.token);
              this.loginForm.reset();
            }
            this.messageService.add({severity: 'success', summary: 'Success', detail: `Bem vindo ${response?.name}!`, life: 3000});
          },
          error: (error) => {
            this.messageService.add({severity: 'error', summary: 'Error', detail: `Erro ao autenticar usuario: ${error.error.message}`, life: 3000});

          }
        })

    }

  }

  onSubmitSignupForm(): void{
    console.log('Signup Form Submitted', this.signupForm.value);
    if(this.signupForm.valid && this.signupForm.value ){
        this.userService.signupUser(this.signupForm.value as SignupUserRequest)
        .subscribe({
          next: (response) =>{
            if(response){
              this.signupForm.reset(); // Reset the form after successful signup
              this.loginCard = true; // Switch to login card after successful signup
            }
             this.messageService.add({severity: 'success', summary: 'Success', detail: `Usuario ${response.name} cadastrado com sucesso!`, life: 3000});
          },
          error: (error) => {
            this.messageService.add({severity: 'error', summary: 'Error', detail: `Erro ao cadastrar usuario: ${error.error.message}`, life: 3000});
          }
        })

    }
  }

}
