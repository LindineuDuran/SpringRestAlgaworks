import { Cliente } from './Cliente';

export class OrdemServico
{
  id!: number;
  cliente!:  Cliente;
  descricao!:  string;
  preco!: number;
  status!:  string;
  dataAbertura!: Date;
  dataFinalizacao!: Date;
}
