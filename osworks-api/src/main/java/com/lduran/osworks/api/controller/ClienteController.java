package com.lduran.osworks.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.osworks.domain.model.Cliente;
import com.lduran.osworks.domain.model.OrdemServico;
import com.lduran.osworks.domain.service.CadastroClienteService;
import com.lduran.osworks.domain.service.GestaoOrdemServicoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/clientes")
public class ClienteController
{
	@Autowired
	private CadastroClienteService cadastroCliente;

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;

	@ApiOperation(value = "Retorna uma lista de clientes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista de clientes") })
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Cliente>> listar()
	{
		return ResponseEntity.ok(this.cadastroCliente.buscarTodos());
	}

	@ApiOperation(value = "Busca um cliente pelo id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um cliente") })
	@RequestMapping(value = "/{clienteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId)
	{
		Cliente cliente = this.cadastroCliente.buscar(clienteId);
		return ResponseEntity.ok(cliente);
	}

	@ApiOperation(value = "Busca um cliente pelo nome")
	@GetMapping("/buscando-nome/{nome}")
	public ResponseEntity<List<Cliente>> buscarPorNome(@PathVariable String nome)
	{
		return ResponseEntity.ok(this.cadastroCliente.buscarPorNome(nome));
	}

	@ApiOperation(value = "Busca um cliente pelo nome parcial")
	@GetMapping("/buscando-nome-parcial/{nome}")
	public ResponseEntity<List<Cliente>> buscarPorNomeParcial(@PathVariable String nome)
	{
		return ResponseEntity.ok(this.cadastroCliente.buscarPorNomeParcial(nome));
	}

	@ApiOperation(value = "Busca um cliente pelo e-mail")
	@GetMapping("/buscando-email/{email}")
	public ResponseEntity<Cliente> buscarPorEmail(@PathVariable String email)
	{
		return ResponseEntity.ok(this.cadastroCliente.buscarPorEmail(email));
	}

	@ApiOperation(value = "Cadastra um novo cliente")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra um cliente novo") })
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cliente> adicionar(@Valid @RequestBody Cliente cliente)
	{
		return ResponseEntity.ok(this.cadastroCliente.salvar(cliente));
	}

	@ApiOperation(value = "Altera um cliente")
	@RequestMapping(value = "/{clienteId}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente)
	{
		if (!this.cadastroCliente.existe(clienteId))
		{
			return ResponseEntity.notFound().build();
		}

		cliente.setId(clienteId);
		cliente = this.cadastroCliente.salvar(cliente);

		return ResponseEntity.ok(cliente);
	}

	@ApiOperation(value = "Elimina um cliente")
	@RequestMapping(value = "/{clienteId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remover(@PathVariable Long clienteId)
	{
		if (!this.cadastroCliente.existe(clienteId))
		{
			return ResponseEntity.notFound().build();
		}

		List<OrdemServico> ordensServico = this.gestaoOrdemServicoService.findOrdemServicoByClienteId(clienteId);
		if (ordensServico != null)
		{
			ordensServico.forEach(ordem -> this.gestaoOrdemServicoService.excluir(ordem.getId()));
		}

		this.cadastroCliente.excluir(clienteId);

		return ResponseEntity.noContent().build();
	}
}
