import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { HardCodedAuthenticationService } from './hard-coded-authentication.service';
import { JwtAuthenticationService } from './jwt-authentication.service';

@Injectable({
  providedIn: 'root'
})
export class RouteGuideService implements CanActivate{

  constructor(private hardCodedAuthenticationService:HardCodedAuthenticationService,
              private jwtAuthenticationService:JwtAuthenticationService,
              private router:Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if(this.jwtAuthenticationService.isLoggedIn())
      return true;
    
    this.router.navigate(['login']);
    return false;
  }
}
 