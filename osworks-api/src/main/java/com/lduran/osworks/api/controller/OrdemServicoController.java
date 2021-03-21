package com.lduran.osworks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.osworks.api.model.OrdemServicoInput;
import com.lduran.osworks.api.model.OrdemServicoModel;
import com.lduran.osworks.domain.model.OrdemServico;
import com.lduran.osworks.domain.service.GestaoOrdemServicoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/ordens-servico")
public class OrdemServicoController
{
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "Retorna uma lista de ordens de serviço")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma lista de ordens de serviço") })
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<OrdemServicoModel>> listar()
	{
		return ResponseEntity.ok(this.toCollectionModel(this.gestaoOrdemServicoService.buscarTodas()));
	}

	@ApiOperation(value = "Busca uma ordem de serviço pelo id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma ordem de serviço") })
	@RequestMapping(value = "/{ordemServicoId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId)
	{
		OrdemServico ordemServico = this.gestaoOrdemServicoService.buscar(ordemServicoId);
		OrdemServicoModel ordemServicoModel = this.toModel(ordemServico);
		return ResponseEntity.ok(ordemServicoModel);
	}

	@ApiOperation(value = "Retorna uma lista de ordens de serviço pelo id do cliente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna uma lista de ordens de serviço pelo id do cliente") })
	@RequestMapping(value = "/buscando-por-cliente-id/{clienteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<OrdemServicoModel>> buscarPorCLienteId(@PathVariable Long clienteId)
	{
		return ResponseEntity
				.ok(this.toCollectionModel(this.gestaoOrdemServicoService.findOrdemServicoByClienteId(clienteId)));
	}

	@ApiOperation(value = "Cadastra uma nova ordem de serviço")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra uma nova ordem de serviço") })
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<OrdemServicoModel> criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput)
	{
		OrdemServico ordemServico = this.toEntity(ordemServicoInput);
		return ResponseEntity.ok(this.toModel(this.gestaoOrdemServicoService.criar(ordemServico)));
	}

	@ApiOperation(value = "Altera uma ordem de serviço")
	@RequestMapping(value = "/{ordemServicoId}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<OrdemServicoModel> atualizar(@Valid @PathVariable Long ordemServicoId,
			@RequestBody OrdemServicoInput ordemServicoInput)
	{
		OrdemServico ordemServicoNova = this.gestaoOrdemServicoService.atualizar(ordemServicoId, ordemServicoInput);
		return ResponseEntity.ok(this.toModel(ordemServicoNova));
	}

	@ApiOperation(value = "Finaliza uma ordem de serviço")
	@RequestMapping(value = "/{ordemServicoId}/finalizacao", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> finalizar(@PathVariable Long ordemServicoId)
	{
		this.gestaoOrdemServicoService.finalizar(ordemServicoId);

		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Elimina uma ordem de serviço")
	@RequestMapping(value = "/{ordemServicoId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remover(@PathVariable Long ordemServicoId)
	{
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
