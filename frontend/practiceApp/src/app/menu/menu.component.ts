import { Component, OnInit } from '@angular/core';
import { HardCodedAuthenticationService } from '../service/hard-coded-authentication.service';
import { JwtAuthenticationService } from '../service/jwt-authentication.service';
import { UserService } from '../service/data/user.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  h:JwtAuthenticationService;
  u:UserService;

  constructor( private jwtAuthenticationService:JwtAuthenticationService,
               private userService:UserService) {
    this.h = jwtAuthenticationService;
    this.u = userService;
   }

  ngOnInit(): void {
  }

}
