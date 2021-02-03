package com.lduran.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.osworks.api.model.ComentarioInput;
import com.lduran.osworks.api.model.ComentarioModel;
import com.lduran.osworks.domain.model.Comentario;
import com.lduran.osworks.domain.repository.ComentarioRepository;
import com.lduran.osworks.domain.service.GestaoOrdemServicoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController
{
	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Retorna uma lista de comentários")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista de comentários") })
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<ComentarioModel> listar()
	{
		return this.toCollectionModel(this.comentarioRepository.findAll());
	}

	@ApiOperation(value = "Busca um comentário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um comentário") })
	@RequestMapping(value = "/{comentarioId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ComentarioModel> buscar(@PathVariable Long comentarioId)
	{
		Optional<Comentario> comentario = this.comentarioRepository.findById(comentarioId);
		if(comentario.isPresent())
		{
			return ResponseEntity.ok(this.toModel(comentario.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Cadastra um novo comentário")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Comentário novo cadastrado") })
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
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
