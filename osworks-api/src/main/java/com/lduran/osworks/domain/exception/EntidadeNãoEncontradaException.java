package com.lduran.osworks.domain.exception;

public class EntidadeNãoEncontradaException extends NegocioException
{
	private static final long serialVersionUID = 1L;

	public EntidadeNãoEncontradaException(String message)
	{
		super(message);
	}
}
