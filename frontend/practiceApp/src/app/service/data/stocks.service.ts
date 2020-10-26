import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StocksService {

  

  constructor(private httpClient:HttpClient) { }

  postStock(stock:Stock){
    return this.httpClient.post<any>(`http://localhost:8080/brokerage/stocks`,stock);
  }

  getAllStocks(){

    return this.httpClient.get<Stock[]>(`http://localhost:8080/brokerage/stocks/all`);

  }

  getStock(stockId:number){
    return this.httpClient.get<Stock>(`http://localhost:8080/brokerage/stocks/${stockId}`);
  }

  updateStock(stock:Stock){
    return this.httpClient.put<Stock>(`http://localhost:8080/brokerage/stocks`,stock);
  }

  deleteStock(stockId:number){
    return this.httpClient.delete(`http://localhost:8080/brokerage/stocks/${stockId}`);
  }

}

export class Stock{
  constructor(
  public id:number,
  public symbol:string,
	public name:string,
	public ppu:number
  ){

  }
  
}