package com.lduran.osworks.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.osworks.domain.model.Cliente;

@RestController
public class ClienteController
{
	@GetMapping("/clientes")
	public List<Cliente> listar()
	{
		var cliente1 = new Cliente();
		cliente1.setId(1L);
		cliente1.setNome("João da Silva");
		cliente1.setEmail("joao.da.silva@algaworks.com.br");
		cliente1.setTelefone("(12) 99999-1111");

		var cliente2 = new Cliente();
		cliente2.setId(2L);
		cliente2.setNome("Maria do Carmo");
		cliente2.setEmail("maria.do.carmo@algaworks.com.br");
		cliente2.setTelefone("(12) 99999-2222");

		return Arrays.asList(cliente1, cliente2);
	}
}
