import { Component, OnInit } from '@angular/core';
import { HardCodedAuthenticationService } from '../service/hard-coded-authentication.service';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { JwtAuthenticationService } from '../service/jwt-authentication.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private hardCodedAuthenticationService:HardCodedAuthenticationService,
              private basicAuthenticationService: BasicAuthenticationService,
              private jwtAuthenticationService:JwtAuthenticationService) { }

  ngOnInit(): void {
    this.jwtAuthenticationService.logout();
  }

}
