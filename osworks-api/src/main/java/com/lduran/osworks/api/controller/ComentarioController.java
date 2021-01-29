package com.lduran.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.osworks.api.model.ComentarioInput;
import com.lduran.osworks.api.model.ComentarioModel;
import com.lduran.osworks.domain.exception.EntidadeNãoEncontradaException;
import com.lduran.osworks.domain.model.Comentario;
import com.lduran.osworks.domain.model.OrdemServico;
import com.lduran.osworks.domain.repository.ComentarioRepository;
import com.lduran.osworks.domain.repository.OrdemServicoRepository;
import com.lduran.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController
{
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long ordemServicoId)
	{
		OrdemServico ordemServico = this.ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNãoEncontradaException("Ordem de Serviço não encontrada."));

		return this.toCollectionModel(ordemServico.getComentarios());
	}

	@GetMapping("/{comentarioId}")
	public ResponseEntity<ComentarioModel> buscar(@PathVariable Long comentarioId)
	{
		Optional<Comentario> comentario = this.comentarioRepository.findById(comentarioId);
		if(comentario.isPresent())
		{
			return ResponseEntity.ok(this.toModel(comentario.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable Long ordemServicoId, @Valid @RequestBody ComentarioInput comentarioInput)
	{		
		Comentario comentario = this.gestaoOrdemServicoService.adicionarComentario(ordemServicoId, comentarioInput.getDescricao());
		return this.toModel(comentario);
	}

	private ComentarioModel toModel(Comentario comentario)
	{
		return this.modelMapper.map(comentario, ComentarioModel.class);
	}

	private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios)
	{
		return comentarios.stream()
				.map(comentario -> this.toModel(comentario))
				.collect(Collectors.toList());
	}
}
