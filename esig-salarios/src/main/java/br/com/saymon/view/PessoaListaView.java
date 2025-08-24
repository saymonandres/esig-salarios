package br.com.saymon.view;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import br.com.saymon.exception.NegocioException;
import br.com.saymon.model.Cargo;
import br.com.saymon.model.Pessoa;
import br.com.saymon.model.PessoaSalarioConsolidado;
import br.com.saymon.model.Usuario;
import br.com.saymon.service.CargoService;
import br.com.saymon.service.PessoaSalarioConsolidadoService;
import br.com.saymon.service.PessoaService;
import br.com.saymon.service.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ViewScoped
@Named("pessoaListaView")
public class PessoaListaView implements Serializable {
	
	private static final long serialVersionUID = 915093273227280204L;
	
	private PessoaSalarioConsolidado pessoa;
    private List<PessoaSalarioConsolidado> pessoas;

    @Inject
    private PessoaSalarioConsolidadoService pessoaSalarioConsolidadoService;
    
    @Inject
    private PessoaService pessoaService;
    
    @Inject
    private CargoService cargoService;
    
    @Inject 
    private UsuarioService usuarioService;
    
    private Pessoa pessoaSelecionada;
    private List<Cargo> listaCargos;
    
    private Usuario usuarioSelecionado;
    private String senha;
    
    private boolean emProcessamento = false;
    private String botaoCalcular = "Calcular/Recalcular Salários";

    @PostConstruct
    public void init() {
    	pessoas = pessoaSalarioConsolidadoService.listar();
    }
    
    public void calcularSalarios() {
        if (!this.emProcessamento) {
        	this.emProcessamento = true;
        	this.botaoCalcular = "Calculando Salários...";
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Iniciado!", "O recálculo de salários foi iniciado em segundo plano. A lista será atualizada em breve."));
            
            // abre thread
            CompletableFuture.runAsync(() -> {
            	this.pessoaSalarioConsolidadoService.calcularSalarios();
            	this.pessoas = this.pessoaSalarioConsolidadoService.listar();
            	this.emProcessamento = false;
            	this.botaoCalcular = "Calcular/Recalcular Salários";
            });
        }
    }

    public boolean isEmProcessamento() {
        return this.emProcessamento;
    }
    
    public void verificarProcessamento() {
    	if(!this.emProcessamento) {
    		FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Finalizado!", "O recálculo de salários foi finalizado com sucesso."));
    	}
    }
    
    // Métodos para o CRUD de Usuario
    public void novoUsuario() {
    	this.senha = null;
    	this.usuarioSelecionado = new Usuario();
    	this.usuarioSelecionado.setPessoa(this.pessoaSelecionada);
    }
    
    public void salvarUsuario() {
    	try {
    		this.usuarioService.criar(this.usuarioSelecionado, this.senha);
	    	this.usuarioSelecionado = this.usuarioService.buscarPorLogin(this.pessoaSelecionada.getUsuario());
	    } catch (NegocioException e) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
	    }
    }
    
    public void excluirUsuario() {
    	try {
    		this.usuarioSelecionado = this.usuarioService.buscarPorLogin(this.pessoaSelecionada.getUsuario());
    		this.usuarioService.excluir(this.usuarioSelecionado);
    		this.usuarioSelecionado = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário excluído com sucesso."));
        } catch (NegocioException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }
    
    // Métodos para o CRUD de Pessoa
    public void novaPessoa() {
    	this.pessoa = new PessoaSalarioConsolidado();
        this.pessoaSelecionada = new Pessoa();
        this.listaCargos = this.cargoService.listar();
    }
    
    public void editarPessoa(PessoaSalarioConsolidado pessoa) {
    	this.pessoa = pessoa;
    	this.pessoaSelecionada = this.pessoaService.buscarPorId(pessoa.getIdPessoa());
        this.listaCargos = this.cargoService.listar();
        this.usuarioSelecionado = this.usuarioService.buscarPorLogin(this.pessoaSelecionada.getUsuario());
    }
    
    public void salvarPessoa() {
        try {
        	this.pessoaService.salvar(this.pessoaSelecionada);
            
            // cria salario
            if(this.pessoa == null || this.pessoa.getId() == null) {
            	this.pessoa = new PessoaSalarioConsolidado();
            }
            this.pessoa.setIdPessoa(this.pessoaSelecionada.getId());
            this.pessoa.setNomePessoa(this.pessoaSelecionada.getNome());
            this.pessoa.setNomeCargo(this.pessoaSelecionada.getCargo().getNome());
            this.pessoa.setSalario(this.pessoaSalarioConsolidadoService.calcularSalarioPessoa(this.pessoaSelecionada));
            this.pessoaSalarioConsolidadoService.salvar(this.pessoa);
            
            this.pessoas = this.pessoaSalarioConsolidadoService.listar();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pessoa criada com sucesso."));
            
        } catch (NegocioException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }
    }

    public void excluirPessoa() {
    	try {
    		this.pessoaSelecionada = this.pessoaService.buscarPorId(this.pessoa.getIdPessoa());
    		this.pessoaService.excluir(this.pessoaSelecionada);
    		this.pessoaSalarioConsolidadoService.excluir(this.pessoa);
    		this.pessoas = this.pessoaSalarioConsolidadoService.listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pessoa excluído com sucesso."));
        } catch (NegocioException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }
    
    public List<Cargo> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<Cargo> listaCargos) {
		this.listaCargos = listaCargos;
	}

	public PessoaSalarioConsolidado getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaSalarioConsolidado pessoa) {
		this.pessoa = pessoa;
	}

	public List<PessoaSalarioConsolidado> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaSalarioConsolidado> pessoas) {
		this.pessoas = pessoas;
	}

	public String getBotaoCalcular() {
		return botaoCalcular;
	}

	public void setBotaoCalcular(String botaoCalcular) {
		this.botaoCalcular = botaoCalcular;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
