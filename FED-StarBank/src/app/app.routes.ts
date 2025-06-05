import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './home/home/home.component';
import { DocumentationComponent } from './components/documentation/documentation.component';


export const routes: Routes = [

  {
    path:'login',
    component:LoginComponent
  },
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'documentation',
    component: DocumentationComponent
  }

];
