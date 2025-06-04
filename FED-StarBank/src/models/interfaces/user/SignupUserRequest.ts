export interface SignupUserRequest {
  name: string;
  lastName: string;
  cpf: string;
  phone: string;
  email: string;
  photo?: string; // Optional field
  password: string;
  confirmPassword: string;
}
