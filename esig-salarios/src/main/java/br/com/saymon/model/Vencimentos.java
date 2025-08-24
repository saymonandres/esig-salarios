package br.com.saymon.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vencimentos", schema = "public")
public class Vencimentos implements Serializable {
	
	private static final long serialVersionUID = -3815511751774208741L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@Column(name = "descricao", length = 255, nullable = false)
	private String descricao;
	
	@Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 10, nullable = false)
    private TipoVencimento tipo;
	
	public Vencimentos() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoVencimento getTipo() {
		return tipo;
	}

	public void setTipo(TipoVencimento tipo) {
		this.tipo = tipo;
	}
	
	
}
