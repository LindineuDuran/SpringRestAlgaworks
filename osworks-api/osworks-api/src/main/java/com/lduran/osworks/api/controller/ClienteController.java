package com.lduran.osworks.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.osworks.domain.model.Cliente;
import com.lduran.osworks.domain.repository.ClienteRepository;

@RestController
public class ClienteController
{
	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping("/clientes")
	public List<Cliente> listar()
	{		
		return this.clienteRepository.findAll();
	}

	@GetMapping("/clientes/buscando-nome")
	public List<Cliente> buscarPorNome()
	{
		return this.clienteRepository.findByNome("Maria de Gouvea");
	}

	@GetMapping("/clientes/buscando-nome-parcial")
	public List<Cliente> buscarPorNomeParcial()
	{
		return this.clienteRepository.findByNomeContaining("João");
	}

	@GetMapping("/clientes/{clienteId}")
	public Cliente buscar(@PathVariable Long clienteId)
	{
		return this.clienteRepository.findById(clienteId).orElse(null);
	}
}
