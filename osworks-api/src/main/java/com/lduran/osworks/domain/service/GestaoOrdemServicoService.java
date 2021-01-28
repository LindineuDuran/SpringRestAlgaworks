package com.lduran.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lduran.osworks.domain.exception.NegocioException;
import com.lduran.osworks.domain.model.Cliente;
import com.lduran.osworks.domain.model.OrdemServico;
import com.lduran.osworks.domain.model.StatusOrdemServico;
import com.lduran.osworks.domain.repository.ClienteRepository;
import com.lduran.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService
{
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public OrdemServico criar(OrdemServico ordemServico)
	{		
		Cliente cliente = this.clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado."));

		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());

		return this.ordemServicoRepository.save(ordemServico);
	}

	public void excluir(Long ordemServicoId)
	{
		this.ordemServicoRepository.deleteById(ordemServicoId);
	}
}
