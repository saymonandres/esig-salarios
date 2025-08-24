package br.com.saymon.view;

import br.com.saymon.model.Usuario;
import br.com.saymon.service.UsuarioService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@Named
@SessionScoped
public class AuthView implements Serializable {

	private static final long serialVersionUID = -5853665016857536521L;

	@Inject
    private UsuarioService usuarioService;

    private String login;
    private String senha;
    private Usuario usuarioLogado;

    public String doLogin() {
        Optional<Usuario> opt = usuarioService.autenticar(login, senha);
        if (opt.isPresent()) {
            usuarioLogado = opt.get();
            return "/pessoa_salario_consolidado_lista.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao logar.", "Usuário ou senha incorretos."));
        return null;
    }

    public String doLogout() {
        usuarioLogado = null;
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean isLogado() { 
    	return usuarioLogado != null; 
    }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
    
    
    
}
