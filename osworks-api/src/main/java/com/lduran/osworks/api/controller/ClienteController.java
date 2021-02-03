package com.lduran.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lduran.osworks.domain.model.Cliente;
import com.lduran.osworks.domain.repository.ClienteRepository;
import com.lduran.osworks.domain.service.CadastroClienteService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/clientes")
public class ClienteController
{
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CadastroClienteService cadastroCliente;

	@ApiOperation(value = "Retorna uma lista de clientes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista de clientes") })
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<Cliente> listar()
	{
		return this.clienteRepository.findAll();
	}

	@ApiOperation(value = "Busca um cliente pelo id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um cliente") })
	@RequestMapping(value = "/{clienteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId)
	{
		Optional<Cliente> cliente = this.clienteRepository.findById(clienteId);
		if(cliente.isPresent())
		{
			return ResponseEntity.ok(cliente.get());
		}

		return ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Busca um cliente pelo nome")
	@GetMapping("/buscando-nome/{nome}")
	public List<Cliente> buscarPorNome(@PathVariable String nome)
	{
		return this.clienteRepository.findByNome(nome);
	}

	@ApiOperation(value = "Busca um cliente pelo nome parcial")
	@GetMapping("/buscando-nome-parcial/{nome}")
	public List<Cliente> buscarPorNomeParcial(@PathVariable String nome)
	{
		return this.clienteRepository.findByNomeContaining(nome);
	}

	@ApiOperation(value = "Busca um cliente pelo e-mail")
	@GetMapping("/buscando-email/{email}")
	public Cliente buscarPorEmail(@PathVariable String email)
	{
		return this.clienteRepository.findByEmail(email);
	}

	@ApiOperation(value = "Cadastra um novo cliente")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cadastra um cliente novo") })
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente)
	{
		return this.cadastroCliente.salvar(cliente);
	}

	@ApiOperation(value = "Altera um cliente")
	@RequestMapping(value = "/{clienteId}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente)
	{
		if(!this.clienteRepository.existsById(clienteId))
		{
			return ResponseEntity.notFound().build();
		}

		cliente.setId(clienteId);
		cliente = this.cadastroCliente.salvar(cliente);

		return ResponseEntity.ok(cliente);
	}

	@ApiOperation(value = "Elimina um cliente")
	@RequestMapping(value = "/{clienteId}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId)
	{
		if (!this.clienteRepository.existsById(clienteId))
		{
			return ResponseEntity.notFound().build();
		}

		this.cadastroCliente.excluir(clienteId);

		return ResponseEntity.noContent().build();
	}
}
