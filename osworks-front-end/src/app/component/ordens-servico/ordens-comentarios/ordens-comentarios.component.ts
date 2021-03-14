import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

import { Comentario } from '../../../models/Comentario';

@Component({
  selector: 'app-ordens-comentarios',
  templateUrl: './ordens-comentarios.component.html',
  styleUrls: ['./ordens-comentarios.component.scss']
})
export class OrdensComentariosComponent implements OnInit
{
  @Input()
  public comentarios!: Comentario[];

  @Output()
  closeModal = new EventEmitter();

  constructor(private router: Router) { }

  ngOnInit() {}

  // comentarioSelect(comentario: Comentario)
  // {
  //   this.closeModal.emit(null);
  //   this.router.navigate(['/ordens-servico', comentario.id]);
  // }

}
