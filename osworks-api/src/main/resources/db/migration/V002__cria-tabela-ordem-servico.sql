drop table if exists ordem_servico;

create table ordem_servico
(
  id bigint not null auto_increment,
  cliente_id bigint not null,
  descricao text not null,
  preco decimal(10,2) not null,
  status varchar(20) not null,
  data_abertura datetime not null,
  data_finalizacao datetime,
  primary key (id)
);

alter table ordem_servico add constraint fk_ordem_servico_cliente
foreign key (cliente_id) references cliente (id);

insert into ordem_servico (cliente_id, descricao, preco, status, data_abertura, data_finalizacao)
       values (1,'Reparo de notebook HP. Fonte não carrega',150.00, 'ABERTA','2021-01-28 19:33:58',NULL),
              (2,'Reparo de notebook HP. Cliente diz que não liga.',300.50, 'ABERTA','2021-01-28 18:24:43',NULL),
	          (5,'Reparo de notebook Apple. Tela quebrada',800.70, 'ABERTA','2021-01-28 19:15:49',NULL);