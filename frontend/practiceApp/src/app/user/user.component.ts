import { Component, OnInit } from '@angular/core';
import { UserService, User } from '../service/data/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  users:User[];
  constructor(private userService:UserService,
              private router:Router) { }

  ngOnInit(): void {
    this.getAllUsers();
  }

  addUser(){
    this.router.navigate(['registration',-1]);
  }

  getAllUsers(){
    this.userService.getAllUsers().subscribe(
      response =>{
        this.users=response;
        console.log(response);
      },
      error =>{
        console.log(error);
      }
    )
  }

  updateUser(user:User){
   this.router.navigate(['registration',user.userId]);
    console.log(user);
  }

  deleteUser(userId:number){
    console.log(userId);
    this.userService.deleteUser(userId).subscribe(
      user => { 
        console.log(user);
        this.getAllUsers();
      },
      error =>{
        console.log(error);
      }

    )
  }



   


}
