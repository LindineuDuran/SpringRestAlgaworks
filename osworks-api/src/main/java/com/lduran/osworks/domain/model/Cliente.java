package com.lduran.osworks.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank
	@Size(max=60)
	private String nome;

	@NotBlank
	@Email
	@Size(max=255)
	private String email;

	@NotBlank
	@Size(max=20)
	private String telefone;
}
