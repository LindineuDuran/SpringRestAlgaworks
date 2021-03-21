package com.lduran.osworks.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lduran.osworks.api.model.OrdemServicoInput;
import com.lduran.osworks.domain.exception.EntidadeNaoEncontradaException;
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

	public List<OrdemServico> buscarTodas()
	{
		return this.ordemServicoRepository.findAll();
	}

	public OrdemServico buscar(Long ordemServicoId)
	{
		return this.ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de Serviço não encontrada."));
	}

	public List<Comentario> buscarTodosComentariosDaOrdem(Long ordemServicoId)
	{
		List<Comentario> comentarios = this.comentarioRepository.findAll().stream()
				.filter(comentario -> comentario.getOrdemServico().getId().equals(ordemServicoId))
				.collect(Collectors.toList());

		return comentarios;
	}

	public Comentario buscarComentario(Long ordemServicoId, Long comentarioId)
	{
		List<Comentario> comentarios = buscarTodosComentariosDaOrdem(ordemServicoId);
		comentarios = comentarios.stream().filter(c -> c.getId().equals(comentarioId))
				.collect(Collectors.<Comentario>toList());

		if (comentarios.isEmpty())
		{
			throw new EntidadeNaoEncontradaException("Comentário não encontrado.");
		}

		return comentarios.get(0);
	}

	public OrdemServico criar(OrdemServico ordemServico)
	{
		Cliente cliente = buscarCliente(ordemServico.getCliente().getId());

		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());

		return this.ordemServicoRepository.save(ordemServico);
	}

	public OrdemServico atualizar(Long ordemServicoId, OrdemServicoInput ordemServicoInput)
	{
		Optional<OrdemServico> ordemServico = this.ordemServicoRepository.findById(ordemServicoId);
		if (ordemServico.isPresent())
		{
			OrdemServico ordemServicoNova = ordemServico.get();
			ordemServicoNova.getCliente().setId(ordemServicoInput.getCliente().getId());
			ordemServicoNova.setDescricao(ordemServicoInput.getDescricao());
			ordemServicoNova.setPreco(ordemServicoInput.getPreco());
			ordemServicoNova = this.ordemServicoRepository.save(ordemServicoNova);

			return ordemServicoNova;
		}
		else
		{
			throw new EntidadeNaoEncontradaException("Ordem de Serviço não encontrada.");
		}
	}

	public void excluir(Long ordemServicoId)
	{
		List<Comentario> comentarios = findComentarioByOrdemServicoId(ordemServicoId);
		if (comentarios != null)
		{
			comentarios.forEach(com ->
			{
				comentarioRepository.deleteById(com.getId());
			});
		}

		this.ordemServicoRepository.deleteById(ordemServicoId);
	}

	public void excluirComentario(Long comentarioId)
	{
		comentarioRepository.deleteById(comentarioId);
	}

	public void finalizar(Long ordemServicoId)
	{
		OrdemServico ordemServico = this.buscar(ordemServicoId);
		ordemServico.finalizar();
		this.ordemServicoRepository.save(ordemServico);
	}

	public Comentario adicionarComentario(Long ordemServicoId, String descricao)
	{
		OrdemServico ordemServico = this.ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem não encontrada."));

		Comentario comentario = new Comentario();
		comentario.setOrdemServico(ordemServico);
		comentario.setDescricao(descricao);
		comentario.setDataEnvio(OffsetDateTime.now());

		return this.comentarioRepository.save(comentario);
	}

	public List<OrdemServico> findOrdemServicoByClienteId(Long clienteId)
	{
		Cliente cliente = buscarCliente(clienteId);

		List<OrdemServico> ordensServico = this.ordemServicoRepository.findAll();

		return ordensServico.stream().filter(ordemServico -> ordemServico.getCliente().equals(cliente))
				.collect(Collectors.toList());
	}

	public List<Comentario> findComentarioByOrdemServicoId(Long ordemServicoId)
	{
		OrdemServico ordemServico = this.ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new NegocioException("Ordem de Servico não encontrada."));

		List<Comentario> comentarios = this.comentarioRepository.findAll();

		return comentarios.stream().filter(comentario -> comentario.getOrdemServico().equals(ordemServico))
				.collect(Collectors.toList());
	}

	private Cliente buscarCliente(Long clienteId)
	{
		return this.clienteRepository.findById(clienteId)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado."));
	}
}
