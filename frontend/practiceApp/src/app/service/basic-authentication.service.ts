import { Injectable } from '@angular/core';
import { LogoutComponent } from '../logout/logout.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { API_URL } from '../app.constant';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthenticationService {

  constructor(private httpClient:HttpClient) { }

  executeAuthenticationService(username:string,password:string){
    
    const basicAuthHeaderString='Basic ' + btoa(username + ':' + password);

    const headers = new HttpHeaders(username ? {
      authorization : basicAuthHeaderString
    } : {});

    return this.httpClient.get<string>(`${API_URL}/basicAuth`,{headers:headers}).pipe(
      map(
        data => {
          console.log(data);
          sessionStorage.setItem('authenticatedUser',data);
          sessionStorage.setItem('token',basicAuthHeaderString);
          return data;
        }
      )
    );
  }

  getAuthenticatedUser(){
    return sessionStorage.getItem('authenticatedUser');
  }

  getAuthenticationToken(){
    if(this.getAuthenticatedUser())
      return sessionStorage.getItem('token');
  }

  isLoggedIn():boolean{
    return !( sessionStorage.getItem('authenticatedUser') === null) && !(sessionStorage.getItem('token') === null);
  }

  logout():void{
    sessionStorage.removeItem('authenticatedUser');
    sessionStorage.removeItem('token');
  }

}
