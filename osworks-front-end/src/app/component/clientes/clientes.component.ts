import { Component, OnInit, TemplateRef, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

import { Cliente } from '../../models/Cliente';
import { ClienteService } from 'src/app/services/cliente.service';
import { OrdemServico } from '../../models/Ordem-Servico';
import { OrdemServicoService } from 'src/app/services/ordem-servico.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit, OnDestroy
{
  public modalRef!: BsModalRef;
  public clienteForm!: FormGroup;
  public titulo = 'Clientes';
  public clienteSelecionado: any = null;
  public clientes!: Cliente[];
  public cliente!: Cliente;
  public ordensClientes!: OrdemServico[];
  public modeSave: string = 'post';

  private unsubscriber = new Subject();

  constructor(private clienteService: ClienteService,
              private osService: OrdemServicoService,
              private fb: FormBuilder,
              private modalService: BsModalService,
              private route: ActivatedRoute,
              private spinner: NgxSpinnerService,
              private toastr: ToastrService)
  {
    this.criarForm();
  }

  ngOnInit()
  {
    this.carregarClientes();
  }

  criarForm()
  {
    this.clienteForm = this.fb.group({
                                      id: [0],
                                      nome: ['', Validators.required],
                                      email: ['', Validators.required],
                                      telefone: ['', Validators.required],
                                      ativo: []
                                    });
  }

  carregarClientes()
  {
    const clienteId = this.route.snapshot.paramMap.get('id');

    this.spinner.show();
    this.clienteService.getAll()
      .pipe(takeUntil(this.unsubscriber))
      .subscribe((clientes: Cliente[]) => {
                                            this.clientes = clientes;
                                            console.log(clientes);

                                            if (clienteId != null && Number(clienteId) > 0)
                                            {
                                              this.clienteSelect(Number(clienteId));
                                            }

                                            this.toastr.success('Clientes foram carregado com Sucesso!');
                                          }, (error: any) => {
                                                                this.toastr.error('Clientes não carregados!');
                                                                console.error(error.message);
                                                                this.spinner.hide();
                                                             }, () => this.spinner.hide()
                );
  }

  clienteSelect(clienteId: number)
  {
    this.modeSave = 'put';

    // console.log(this.modeSave);

    this.clienteService.getById(clienteId).subscribe(
                                                      (clienteReturn) => {
                                                                          this.clienteSelecionado = clienteReturn;
                                                                          this.clienteForm.patchValue(this.clienteSelecionado);
                                                                        },
                                                                        (error) => {
                                                                                    this.toastr.error('Clientes não carregados!');
                                                                                    console.error(error);
                                                                                    this.spinner.hide();
                                                                                  }, () => this.spinner.hide()
                                                    );
  }

  saveCliente()
  {
    if (this.clienteForm.valid)
    {
      this.spinner.show();
      console.log(this.modeSave);

      if (this.modeSave === 'post')
      {
        this.cliente = {...this.clienteForm.value};

        this.clienteService.post(this.cliente)
          .pipe(takeUntil(this.unsubscriber))
          .subscribe(
            () => {
                    this.carregarClientes();
                    this.toastr.success('Cliente salvo com sucesso!');
                  }, (error: any) => {
                                      this.toastr.error(`Erro: Cliente não pode ser salvo!`);
                                      console.error(error.message);
                                      this.spinner.hide();
                                    }, () => this.spinner.hide()
                );
      }
      else
      {
        this.cliente = {id: this.clienteSelecionado.id, ...this.clienteForm.value};

        this.clienteService.put(this.cliente)
          .pipe(takeUntil(this.unsubscriber))
          .subscribe(
            () => {
                    this.carregarClientes();
                    this.toastr.success('Cliente salvo com sucesso!');
                  }, (error: any) => {
                                      this.toastr.error(`Erro: Cliente não pode ser salvo!`);
                                      console.error(error.message);
                                      this.spinner.hide();
                                    }, () => this.spinner.hide()
                );
      }
    }
  }

  excluirCliente(clienteId: number) {
    this.clienteService.delete(clienteId)
        .pipe(takeUntil(this.unsubscriber))
        .subscribe(
          (resp) => {
            this.carregarClientes();
            this.toastr.success('Cliente excluído com sucesso!');
          }, (error: any) => {
            this.toastr.error(`Erro: Cliente não pode ser excluído!`);
            console.error(error.message);
            this.spinner.hide();
          }, () => this.spinner.hide()
        );
  }

  carregaOrdensClientes(template: TemplateRef<any>, id: number)
  {
    this.spinner.show();
    this.osService.getByClienteId(id)
                  .pipe(takeUntil(this.unsubscriber))
                  .subscribe((ordens: OrdemServico[]) => {
                                                            this.ordensClientes = ordens;
                                                            this.modalRef = this.modalService.show(template);
                                                          }, (error: any) => {
                                                                                this.toastr.error(`erro: ${error.message}`);
                                                                                console.error(error);
                                                                                this.spinner.hide();
                                                                              }, () => this.spinner.hide()
                            );
  }

  voltar()
  {
    this.clienteSelecionado = null;
  }

  openModal(template: TemplateRef<any>, clienteId: number)
  {
    this.carregaOrdensClientes(template, clienteId);
  }

  closeModal()
  {
    this.modalRef.hide();
  }

  ngOnDestroy(): void
  {
    this.unsubscriber.next();
    this.unsubscriber.complete();
  }
}
