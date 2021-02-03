package com.lduran.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lduran.osworks.domain.exception.NegocioException;
import com.lduran.osworks.domain.model.Cliente;
import com.lduran.osworks.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService
{
	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente salvar(Cliente cliente)
	{
		Cliente clienteExistente = this.clienteRepository.findByEmail(cliente.getEmail());

		if(clienteExistente != null && !clienteExistente.equals(cliente))
		{
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail");
		}

		return this.clienteRepository.save(cliente);
	}

	public void excluir(Long clienteId)
	{
		this.clienteRepository.deleteById(clienteId);
	}
}
