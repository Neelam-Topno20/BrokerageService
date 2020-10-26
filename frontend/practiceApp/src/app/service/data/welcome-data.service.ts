import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URL } from 'src/app/app.constant';

@Injectable({
  providedIn: 'root'
})
export class WelcomeDataService {

  constructor(private httpClient:HttpClient) { }

  restAPIHealthCheck(){
    return this.httpClient.get<CustomMessage>(`${API_URL}/health`);
  }

}

export class CustomMessage{
  constructor(public status:string){

  }
}
