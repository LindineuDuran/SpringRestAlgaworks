import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

import { OrdemServico } from 'src/app/models/Ordem-Servico';

@Component({
  selector: 'app-clientes-ordens',
  templateUrl: './clientes-ordens.component.html',
  styleUrls: ['./clientes-ordens.component.scss']
})
export class ClientesOrdensComponent implements OnInit
{
  @Input()
  public ordens!: OrdemServico[];

  @Output()
  closeModal = new EventEmitter();

  constructor(private router: Router) { }

  ngOnInit() {}

  ordemSelect(ord: OrdemServico)
  {
    this.closeModal.emit(null);
    this.router.navigate(['/ordens-servico', ord.id]);
  }
}
