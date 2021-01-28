package com.lduran.osworks.api.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrdemServicoInput
{
	@Valid
	@NotNull
	private ClienteIdInput cliente;

	@NotBlank
	private String descricao;

	@NotNull
	private BigDecimal preco;
}
