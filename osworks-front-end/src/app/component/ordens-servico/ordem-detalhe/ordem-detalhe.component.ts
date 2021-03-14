import { Component, OnInit, OnDestroy, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

import { OrdemServico } from '../../../models/Ordem-Servico';
import { OrdemServicoService } from 'src/app/services/ordem-servico.service';
import { Cliente } from '../../../models/Cliente';
import { Comentario } from '../../../models/Comentario';

@Component({
  selector: 'app-ordem-detalhe',
  templateUrl: './ordem-detalhe.component.html',
  styleUrls: ['./ordem-detalhe.component.scss']
})
export class OrdemDetalheComponent implements OnInit, OnDestroy
{
  public modalRef!: BsModalRef;
  public ordemForm!: FormGroup;
  public comentarioFormNovo!: FormGroup;
  public titulo = '';
  public ordem!: OrdemServico;
  public ordemSelecionada!: OrdemServico;
  public comentariosOrdens!: Comentario[];
  public comentario!: Comentario;
  public clientesOrdens!: Cliente[];
  private unsubscriber = new Subject();

  descricao: string = '';

  constructor(
                private router: Router,
                private route: ActivatedRoute,
                private osService: OrdemServicoService,
                private fb: FormBuilder,
                private modalService: BsModalService,
                private toastr: ToastrService,
                private spinner: NgxSpinnerService
              )
              {
                this.criarForm();
              }

  ngOnInit()
  {
    this.carregarOrdem();
    this.carregaComentariosOrdens();
  }

  criarForm()
  {
    this.comentarioFormNovo = this.fb.group({descricao: ['', Validators.required]});
  }

  carregarOrdem()
  {
    const ordemId = this.route.snapshot.paramMap.get('id');
    this.spinner.show();
    this.osService.getById(Number(ordemId))
        .pipe(takeUntil(this.unsubscriber))
        .subscribe((ordem: OrdemServico) => {
                                              this.ordemSelecionada = ordem;
                                              this.titulo = 'Ordem de Serviço: ' + this.ordemSelecionada.id;
                                              this.toastr.success('Ordem de Serviço carregada com Sucesso!');
                                            }, (error: any) => {
                                                                  this.toastr.error('Ordem de Serviço não carregada!');
                                                                  console.log(error);
                                                               }, () => this.spinner.hide()
                                          );
  }

carregaComentariosOrdens()
{
  this.spinner.show();
  const ordemId = this.route.snapshot.paramMap.get('id');

  this.osService.getComentarios(Number(ordemId))
                .pipe(takeUntil(this.unsubscriber))
                .subscribe((comentarios: Comentario[]) => {
                                                          this.comentariosOrdens = comentarios;
                                                        }, (error: any) => {
                                                                              this.toastr.error(`erro: ${error.message}`);
                                                                              console.error(error);
                                                                              this.spinner.hide();
                                                                            }, () => this.spinner.hide()
                          );
}

  saveOrdem()
  {
    this.spinner.show();

    this.osService.put(this.ordemSelecionada)
      .pipe(takeUntil(this.unsubscriber))
      .subscribe(
        () => {
                this.carregarOrdem();
                this.toastr.success('Ordem salva com sucesso!');
              }, (error: any) => {
                                  this.toastr.error(`Erro: Ordem não pode ser salva!`);
                                  console.error(error.message);
                                  this.spinner.hide();
                                }, () => this.spinner.hide()
            );
  }

  saveComentario()
  {
    this.spinner.show();

    const ordemId = this.route.snapshot.paramMap.get('id');
    this.comentario = {...this.comentarioFormNovo.value};

    this.osService.posComentario(Number(ordemId), this.comentario)
      .pipe(takeUntil(this.unsubscriber))
      .subscribe(
        () => {
                this.carregaComentariosOrdens();
                this.toastr.success('Comentario salvo com sucesso!');
                this.modalRef.hide();
              }, (error: any) => {
                                  this.toastr.error(`Erro: Comentario não pode ser salvo!`);
                                  console.error(error.message);
                                  this.spinner.hide();
                                }, () => this.spinner.hide()
            );
  }

  finalizarOrdem(ordemServico: OrdemServico) {
    this.osService.finalizar(ordemServico)
        .pipe(takeUntil(this.unsubscriber))
        .subscribe(
          (resp) => {
            this.carregarOrdem();
            this.toastr.success('Ordem salva com sucesso!');
          }, (error: any) => {
            this.toastr.error(`Erro: Ordem não pode ser salva!`);
            console.error(error.message);
            this.spinner.hide();
          }, () => this.spinner.hide()
        );
  }

  openModal(template: TemplateRef<any>)
  {
    this.criarForm();
    this.modalRef = this.modalService.show(template);
  }

  closeModal()
  {
    this.modalRef.hide();
  }

  voltar()
  {
    this.router.navigate(['/ordens-servico']);
  }

  ngOnDestroy(): void
  {
    this.unsubscriber.next();
    this.unsubscriber.complete();
  }
}

