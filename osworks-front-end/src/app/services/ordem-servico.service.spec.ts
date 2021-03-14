/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { OrdemServicoService } from './ordem-servico.service';

describe('Service: OrdemServico', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OrdemServicoService]
    });
  });

  it('should ...', inject([OrdemServicoService], (service: OrdemServicoService) => {
    expect(service).toBeTruthy();
  }));
});
