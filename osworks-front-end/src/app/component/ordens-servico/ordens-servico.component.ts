import { Component, OnInit, TemplateRef, OnDestroy } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

import { OrdemServico } from '../../models/Ordem-Servico';
import { OrdemServicoService } from 'src/app/services/ordem-servico.service';
import { Comentario } from '../../models/Comentario';

@Component({
  selector: 'app-ordens-servico',
  templateUrl: './ordens-servico.component.html',
  styleUrls: ['./ordens-servico.component.css']
})
export class OrdensServicoComponent implements OnInit, OnDestroy
{
  public modalRef!: BsModalRef;
  public titulo = 'Ordens de Serviço';
  public ordemSelecionada!: OrdemServico;
  public comentariosOrdens!: Comentario[];
  private unsubscriber = new Subject();

  public ordens!: OrdemServico[];

  constructor(private router: Router,
              private modalService: BsModalService,
              private osService: OrdemServicoService,
              private toastr: ToastrService,
              private spinner: NgxSpinnerService) { }

  ngOnInit()
  {
    this.carregarOrdemServico();
  }

  carregarOrdemServico()
  {
    this.spinner.show();
    this.osService.getAll()
        .pipe(takeUntil(this.unsubscriber))
        .subscribe((ordens: OrdemServico[]) => {
                                                  this.ordens = ordens;
                                                  this.toastr.success('Ordens de Serviço foram carregadas com Sucesso!');
                                                }, (error: any) => {
                                                                      this.toastr.error('Ordens de Serviço não carregadas!');
                                                                      console.log(error);
                                                                    }, () => this.spinner.hide()
                  );
  }

  excluirOrdem(ordemId: number)
  {
    this.osService.delete(ordemId)
        .pipe(takeUntil(this.unsubscriber))
        .subscribe(
          (resp) => {
            this.carregarOrdemServico();
            this.toastr.success('Ordem de Serviço excluída com sucesso!');
          }, (error: any) => {
            this.toastr.error(`Erro: Ordem de Serviço não pode ser excluída!`);
            console.error(error.message);
            this.spinner.hide();
          }, () => this.spinner.hide()
        );
  }

  carregaComentariosOrdens(template: TemplateRef<any>, id: number)
  {
    this.spinner.show();
    this.osService.getComentarios(id)
                  .pipe(takeUntil(this.unsubscriber))
                  .subscribe((comentarios: Comentario[]) => {
                                                            this.comentariosOrdens = comentarios;
                                                            this.modalRef = this.modalService.show(template);
                                                          }, (error: any) => {
                                                                                this.toastr.error(`erro: ${error.message}`);
                                                                                console.error(error);
                                                                                this.spinner.hide();
                                                                              }, () => this.spinner.hide()
                            );
  }

  openModal(template: TemplateRef<any>, ordemId: number)
  {
    this.carregaComentariosOrdens(template, ordemId);
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
