package br.com.saymon.dao;

import java.util.List;

import br.com.saymon.model.Pessoa;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaDAO extends GenericDAO<Pessoa> {
	
	public PessoaDAO() {
        setClasse(Pessoa.class);
    }

	public List<Pessoa> listar() {
	    return em.createQuery("SELECT p FROM Pessoa p JOIN FETCH p.cargo", Pessoa.class).getResultList();
	}
	
	public Pessoa buscarPorId(Long id) {
		return em.createQuery("SELECT p FROM Pessoa p JOIN FETCH p.cargo WHERE p.id = :id", Pessoa.class)
	             .setParameter("id", id)
	             .getSingleResult();
    }
}
