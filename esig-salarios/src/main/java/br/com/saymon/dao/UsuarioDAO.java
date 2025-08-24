package br.com.saymon.dao;

import java.util.Optional;

import br.com.saymon.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioDAO extends GenericDAO<Usuario> {

	public UsuarioDAO() {
        setClasse(Usuario.class);
    }

    public Optional<Usuario> buscarPorLogin(String login) {
        return em.createQuery("SELECT u FROM Usuario u JOIN FETCH u.pessoa WHERE u.pessoa.usuario = :login", Usuario.class)
                .setParameter("login", login)
                .getResultStream().findFirst();
    }
}
