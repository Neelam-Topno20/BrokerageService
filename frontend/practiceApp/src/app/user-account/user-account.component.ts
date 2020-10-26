import { Component, OnInit } from '@angular/core';
import { UserService, PurchasedStock } from '../service/data/user.service';
import { StocksService, Stock } from '../service/data/stocks.service';
import { StockComponent } from '../stock/stock.component';
import { TradesService } from '../service/data/trades.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html', 
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {

  purchasedStockList : PurchasedStock[];
  stockList : Stock[];
  flag :boolean=false;
  //tempStockList
  
  constructor(private userService:UserService,
              private stocksService:StocksService,
              private router:Router) { }

  ngOnInit(): void {

      this.stocksService.getAllStocks().subscribe(
        response =>{ 
          this.stockList=response;
          console.log(this.stockList);

        }
      );

  }

  viewPurchasedStocks(){
    this.userService.getUserPurchasedStocks().subscribe(
      response=>{
        this.purchasedStockList=response;
        //console.log(this.purchasedStockList);

        for(let i=0;i<this.purchasedStockList.length;i++){
          this.purchasedStockList[i].symbol=this.stockList[this.purchasedStockList[i].stockId - 1].symbol;
          this.purchasedStockList[i].name=this.stockList[this.purchasedStockList[i].stockId - 1].name;
        }
      },
      error =>{
        console.log(error);
      }
    );

    if(this.flag)
      this.flag=false;
    else
      this.flag=true;
  }

  sellPurchasedStock(stockId:number){
    this.router.navigate(['trade',-1,'stock',stockId]);
  }


}
