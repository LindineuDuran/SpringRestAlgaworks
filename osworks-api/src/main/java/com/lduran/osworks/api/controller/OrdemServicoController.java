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

import com.lduran.osworks.api.model.OrdemServicoInput;
import com.lduran.osworks.api.model.OrdemServicoModel;
import com.lduran.osworks.domain.model.OrdemServico;
import com.lduran.osworks.domain.repository.OrdemServicoRepository;
import com.lduran.osworks.domain.service.GestaoOrdemServicoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController
{
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Retorna uma lista de ordens de serviço")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma lista de ordens de serviço") })
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<OrdemServicoModel> listar()
	{
		return this.toCollectionModel(this.ordemServicoRepository.findAll());
	}

	@ApiOperation(value = "Busca uma ordem de serviço pelo id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma ordem de serviço") })
	@RequestMapping(value = "/{ordemServicoId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId)
	{
		Optional<OrdemServico> ordemServico = this.ordemServicoRepository.findById(ordemServicoId);
		if (ordemServico.isPresent())
		{
			OrdemServicoModel ordemServicoModel = this.toModel(ordemServico.get());
			return ResponseEntity.ok(ordemServicoModel);
		}

		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Retorna uma lista de ordens de serviço pelo id do cliente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna uma lista de ordens de serviço pelo id do cliente") })
	@RequestMapping(value = "/buscando-por-cliente-id/{clienteId}", method = RequestMethod.GET, produces = "application/json")
	public List<OrdemServicoModel> buscarPorCLienteId(@PathVariable Long clienteId)
	{
		return this.toCollectionModel(this.gestaoOrdemServicoService.findOrdemServicoByClienteId(clienteId));
	}

	@ApiOperation(value = "Cadastra uma nova ordem de serviço")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra uma nova ordem de serviço") })
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput)
	{
		OrdemServico ordemServico = this.toEntity(ordemServicoInput);
		return this.toModel(this.gestaoOrdemServicoService.criar(ordemServico));
	}

	@ApiOperation(value = "Finaliza uma ordem de serviço")
	@RequestMapping(value = "/{ordemServicoId}/finalizacao", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId)
	{
		this.gestaoOrdemServicoService.finalizar(ordemServicoId);
	}

	@ApiOperation(value = "Elimina um cliente")
	@RequestMapping(value = "/{ordemServicoId}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Void> remover(@PathVariable Long ordemServicoId)
	{
		if (!this.ordemServicoRepository.existsById(ordemServicoId))
		{
			return ResponseEntity.notFound().build();
		}

		this.gestaoOrdemServicoService.excluir(ordemServicoId);

		return ResponseEntity.noContent().build();
	}

	private OrdemServicoModel toModel(OrdemServico ordemServico)
	{
		return this.modelMapper.map(ordemServico, OrdemServicoModel.class);
	}

	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico)
	{
		return ordensServico.stream().map(ordemServico -> this.toModel(ordemServico)).collect(Collectors.toList());
	}

	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput)
	{
		return this.modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
}
