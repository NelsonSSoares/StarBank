import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { SignupUserRequest } from '../../../models/interfaces/user/SignupUserRequest';
import { UserService } from './../../services/user/user.service';


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
    ToastModule
],
  providers: [MessageService],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',

})
export class HomeComponent {
  isHovering: boolean = false;
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

     if (this.loginForm.valid && this.loginForm.value) {
    const email = this.loginForm.value.email!;
    const senhaDigitada = this.loginForm.value.password!;

    this.userService.getUserByEmail(email).subscribe({
      next: (usuario) => {
        if (usuario) {
          const senhaArmazenada = localStorage.getItem('USER_PASSWORD');

          if (senhaDigitada === senhaArmazenada) {
            this.messageService.add({
              severity: 'success',
              summary: 'Login',
              detail: `Bem-vindo de volta, ${usuario.name}!`,
              life: 3000
            });

            this.loginForm.reset();
            // Aqui você pode navegar para a próxima tela ou salvar o token, etc.

          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'Erro',
              detail: 'Senha incorreta.',
              life: 3000
            });
          }

        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Usuário não encontrado.',
            life: 3000
          });
        }
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: `Erro ao buscar usuário: ${error.error.message}`,
          life: 3000
        });
      }
    });
  }


/*     if(this.loginForm.valid && this.loginForm.value ){
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

    } */

  }

  onSubmitSignupForm(): void{
    console.log('Signup Form Submitted', this.signupForm.value);
    if(this.signupForm.valid && this.signupForm.value ){

      // Check if passwords match
      if (this.signupForm.value.password !== this.signupForm.value.confirmPassword) {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'As senhas não coincidem.', life: 3000});
        return;
      }

        this.userService.signupUser(this.signupForm.value as SignupUserRequest)
        .subscribe({
          next: (response) =>{
            if(response){
              this.signupForm.reset(); // Reset the form after successful signup
              this.loginCard = true; // Switch to login card after successful signup
            }
            // Remover depois
            localStorage.setItem('USER_EMAIL', response.email);
            localStorage.setItem('USER_PASSWORD', this.signupForm.value.password as string);

             this.messageService.add({severity: 'success', summary: 'Success', detail: `Usuario ${response.name} cadastrado com sucesso!`, life: 3000});
          },
          error: (error) => {
            this.messageService.add({severity: 'error', summary: 'Error', detail: `Erro ao cadastrar usuario: ${error.error.message}`, life: 3000});
          }
        })

    }
  }

}
