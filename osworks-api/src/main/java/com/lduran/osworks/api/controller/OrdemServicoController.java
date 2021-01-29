package com.lduran.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.osworks.api.model.OrdemServicoInput;
import com.lduran.osworks.api.model.OrdemServicoModel;
import com.lduran.osworks.domain.model.OrdemServico;
import com.lduran.osworks.domain.repository.OrdemServicoRepository;
import com.lduran.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController
{
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping()
	public List<OrdemServicoModel> listar()
	{		
		return this.toCollectionModel(this.ordemServicoRepository.findAll());
	}

	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId)
	{
		Optional<OrdemServico> ordemServico = this.ordemServicoRepository.findById(ordemServicoId);
		if(ordemServico.isPresent())
		{
			OrdemServicoModel ordemServicoModel = this.toModel(ordemServico.get());
			return ResponseEntity.ok(ordemServicoModel);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput)
	{
		OrdemServico ordemServico = this.toEntity(ordemServicoInput);
		return this.toModel(this.gestaoOrdemServicoService.criar(ordemServico));
	}

	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId)
	{
		this.gestaoOrdemServicoService.finalizar(ordemServicoId);
	}

	@DeleteMapping("/{ordemServicoId}")
	public ResponseEntity<Void> remover(@PathVariable Long ordemServicoId)
	{
		if(!this.ordemServicoRepository.existsById(ordemServicoId))
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
		return ordensServico.stream()
				.map(ordemServico -> this.toModel(ordemServico))
				.collect(Collectors.toList());
	}

	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput)
	{
		return this.modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
}
