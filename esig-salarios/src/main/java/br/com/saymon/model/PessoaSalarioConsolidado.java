package br.com.saymon.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa_salario_consolidado", schema = "public")
public class PessoaSalarioConsolidado implements Serializable {

	private static final long serialVersionUID = 4184867047797019858L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Long id;

	@Column(name = "nome_pessoa", length = 255, nullable = false)
	private String nomePessoa;
	
	@Column(name = "nome_cargo", length = 255, nullable = false)
	private String nomeCargo;
	
	@Column(name = "salario", precision = 10, scale = 2, nullable = false)
    private BigDecimal salario;
	
	@Column(name = "pessoa_id", length = 255, nullable = false)
    private Long idPessoa;
	
	public PessoaSalarioConsolidado() {
		
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getNomeCargo() {
		return nomeCargo;
	}

	public void setNomeCargo(String nomeCargo) {
		this.nomeCargo = nomeCargo;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}


	
}

