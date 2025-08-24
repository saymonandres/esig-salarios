package br.com.saymon.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

import br.com.saymon.dao.UsuarioDAO;
import br.com.saymon.exception.NegocioException;
import br.com.saymon.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {

    @Inject
    private UsuarioDAO usuarioDAO;

    @Transactional
    public void criar(Usuario usuario, String senha) {
    	try {
    		usuario.setSenhaHash(BCrypt.hashpw(senha, BCrypt.gensalt()));
            usuarioDAO.salvar(usuario);
        } catch (PersistenceException e) {
            throw new NegocioException("Não foi possível criar o usuário.");
        }
    }

    @Transactional
    public void excluir(Usuario u) {
        try {
        	usuarioDAO.excluir(u);
        } catch (PersistenceException e) {
            throw new NegocioException("Não foi possível excluir o usuário.");
        }
    }

    public Optional<Usuario> autenticar(String login, String senha) {
        var opt = usuarioDAO.buscarPorLogin(login);
        if (opt.isPresent() && BCrypt.checkpw(senha, opt.get().getSenhaHash())) {
        	return opt;
        }
        return Optional.empty();
    }
    
    public Usuario buscarPorLogin(String login) {
        var opt = usuarioDAO.buscarPorLogin(login);
        if (opt.isPresent()) {
        	return opt.get();
        }
        return null;
    }
}
