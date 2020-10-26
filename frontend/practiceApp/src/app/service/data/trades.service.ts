import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URL, AUTHENTICATED_USER } from 'src/app/app.constant';

@Injectable({
  providedIn: 'root'
})
export class TradesService {

  constructor(private httpClient:HttpClient) { }

  
  postTradeFromAPI(trade:Trade,stockId:number){
    return this.httpClient.post<Trade>(`${API_URL}/trades/user/${sessionStorage.getItem(AUTHENTICATED_USER)}/stock/${stockId}`,trade);
  }

  getStockIdForTrade(tradeId:number){
    return this.httpClient.get<number>(`${API_URL}/trade/stock/${tradeId}`);
  }

  getTradeFromAPI(id:number){
    return this.httpClient.get<Trade>(`${API_URL}/trades/${id}`);
  }

  getAllTrades(){
    return this.httpClient.get<Trade[]>(`${API_URL}/trades`);
  }

  getAllTradesForUser(){
    return this.httpClient.get<Trade[]>(`${API_URL}/trades/users/${sessionStorage.getItem(AUTHENTICATED_USER)}`);
  }

  deleteTradeFromAPI(id:number){
    return this.httpClient.delete(`${API_URL}/trades/${id}`);
  }
 
  updateTradeFromAPI(trade:Trade,stockId:number){
    return this.httpClient.put<Trade>(`${API_URL}/trades/user/${sessionStorage.getItem(AUTHENTICATED_USER)}/stock/${stockId}`,trade);
  }
  
  filterTrades(filterTrade:FilterTrade){
    return this.httpClient.get<Trade[]>(`${API_URL}/trades/user/${sessionStorage.getItem(AUTHENTICATED_USER)}/stocks/${filterTrade.symbol}?type=${filterTrade.type}&start=${filterTrade.beginTimestamp}&end=${filterTrade.endTimestamp}`);
  }

}

export class Trade {
  constructor(
    public tradeId:number,
    public type:string,
    public symbol:string,
    public shares:number,
    public price:number,
    public timestamp:string){

              }
}

export class FilterTrade{
  constructor(
    public symbol:string,
    public type:string,
    public beginTimestamp:string,
    public endTimestamp:string
  ){}
}

// export class User{
//   constructor(
//     userId:number,
//     name:string,
//     password:string,
//     active:boolean,
//     roles:string
//   ){}
// }