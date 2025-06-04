export interface AuthResponse{
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
  token: string; // Added token field for authentication
}
