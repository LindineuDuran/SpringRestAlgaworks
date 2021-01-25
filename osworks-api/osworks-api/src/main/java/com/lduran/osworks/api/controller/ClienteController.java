package com.lduran.osworks.api.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.osworks.domain.model.Cliente;
import com.lduran.osworks.domain.repository.ClienteRepository;

@RestController
public class ClienteController
{
	@Autowired
	private ClienteRepository clienteRepository;

	@PersistenceContext
	private EntityManager manager;

	@GetMapping("/clientes")
	public List<Cliente> listar()
	{		
		return this.clienteRepository.findAll();
	}
}
