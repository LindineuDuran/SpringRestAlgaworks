import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdemServicoNovoComponent } from './ordem-servico-novo.component';

describe('OrdemServicoNovoComponent', () => {
  let component: OrdemServicoNovoComponent;
  let fixture: ComponentFixture<OrdemServicoNovoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrdemServicoNovoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdemServicoNovoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
