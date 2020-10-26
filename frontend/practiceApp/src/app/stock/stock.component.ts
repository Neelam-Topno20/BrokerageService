import { Component, OnInit } from '@angular/core';
import { StocksService, Stock } from '../service/data/stocks.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.css']
})
export class StockComponent implements OnInit {

  public stockList:Stock[];
  constructor(private stocksService:StocksService,
              private router:Router) { }

  ngOnInit(): void {

    this.refreshStockList();
  }

  refreshStockList(){
    this.stocksService.getAllStocks().subscribe(
      response => {
        this.stockList=response;
        console.log(this.stockList);
      },
      error => console.log(error) 
    );
  }


  purchase(stockId:number){
    console.log(stockId);
    this.router.navigate(['trade',-1,'stock',stockId]); 
  }
}
