import { Injectable } from '@angular/core';
import { LogoutComponent } from '../logout/logout.component';
import { AUTHENTICATED_USER } from '../app.constant';

@Injectable({
  providedIn: 'root'
})
export class HardCodedAuthenticationService {

  constructor() { }

  

  authenticate(username:string,password:string):boolean{
    console.log('Before: '+this.isLoggedIn());
    if(username === 'neelam@gmail.com' && password === 'password'){
      sessionStorage.setItem(AUTHENTICATED_USER,username);
      console.log('After: '+this.isLoggedIn());
      return true
    }
    else{
      return false;
    }
  }

  isLoggedIn():boolean{
    return !( sessionStorage.getItem(AUTHENTICATED_USER) === null);
  }

  logout():void{
    sessionStorage.removeItem(AUTHENTICATED_USER);
  }

}
