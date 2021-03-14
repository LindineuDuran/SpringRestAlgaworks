import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ClientesComponent } from './component/clientes/clientes.component';
import { ClienteNovoComponent } from './component/cliente-novo/cliente-novo.component';
import { ClientesOrdensComponent } from './component/clientes/clientes-ordens/clientes-ordens.component';
import { OrdensServicoComponent } from './component/ordens-servico/ordens-servico.component';
import { OrdemServicoNovoComponent } from './component/ordem-servico-novo/ordem-servico-novo.component';
import { OrdemDetalheComponent } from './component/ordens-servico/ordem-detalhe/ordem-detalhe.component';
import { OrdensComentariosComponent } from './component/ordens-servico/ordens-comentarios/ordens-comentarios.component';

const routes: Routes =
[
  { path: '', redirectTo: 'clientes', pathMatch: 'full' },
  { path: 'clientes', component: ClientesComponent },
  { path: 'cliente-novo', component: ClienteNovoComponent },
  { path: 'clientes-ordens', component: ClientesOrdensComponent },
  { path: 'ordens-servico', component: OrdensServicoComponent },
  { path: 'ordem-servico-novo/:id', component: OrdemServicoNovoComponent },
  { path: 'ordens-servico/:id', component: OrdemDetalheComponent },
  { path: 'ordens-comentarios', component: OrdensComentariosComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
