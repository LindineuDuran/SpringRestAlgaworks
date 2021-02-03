package com.lduran.osworks.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lduran.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.lduran.osworks.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	protected ResponseEntity<Object> handleEntidadeNãoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request)
	{
		var status = HttpStatus.NOT_FOUND;

		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());

		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	protected ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request)
	{
		var status = HttpStatus.BAD_REQUEST;

		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());

		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		var campos = new ArrayList<Campo>();

		for(ObjectError error: ex.getBindingResult().getAllErrors())
		{
			String nome = ((FieldError)error).getField();
			String mensagem = error.getDefaultMessage();

			campos.add(new Campo(nome, mensagem));
		}

		var problema = new Problema();

		problema.setStatus(status.value());

		problema.setTitulo("Um ou mais campos estão inválidos. "
				+ "Faça o preenchimento correto e tente novamente.");

		problema.setDataHora(OffsetDateTime.now());

		problema.setCampos(campos);

		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}
}
