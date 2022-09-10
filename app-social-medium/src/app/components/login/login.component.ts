import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../../services/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup = new FormGroup({}, [], []);
  submitted = false;
  loading = false;

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private router: Router) {
  }

  get f() {
    return this.loginForm?.controls;
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    /*
    * ===============================================================================
    * For redirecting user if he is logged in and tries to visit again the login path
    * ===============================================================================
    * */
    if (this.authenticationService.currentUserValue !== null
      && this.authenticationService.currentUserValue !== undefined) {
      this.router.navigate(['/home']);
    }
  }


  onSubmit() {
    this.submitted = true;
    if (this.loginForm?.invalid) {
      return;
    }
    this.loading = true;
    // @ts-ignore
    this.authenticationService.login(this.f.username.value, this.f.password.value)
      .subscribe(
        data => {
          console.log(data);
          this.router.navigate(['/home']);
        },
        error => {
          console.log('error');
          this.loading = false;
        });
  }
}
