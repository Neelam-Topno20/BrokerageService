import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TradesService, Trade } from '../service/data/trades.service';
import { StocksService, Stock } from '../service/data/stocks.service';
import {formatDate } from '@angular/common';

@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.css']
})
export class TradeComponent implements OnInit {

  tradeId:number;
  stockId:number;
  stock:Stock;
  trade :Trade
  noOfUnits:number;
  errorMessage:any;

  constructor( private activatedRoute:ActivatedRoute, private tradesService:TradesService,
               private stocksService:StocksService,private router:Router) { }

  

  ngOnInit(): void {
    this.tradeId = this.activatedRoute.snapshot.params['tradeId']; 
    this.stockId = this.activatedRoute.snapshot.params['stockId'];
    //{{today | date:'dd-MM-yyyy hh:mm:ss a':'+0530'}}
    this.trade = new Trade(this.tradeId,'buy','SBI',30,500,formatDate(new Date(), 'yyyy-MM-dd HH:mm:ss', 'en-US', '+0530'));
    this.stock = new Stock(this.stockId,'SBI','State Bank Of India',26.0);
    if(this.tradeId != -1){
      this.tradesService.getTradeFromAPI(this.tradeId).subscribe(
        response => this.trade=response,
        error => console.log(error)
      );
    }
    if(this.stockId != -1){
      this.stocksService.getStock(this.stockId).subscribe(
        response =>{ 
          this.stock=response;
          this.trade.symbol = this.stock.symbol;
          this.trade.shares = this.trade.price/this.stock.ppu;
          this.trade.shares=Math.round(this.trade.shares  * 100) / 100
        },
        error => console.log(error)
      );
   
    }
    
  }

  updateTrade(){
    console.log(this.tradeId);
    if(this.tradeId == -1){
      console.log(this.trade);
      this.trade.timestamp = formatDate(new Date(), 'yyyy-MM-dd HH:mm:ss', 'en-US', '+0530');
      this.tradesService.postTradeFromAPI(this.trade,this.stockId).subscribe(
        response =>{
         console.log(response);
         this.router.navigate(['list']);
        },
        error =>{
          this.errorMessage=error.error.message;
          
        }
      )
    }
    else{
      this.trade.timestamp  = formatDate(new Date(), 'yyyy-MM-dd HH:mm:ss', 'en-US', '+0530');
      this.tradesService.updateTradeFromAPI(this.trade,this.stockId).subscribe(
        response => {
          console.log(response);
          this.router.navigate(['list']);
        },
        error =>{
          this.errorMessage=error.error.message;
          
        }
      )
    }
    
  }

  priceChange(){
    this.trade.shares = this.trade.price/this.stock.ppu;
    this.trade.shares=Math.round(this.trade.shares  * 100) / 100
  }

}
