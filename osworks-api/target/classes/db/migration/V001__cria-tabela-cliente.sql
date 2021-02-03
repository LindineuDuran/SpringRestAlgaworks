drop table if exists comentario;
drop table if exists ordem_servico;
drop table if exists cliente;

create table cliente
(
	id bigint not null auto_increment,
    nome varchar(60) not null,
    email varchar(255) not null,
    telefone varchar(20) not null,
    
    primary key (id)
);

insert into cliente (nome,email,telefone)
       values ('João da Silva','joao.da.silva@algaworks.com.br','(12) 99999-1111'),
			  ('Maria de Gouvea','maria.gouvea@algaworks','(21) 99999-2222'),
			  ('Paulo Fagundes','paulo.fagundes@algaworks.com.br','(35) 98877-3333'),
			  ('José Figueiredo','jose.figueiredo@algaworks.com.br','(24) 99999-4444'),
			  ('Clara dos Anjos','clara.dos.anjos@algaworks.com.br','(35) 98789-1122'),
			  ('Daniele Martins','daniele.martins@algaworks.com.br','(11) 97877-4321');