import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

import { OrdemServico } from '../../models/Ordem-Servico';
import { OrdemServicoService } from 'src/app/services/ordem-servico.service';
import { Cliente } from '../../models/Cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-ordem-servico-novo',
  templateUrl: './ordem-servico-novo.component.html',
  styleUrls: ['./ordem-servico-novo.component.css']
})
export class OrdemServicoNovoComponent implements OnInit
{
  public ordemFormNovo!: FormGroup;
  public titulo = 'Nova Ordem de Serviço';
  public ordemSelecionada: any = null;
  public ordensServico!: OrdemServico[];
  public ordemServico!: OrdemServico;
  public clienteSelecionado: any = null;

  cliente!: Cliente;
  descricao: string = '';
  preco: number = 0;

  submitted = false;

  private unsubscriber = new Subject();

  constructor(private osService: OrdemServicoService,
              private clienteService: ClienteService,
              private route: ActivatedRoute,
              private fb: FormBuilder,
              private spinner: NgxSpinnerService,
              private toastr: ToastrService)
  {
    this.criarForm();
  }

  ngOnInit(): void
  {
    // this.clienteSelect();
  }

  // clienteSelect()
  // {
  //   const clienteId = this.route.snapshot.paramMap.get('id');
  //   this.clienteService.getById(Number(clienteId)).subscribe(
  //                                                     (clienteReturn) => {
  //                                                                         this.clienteSelecionado = clienteReturn;
  //                                                                         },
  //                                                                         (error) => {
  //                                                                                     this.toastr.error('Clientes não carregados!');
  //                                                                                     console.error(error);
  //                                                                                     this.spinner.hide();
  //                                                                                   }, () => this.spinner.hide()
  //                                                     );
  // }

  criarForm()
  {
    const clienteId = this.route.snapshot.paramMap.get('id');

    this.ordemFormNovo = this.fb.group({
                                        cliente: {id: clienteId},
                                        descricao: ['', Validators.required],
                                        preco: [0, Validators.required]
                                      });
  }

  saveOrdem()
  {
    this.ordemServico = {...this.ordemFormNovo.value};

    console.log(this.ordemServico);

    this.osService.post(this.ordemServico)
      .pipe(takeUntil(this.unsubscriber))
      .subscribe(
        () => {
                this.toastr.success('Ordem de Serviço salva com sucesso!');
                this.submitted = true;
              }, (error: any) => {
                                  this.toastr.error(`Erro: Ordem de Serviço não pode ser salva!`);
                                  console.error(error.message);
                                  this.spinner.hide();
                                }, () => this.spinner.hide()
            );
  }

	newOrdem(): void
  {
    this.cliente.id = 0;
    this.descricao = '';
  	this.preco = 0;

		this.submitted = false;
	}

  voltar()
  {
    this.ordemSelecionada = null;
  }
}
