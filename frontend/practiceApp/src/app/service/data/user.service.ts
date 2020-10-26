import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AUTHENTICATED_USER_ROLE, AUTHENTICATED_USER } from 'src/app/app.constant';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient:HttpClient) { }

  postUser(user:User){
    return this.httpClient.post<any>(`http://localhost:8080/brokerage/users`,user);
  }

  isAdminUser():boolean{
    let role=sessionStorage.getItem(AUTHENTICATED_USER_ROLE);
    if(role === 'ROLE_ADMIN')
      return true;
    else
      return false;
  }

  getUser(userId:number){
    return this.httpClient.get<User>(`http://localhost:8080/brokerage/users?id=${userId}`);
  }

  getAllUsers(){
    return this.httpClient.get<User[]>(`http://localhost:8080/brokerage/users/all`);
  }

  putUser(user:User){
    return this.httpClient.put<User>(`http://localhost:8080/brokerage/users`,user);
  }

  deleteUser(userId:number){
    return this.httpClient.delete(`http://localhost:8080/brokerage/users?id=${userId}`);
  }

  getUserPurchasedStocks(){
    return this.httpClient.get<PurchasedStock[]>(`http://localhost:8080/brokerage/users/${sessionStorage.getItem(AUTHENTICATED_USER)}/purchasedStocks`);
  }
  
}


export class User{
  constructor(
    public userId:number,
    public emailId:string,
    public name:string,
    public password:string,
    public active:boolean,
    public roles:string
  ){}
}

export class PurchasedStock{
  constructor(
    public id:number,
	  public totalUnits:number,
    public totalPrice:number,
    public symbol:string,
    public name:string,
	  public user:User,
	  public stockId:number){}
}

