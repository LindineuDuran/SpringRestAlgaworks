import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Comentario } from '../models/Comentario';
import { OrdemServico } from '../models/Ordem-Servico';

@Injectable({
  providedIn: 'root'
})
export class OrdemServicoService {

  baseURL = `${environment.mainUrlAPI}ordens-servico`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<OrdemServico[]>
  {
    return this.http.get<OrdemServico[]>(this.baseURL);
  }

  getById(id: number): Observable<OrdemServico>
  {
    return this.http.get<OrdemServico>(`${this.baseURL}/${id}`);
  }

  getByClienteId(id: number): Observable<OrdemServico[]>
  {
    return this.http.get<OrdemServico[]>(`${this.baseURL}/buscando-por-cliente-id/${id}`);
  }

  getComentarios(id: number): Observable<Comentario[]>
  {
    console.log(id);

    return this.http.get<Comentario[]>(`${this.baseURL}/${id}/comentarios`);
  }

  post(ordemServico: OrdemServico)
  {
    return this.http.post(this.baseURL, ordemServico);
  }

  posComentario(id: number,comentario: Comentario)
  {
    return this.http.post(`${this.baseURL}/${id}/comentarios`, comentario);
  }

  put(ordemServico: OrdemServico)
  {
    return this.http.put(`${this.baseURL}/${ordemServico.id}`, ordemServico);
  }

  finalizar(ordemServico: OrdemServico)
  {
    return this.http.put(`${this.baseURL}/${ordemServico.id}/finalizacao`, ordemServico);
  }

  delete(id: number)
  {
    return this.http.delete(`${this.baseURL}/${id}`);
  }
}
