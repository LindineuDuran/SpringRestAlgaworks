package com.lduran.osworks.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lduran.osworks.domain.model.StatusOrdemServico;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdemServicoModel
{
	private Long id;
	private ClienteResumoModel cliente;
	private String descricao;
	private BigDecimal preco;
	StatusOrdemServico status;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private OffsetDateTime dataAbertura;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private OffsetDateTime dataFinalizacao;
}
