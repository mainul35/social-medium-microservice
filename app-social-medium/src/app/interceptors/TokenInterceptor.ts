import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { Observable } from "rxjs";
import { AuthenticationService } from "../services/authentication.service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private cookieService: CookieService) {} 

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        
        if (request.url.includes('login') || request.url.includes('register')) {
            return next.handle(request);
        }

        const userInfo = JSON.parse(this.cookieService.get(AuthenticationService.USER_INFO));
        const jwt = userInfo.jwtToken;
        
        // request.headers['headers'] = [{"Authorization": `Bearer ${jwt}`}]

        request = request.clone({
            setHeaders: {'authorization': 'Bearer '+jwt}
        });

        return next.handle(request);
    }    
}