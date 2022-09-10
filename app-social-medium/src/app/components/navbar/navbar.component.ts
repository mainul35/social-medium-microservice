import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  loggedIn = false;

  constructor(private authenticationService: AuthenticationService, private router: Router) {
    this.authenticationService.currentUser$.subscribe(
      (userModel) => {
        this.loggedIn = userModel != null;
      }
    );
  }

  ngOnInit(): void {
  }

  logoutClicked($event: any) {
    $event.preventDefault();
    this.authenticationService.logout();
    this.router.navigate(['/home']);
  }
}
