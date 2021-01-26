package com.lduran.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@GetMapping("/clientes/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId)
	{
		Optional<Cliente> cliente = this.clienteRepository.findById(clienteId);
		if(cliente.isPresent())
		{
			return ResponseEntity.ok(cliente.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente)
	{		
		return this.clienteRepository.save(cliente);
	}

	@PutMapping("/clientes/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente)
	{
		if(!this.clienteRepository.existsById(clienteId))
		{
			return ResponseEntity.notFound().build();
		}

		cliente.setId(clienteId);
		cliente = this.clienteRepository.save(cliente);
		return ResponseEntity.ok(cliente);
	}

	@DeleteMapping("/clientes/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId)
	{
		if(!this.clienteRepository.existsById(clienteId))
		{
			return ResponseEntity.notFound().build();
		}

		this.clienteRepository.deleteById(clienteId);

		return ResponseEntity.noContent().build();
	}
}
