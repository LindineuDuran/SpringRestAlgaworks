package com.lduran.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lduran.osworks.domain.exception.EntidadeNãoEncontradaException;
import com.lduran.osworks.domain.exception.NegocioException;
import com.lduran.osworks.domain.model.Cliente;
import com.lduran.osworks.domain.model.Comentario;
import com.lduran.osworks.domain.model.OrdemServico;
import com.lduran.osworks.domain.model.StatusOrdemServico;
import com.lduran.osworks.domain.repository.ClienteRepository;
import com.lduran.osworks.domain.repository.ComentarioRepository;
import com.lduran.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService
{
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ComentarioRepository comentarioRepository;

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

	public void finalizar(Long ordemServicoId)
	{
		OrdemServico ordemServico = this.buscar(ordemServicoId);

		ordemServico.finalizar();

		this.ordemServicoRepository.save(ordemServico);
	}

	public Comentario adicionarComentario(Long ordemServicoId, String descricao)
	{
		OrdemServico ordemServico = this.buscar(ordemServicoId);

		Comentario comentario = new Comentario();
		comentario.setOrdemServico(ordemServico);
		comentario.setDescricao(descricao);
		comentario.setDataEnvio(OffsetDateTime.now());

		return this.comentarioRepository.save(comentario);
	}

	private OrdemServico buscar(Long ordemServicoId)
	{
		return this.ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNãoEncontradaException("Ordem de Serviço não encontrada."));
	}
}
