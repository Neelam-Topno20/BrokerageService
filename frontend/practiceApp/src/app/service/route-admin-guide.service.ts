import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { JwtAuthenticationService } from './jwt-authentication.service';
import { UserService } from './data/user.service';

@Injectable({
  providedIn: 'root'
})
export class RouteAdminGuideService implements CanActivate{

  constructor(
    private jwtAuthenticationService:JwtAuthenticationService,
    private router:Router,
    private userService:UserService
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if(this.jwtAuthenticationService.isLoggedIn() && this.userService.isAdminUser())
      return true;
    else{
      this.router.navigate(['login']);
      return false;
    }
  }
}
