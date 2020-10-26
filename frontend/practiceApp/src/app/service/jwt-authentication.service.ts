import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { API_URL, AUTHENTICATED_USER, TOKEN, AUTHENTICATED_USER_ROLE } from '../app.constant';

@Injectable({
  providedIn: 'root'
})
export class JwtAuthenticationService {

  constructor(private httpClient:HttpClient) { }

  executeAuthenticationService(username:string,password:string){
    
    return this.httpClient.post<any>(`${API_URL}/authenticate`,{
      username,
      password
    }).pipe(
      map(
        data => {
          console.log(data);
          sessionStorage.setItem(AUTHENTICATED_USER,data.userId);
          sessionStorage.setItem(AUTHENTICATED_USER_ROLE,data.role);
          sessionStorage.setItem(TOKEN,data.jwt);
          return data;
        }
      )
    );
  }

  getAuthenticatedUser(){
    return sessionStorage.getItem(AUTHENTICATED_USER);
  }

  getAuthenticationToken(){
    if(this.getAuthenticatedUser())
      return sessionStorage.getItem(TOKEN);
  }

  isLoggedIn():boolean{
    return !( sessionStorage.getItem(AUTHENTICATED_USER) === null) 
              && !(sessionStorage.getItem(TOKEN) === null)
              && !(sessionStorage.getItem(AUTHENTICATED_USER_ROLE)=== null)  ;
  }

  logout():void{
    sessionStorage.removeItem(AUTHENTICATED_USER);
    sessionStorage.removeItem(AUTHENTICATED_USER_ROLE);
    sessionStorage.removeItem(TOKEN);
  }
}
