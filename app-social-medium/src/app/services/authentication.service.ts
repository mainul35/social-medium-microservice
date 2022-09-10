import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthInfoModel} from "../models/auth-info.model";
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService implements CanActivate {

  static USER_INFO = 'USER_INFO';
  static TOKEN = 'TOKEN';
  public currentUser$: Observable<AuthInfoModel>;
  /*
  * ======================================================
  * Will be used to notify the others who subscribe to it.
  * It is useful for user login / logout events
  * ======================================================
  * */

  // @ts-ignore
  private userInfoSubject: BehaviorSubject<UserModel>;

  constructor(private httpClient: HttpClient, private cookieService: CookieService, private router: Router) {
    const jsonString = this.cookieService.get(AuthenticationService.USER_INFO);
    if (jsonString === '') {
      // @ts-ignore
      this.userInfoSubject = new BehaviorSubject<UserModel>(null);
    } else {
      this.userInfoSubject = new BehaviorSubject<AuthInfoModel>(JSON.parse(this.cookieService.get(AuthenticationService.USER_INFO)));
    }
    this.currentUser$ = this.userInfoSubject.asObservable();
  }

  /**
   * =====================================================
   * This canActivate method is overriden and will work
   * for handling the logic of checking is a user is
   * logged in or not, and will perform necessary actions.
   * Only implementing this method is not enough. We will
   * also have to add canActivate: attribute in our routes
   * definition.
   * =====================================================
   * */
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    this.checkAuthentication();
    if (this.currentUserValue != null) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }

  private checkAuthentication() {
    const jsonString = this.cookieService.get(AuthenticationService.USER_INFO);
    if (jsonString === '') {
      // @ts-ignore
      this.userInfoSubject.next(null);
      // @ts-ignore
      this.userInfoSubject = new BehaviorSubject<UserModel>(null);
      this.currentUser$ = this.userInfoSubject.asObservable();
    } else {
      this.userInfoSubject = new BehaviorSubject<AuthInfoModel>(JSON.parse(this.cookieService.get(AuthenticationService.USER_INFO)));
    }
  }

  public get currentUserValue() {
    return this.userInfoSubject.value;
  }

  private updateData() {
    if (this.cookieService.get(AuthenticationService.USER_INFO) === '') {
      // @ts-ignore
      this.userInfoSubject.next(null);
      // @ts-ignore
      this.userInfoSubject = new BehaviorSubject<UserModel>(null);
      this.currentUser$ = this.userInfoSubject.asObservable();
    } else {
      this.userInfoSubject = new BehaviorSubject<AuthInfoModel>(JSON.parse(this.cookieService.get(AuthenticationService.USER_INFO)));
    }
  }

  logout() {
    // Delete the cookie
    this.cookieService.delete(AuthenticationService.USER_INFO);
    // @ts-ignore
    this.userInfoSubject.next(null);
  }

  login(username: string, password: string): Observable<AuthInfoModel> | null {
    const url = 'user/login';
    return this.httpClient.post<AuthInfoModel>(environment.SERVER_URL + url, {username, password})
      .pipe(
        map(userModel => {
          this.cookieService.set(AuthenticationService.USER_INFO, JSON.stringify(userModel));
          this.userInfoSubject.next(userModel);
          return userModel;
        })
      );
  }

  registerUser(formData: any): Observable<AuthInfoModel> {
    const url = '/user/create';
    return this.httpClient.post<AuthInfoModel>(environment.SERVER_URL + url, formData)
      .pipe(
        map(userModel => {
          this.cookieService.set(AuthenticationService.USER_INFO, JSON.stringify(userModel));
          this.userInfoSubject.next(userModel);
          return userModel;
        })
      );
  }
}
