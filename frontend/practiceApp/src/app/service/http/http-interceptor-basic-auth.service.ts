import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { AUTHENTICATED_USER, TOKEN } from 'src/app/app.constant';


@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorBasicAuthService implements HttpInterceptor{

  constructor() { }

  intercept(request: HttpRequest<any>, next: HttpHandler){
    // let username="neelam@gmail.com";
    // let password = "password";
    // let basicAuthHeaderString='Basic '+window.btoa(username+":"+password);

    let basicAuthHeaderString = sessionStorage.getItem(TOKEN);
    let userId = sessionStorage.getItem(AUTHENTICATED_USER);

    //console.log('from interceptor '+basicAuthHeaderString);
    if(basicAuthHeaderString && userId){
      request=request.clone({
        setHeaders:{
          authorization : basicAuthHeaderString
        }
      });
    }
      return next.handle(request);
  }
}
