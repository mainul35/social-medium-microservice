import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MutualTabsComponent} from "./components/mutual-tabs/mutual-tabs.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {HomepageComponent} from "./components/homepage/homepage.component";
import {UserRegisterComponent} from "./components/user-register/user-register.component";
import {LoginComponent} from "./components/login/login.component";

const routes: Routes = [
  {path: 'home', component: HomepageComponent},
  {path: 'register', component: UserRegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'mutuals', component: MutualTabsComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: '**', component: NotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
