import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HardCodedAuthenticationService } from '../service/hard-coded-authentication.service';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { DEFAULT_USER, DEFAULT_PASSWORD, INVALID_CREDENTIALS } from '../app.constant';
import { JwtAuthenticationService } from '../service/jwt-authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username=DEFAULT_USER;
  password:string=DEFAULT_PASSWORD;
  invalidCredentials = false;
  errorMessage = INVALID_CREDENTIALS;

    //Angular has inbuilt dependency feature just set as paramter in constructor, 
    //router now becomes a member variable
    // It has to be declared private 
  constructor(private route: Router, private hardCodedAuthentication:HardCodedAuthenticationService,
              private basicAuthenticationService:BasicAuthenticationService,
              private jwtAuthenticationService:JwtAuthenticationService) { 
  }

  ngOnInit(): void {
  }

  // hard coded authentication
  handleLogin(){
    if(this.hardCodedAuthentication.authenticate(this.username,this.password)){
      this.invalidCredentials= false;
      this.route.navigate(['welcome',this.username]);
    }
    else{
      this.invalidCredentials = true;
    }
  }

  handleBasicAuthLogin(){
    this.basicAuthenticationService.executeAuthenticationService(this.username,this.password).subscribe(
      response =>{
        console.log(response);
        this.invalidCredentials=false;
        this.route.navigate(['welcome',this.username]);
      },
      error =>{
        console.log(error);
        this.invalidCredentials=true;
      }
    );
      
  }

  handleJwtLogin(){
    this.jwtAuthenticationService.executeAuthenticationService(this.username,this.password).subscribe(
      response =>{
        console.log(response);
        this.invalidCredentials=false;
        this.route.navigate(['welcome',this.username]);
      },
      error =>{
        console.log(error);
        this.invalidCredentials=true;
      }
    );
      
  }
}
