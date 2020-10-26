import { Component, OnInit } from '@angular/core';
import { TradesService, Trade, FilterTrade } from '../service/data/trades.service';
import { Router } from '@angular/router';
import { AUTHENTICATED_USER_ROLE } from '../app.constant';
import { StocksService } from '../service/data/stocks.service';
import { Stock } from '../service/data/stocks.service'



@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  updateMessage:string;
  deleteMessage:string;
  tradeList:Trade[];
  filterTrade:FilterTrade;
  stockList:Stock[];

  constructor(private tradesService:TradesService,private router:Router,
              private stocksService:StocksService) { }

  ngOnInit(): void {
   this.refreshList();
   this.filterTrade = new FilterTrade('SBI','buy','2015-01-13 12:02:56','2020-01-13 12:02:56');
  }

  
  refreshList(){
    let role = sessionStorage.getItem(AUTHENTICATED_USER_ROLE);
    // if(role === 'ROLE_ADMIN'){
    //   this.tradesService.getAllTrades().subscribe(
    //     response => {
    //       this.tradeList = response;
    //     }
    //   )
    // }
    // else{
    //   this.tradesService.getAllTradesForUser().subscribe(
    //     response =>{
    //       this.tradeList=response;
    //     }
    //   );
    // }
    
    this.tradesService.getAllTradesForUser().subscribe(
      response =>{
        this.tradeList=response;
        console.log(this.tradeList);
      }
    );

    this.stocksService.getAllStocks().subscribe(
      response =>{
        this.stockList=response;
      },
      error=>{
        console.log(error);
      }
    )


  }

  addTrade(){
    console.log('add trade');
    this.router.navigate(['trade',-1]);
  }

  updateTrade(tradeId:number){

    this.tradesService.getStockIdForTrade(tradeId).subscribe(
      id =>{
        this.router.navigate(['trade',tradeId,'stock',id]);
        console.log('stockId',id);
        this.updateMessage=`Trade Id ${tradeId} updated succesfully!!`;
      },
      error =>{
        console.log(error);
      }
    )
   
  }

  deleteTrade(tradeId:number){
    this.tradesService.deleteTradeFromAPI(tradeId).subscribe(
      () => {
        console.log('trade deleted')
      this.refreshList();
    }
    );
    this.deleteMessage=`Trade Id ${tradeId} deleted succesfully!!`
  }

  filterTrades(){
    this.filterTrade.beginTimestamp = this.filterTrade.beginTimestamp + ' 00:00:00';
    this.filterTrade.endTimestamp = this.filterTrade.endTimestamp + ' 00:00:00';
    console.log('filtering trade',this.filterTrade);
    this.tradesService.filterTrades(this.filterTrade).subscribe(
      response =>{
        this.tradeList=response;

      },
      error=>{
        console.log(error);
      }
    )

  }

}
