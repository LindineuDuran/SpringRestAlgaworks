package com.lduran.osworks.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lduran.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.osworks.domain.exception.NegocioException;
import com.lduran.osworks.domain.model.Cliente;
import com.lduran.osworks.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService
{
	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> buscarTodos()
	{
		return this.clienteRepository.findAll();
	}

	public Cliente buscar(Long clienteId)
	{
		return this.clienteRepository.findById(clienteId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado."));
	}

	public List<Cliente> buscarPorNome(String nome)
	{
		return this.clienteRepository.findByNome(nome);
	}

	public List<Cliente> buscarPorNomeParcial(String nome)
	{
		return this.clienteRepository.findByNomeContaining(nome);
	}

	public Cliente buscarPorEmail(String email)
	{
		return this.clienteRepository.findByEmail(email);
	}

	public boolean existe(Long clienteId)
	{
		Optional<Cliente> cliente = this.clienteRepository.findById(clienteId);
		if (cliente.isPresent())
		{
			return true;
		}

		return false;
	}

	public Cliente salvar(Cliente cliente)
	{
		Cliente clienteExistente = this.clienteRepository.findByEmail(cliente.getEmail());

		if (clienteExistente != null && !clienteExistente.equals(cliente))
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
