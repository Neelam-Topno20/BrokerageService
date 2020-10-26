import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { ErrorComponent } from './error/error.component';
import { ListComponent } from './list/list.component';
import { LogoutComponent } from './logout/logout.component';
import { RouteGuideService } from './service/route-guide.service';
import { TradeComponent } from './trade/trade.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { UserComponent } from './user/user.component';
import { RouteAdminGuideService } from './service/route-admin-guide.service';
import { StockComponent } from './stock/stock.component';



const routes: Routes = [
  {path:'',component: LoginComponent},
  {path:'login',component: LoginComponent},
  {path:'registration/:id',component: UserRegistrationComponent},
  {path:'user',component: UserComponent,canActivate:[RouteAdminGuideService]},
  {path:'welcome/:name',component: WelcomeComponent,canActivate:[RouteGuideService]},
  {path:'stocks',component: StockComponent,canActivate:[RouteGuideService]},
  {path:'list',component: ListComponent,canActivate:[RouteGuideService]},
  {path:'trade/:tradeId/stock/:stockId',component: TradeComponent,canActivate:[RouteGuideService]},
  {path:'logout',component: LogoutComponent,canActivate:[RouteGuideService]},
  {path:'**',component: ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
