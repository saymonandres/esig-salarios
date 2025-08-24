package br.com.saymon.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "pessoa", schema = "public")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = -4660600782445779837L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@Column(name = "nome", length = 255, nullable = false)
	private String nome;
	
	@Column(name = "cidade", length = 255, nullable = false)
	private String cidade;
	
	@Column(name = "email", length = 255, nullable = false)
	private String email;
	
	@Column(name = "cep", length = 15, nullable = false)
	private String cep;
	
	@Column(name = "endereco", length = 500, nullable = false)
	private String endereco;
	
	@Column(name = "pais", length = 255, nullable = false)
	private String pais;
	
	@Column(name = "usuario", length = 50, nullable = false)
	private String usuario;
	
	@Column(name = "telefone", length = 15, nullable = false)
	private String telefone;
	
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
	
	public Pessoa() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", cidade=" + cidade + ", email=" + email + ", cep=" + cep
				+ ", endereco=" + endereco + ", pais=" + pais + ", usuario=" + usuario + ", telefone=" + telefone
				+ ", dataNascimento=" + dataNascimento + ", cargo=" + cargo + "]";
	}
	
	

}

