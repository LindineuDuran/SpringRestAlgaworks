package com.lduran.osworks.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.lduran.osworks.domain.exception.NegocioException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdemServico
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToOne
	private Cliente cliente;

	private String descricao;

	private BigDecimal preco;

	@Enumerated(EnumType.STRING)
	private StatusOrdemServico status;

	private OffsetDateTime dataAbertura;

	private OffsetDateTime dataFinalizacao;

	@OneToMany(mappedBy = "ordemServico")
	private List<Comentario> comentarios = new ArrayList<>();

	private boolean podeSerFinalizada()
	{
		return StatusOrdemServico.ABERTA.equals(this.getStatus());
	}

	private boolean naoPodeSerFinalizada()
	{
		return !StatusOrdemServico.ABERTA.equals(this.getStatus());
	}

	public void finalizar()
	{
		if(this.naoPodeSerFinalizada())
		{
			throw new NegocioException("Ordem de serviço não pode ser finalizada");
		}

		this.setStatus(StatusOrdemServico.FINALIZADA);
		this.setDataFinalizacao(OffsetDateTime.now());
	}
}
