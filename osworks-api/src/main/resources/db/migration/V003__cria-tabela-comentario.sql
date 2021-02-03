drop table if exists comentario;

create table comentario
(
  id bigint not null auto_increment,
  ordem_servico_id bigint not null,
  descricao text not null,
  data_envio datetime not null,
  primary key (id)
);

alter table comentario add constraint fk_comentario_ordem_servico foreign key (ordem_servico_id) references ordem_servico (id);

insert into comentario (ordem_servico_id, descricao, data_envio)
       values (2,'Placa mãe foi substituída.','2021-01-29 14:18:52'),
	          (3,'Troca da tela','2021-01-29 14:29:48');