import { ProfileDashboardComponent } from './components/profile-dashboard/profile-dashboard.component';
import { SignupPageComponent } from './components/signup-page/signup-page.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { LandingComponent } from './components/landing/landing.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PresentationPageComponent } from './components/presentation-page/presentation-page.component';

import {canActivate, redirectLoggedInTo, redirectUnauthorizedTo } from '@angular/fire/compat/auth-guard';


const redirectToLogin = () => redirectUnauthorizedTo(['login']);
const redirectToHome = () => redirectLoggedInTo(['home']);

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component:PresentationPageComponent
  },
  {
    path:'login',
    component:LoginPageComponent,
    ...canActivate(redirectToHome)
  },
  {
    path:'cadastrar',
    component:SignupPageComponent,
    ...canActivate(redirectToHome)
  },
  {
    path: 'home',
    component:LandingComponent,
    ...canActivate(redirectToLogin)
  },
  {
    path:'perfil',
    component: ProfileDashboardComponent,
    ...canActivate(redirectToLogin)
  },
  {
    path: '**',
    redirectTo:'',
    ...canActivate(redirectToHome)
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
