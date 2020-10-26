import { Component, OnInit } from '@angular/core';
import { User, UserService } from '../service/data/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AUTHENTICATED_USER, TOKEN, AUTHENTICATED_USER_ROLE } from '../app.constant';

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {

  user:User;
  constructor(private userService:UserService,
              private activatedRoute:ActivatedRoute,
              private router:Router) { }

  ngOnInit(): void {
    this.user = new User(-1,'','','',true,'ROLE_USER');
    this.user.userId=this.activatedRoute.snapshot.params['id'];
    // !== type matching not working here
    if(this.user.userId != -1){
      this.userService.getUser(this.user.userId).subscribe(
        response => this.user= response
        ,
        error=> console.log(error)
      )
    }

  }

  insertUser(user){
    if(user.userId == -1){
      this.userService.postUser(user).subscribe(
        user =>{
          console.log(user);
          if(AUTHENTICATED_USER == null && TOKEN == null && AUTHENTICATED_USER_ROLE == null)
             this.router.navigate(['login']);
          else
            this.router.navigate(['user']);
        },
        error=>{
          console.log(error)
        }
      )

    }
    else{
      this.userService.putUser(user).subscribe(
        user =>{
          this.router.navigate(['user']);
        },
        error =>{
          console.log(error);
        }
      )

    }
 
    console.log(user);
  }

}
