package com.lduran.osworks.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ComentarioInput
{
	@NotBlank
	private String descricao;
}
