import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClientesComponent } from './component/clientes/clientes.component';
import { ClienteNovoComponent } from './component/cliente-novo/cliente-novo.component';
import { ClientesOrdensComponent } from './component/clientes/clientes-ordens/clientes-ordens.component';
import { OrdensServicoComponent } from './component/ordens-servico/ordens-servico.component';
import { OrdemServicoNovoComponent } from './component/ordem-servico-novo/ordem-servico-novo.component';
import { OrdemDetalheComponent } from './component/ordens-servico/ordem-detalhe/ordem-detalhe.component';
import { OrdensComentariosComponent } from './component/ordens-servico/ordens-comentarios/ordens-comentarios.component';

import { NavComponent } from './component/shared/nav/nav.component';
import { TituloComponent } from './component/shared/titulo/titulo.component';

import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ToastrModule } from 'ngx-toastr';
import { NgxSpinnerModule } from 'ngx-spinner';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    ClientesComponent,
    ClienteNovoComponent,
    ClientesOrdensComponent,
    OrdensServicoComponent,
    OrdemServicoNovoComponent,
    OrdemDetalheComponent,
    OrdensComentariosComponent,
    NavComponent,
    TituloComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule.forRoot(),
    HttpClientModule,
    BsDropdownModule.forRoot(),
    BrowserAnimationsModule,
    NgxSpinnerModule,
    ToastrModule.forRoot({
      timeOut: 3500,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
      progressBar: true,
      closeButton: true
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
