import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  newAdmin = {
    userName: '', // Use 'userName' instead of 'username'
    userFirstName: '',
    userLastName: '',
    userPassword: ''
};

constructor(private http: HttpClient) {}

registerAdmin() {

  const token = localStorage.getItem('jwtToken'); // Adjust based on where you store your token
  console.log("token : ",token);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.http.post('http://localhost:9090/admin/add', this.newAdmin, {headers}).subscribe(
        response => {
            alert('Admin registered successfully!');
            // Optionally reset the form fields
            this.newAdmin = { userName: '', userFirstName: '', userLastName: '', userPassword: '' };
        },
        error => {
            alert('Failed to register admin.');
        }
    );
  }
}
