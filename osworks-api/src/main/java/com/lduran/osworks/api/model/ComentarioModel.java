package com.lduran.osworks.api.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioModel
{
	private Long id;
	private String descricao;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private OffsetDateTime dataEnvio;
}
