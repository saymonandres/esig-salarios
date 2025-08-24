package br.com.saymon.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cargo_vencimentos", schema = "public")
public class CargoVencimentos implements Serializable {

	private static final long serialVersionUID = -1159411337421895420L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vencimento_id")
    private Vencimentos vencimentos;
	
	public CargoVencimentos() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Vencimentos getVencimentos() {
		return vencimentos;
	}

	public void setVencimentos(Vencimentos vencimentos) {
		this.vencimentos = vencimentos;
	}
	
	

}
