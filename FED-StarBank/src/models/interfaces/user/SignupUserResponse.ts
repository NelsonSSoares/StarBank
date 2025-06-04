export interface SignupUserResponse{
  id: string;
  name: string;
  lastName: string;
  cpf: string;
  phone: string;
  email: string;
  photo?: string; // Optional field
  password: string;
  agency: string;
  account: string;
}
