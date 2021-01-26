package com.lduran.osworks.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Problema
{
	private int status;
	private LocalDateTime dataHora;
	private String titulo;
	private List<Campo> campos;
}
