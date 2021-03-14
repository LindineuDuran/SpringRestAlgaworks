import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Cliente } from '../models/Cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService
{
  baseURL = `${environment.mainUrlAPI}clientes`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.baseURL);
  }

  getById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.baseURL}/${id}`);
  }

  post(cliente: Cliente) {
    return this.http.post(this.baseURL, cliente);
  }

  put(cliente: Cliente) {
    return this.http.put(`${this.baseURL}/${cliente.id}`, cliente);
  }

  patch(cliente: Cliente) {
    return this.http.patch(`${this.baseURL}/${cliente.id}`, cliente);
  }

  delete(id: number) {
    return this.http.delete(`${this.baseURL}/${id}`);
  }
}
