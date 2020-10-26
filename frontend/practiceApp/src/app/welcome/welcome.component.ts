import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WelcomeDataService } from '../service/data/welcome-data.service';
import { TradesService, Trade } from '../service/data/trades.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  username:string;
  message:string;
  errorMessage:string;
  trade:Trade;

  constructor(private aroute : ActivatedRoute,private welcomeDataService:WelcomeDataService,
    private tradesService:TradesService) { }

  ngOnInit(): void {
    //params is a map . name is the key
    this.username = this.aroute.snapshot.params['name'];
    this.getHealthCheckMessage();
  }

  getHealthCheckMessage(){
    console.log(this.welcomeDataService.restAPIHealthCheck());
    this.welcomeDataService.restAPIHealthCheck().subscribe(
      response => this.displayMessage(response),
      error => this.displayErrorMessage(error)
    );
  }

  displayMessage(message){
    this.message=message.status;
  }

  displayErrorMessage(error){
    this.message=error.error.message;
  }

  getTradeInfo(){
    this.tradesService.getTradeFromAPI(2).subscribe(
      response => this.displayTrade(response)
    );
  }

  displayTrade(trade){
   console.log(trade);
   this.trade=trade;
  }

}
