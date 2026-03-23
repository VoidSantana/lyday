import { Component } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {FormsModule} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.html',
  styleUrl: './login.css',
  imports: [
    FormsModule
  ]
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(
    private authService: AuthService,
    private router: Router) {}

  login(){
    this.authService.login({
      username: this.username,
      password: this.password
    }).subscribe({
      next: (res) => {
        console.log('TOKEN', res.token);

        this.router.navigate(['/dashboard']);

      },
      error: (err) =>{
        console.error("Erro login ", err);
      }
    });
  }
}
