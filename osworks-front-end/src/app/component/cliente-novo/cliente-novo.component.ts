import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

import { Cliente } from '../../models/Cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-novo',
  templateUrl: './cliente-novo.component.html',
  styleUrls: ['./cliente-novo.component.css']
})
export class ClienteNovoComponent implements OnInit
{
  public clienteFormNovo!: FormGroup;
  public titulo = 'Novo Cliente';
  public clienteSelecionado: any = null;
  public clientes!: Cliente[];
  public cliente!: Cliente;

  nome: string = '';
  email: string = '';
  telefone: string = '';

  submitted = false;

  private unsubscriber = new Subject();

  constructor(private clienteService: ClienteService,
              private fb: FormBuilder,
              private spinner: NgxSpinnerService,
              private toastr: ToastrService)
  {
    this.criarForm();
  }

  ngOnInit(): void
  {

  }

  criarForm()
  {
    this.clienteFormNovo = this.fb.group({
                                      id: [0],
                                      nome: ['', Validators.required],
                                      email: ['', Validators.required],
                                      telefone: ['', Validators.required]
                                    });
  }

  saveCliente()
  {
    this.spinner.show();

    this.cliente = {...this.clienteFormNovo.value};

    this.clienteService.post(this.cliente)
      .pipe(takeUntil(this.unsubscriber))
      .subscribe(
        () => {
                this.toastr.success('Cliente salvo com sucesso!');
                this.submitted = true;
              }, (error: any) => {
                                  this.toastr.error(`Erro: Cliente não pode ser salvo!`);
                                  console.error(error.message);
                                  this.spinner.hide();
                                }, () => this.spinner.hide()
            );
  }

	newCliente(): void
  {
		this.nome = '';
        this.email = '';
        this.telefone = '';

		this.submitted = false;
	}

  voltar()
  {
    this.clienteSelecionado = null;
  }
}
