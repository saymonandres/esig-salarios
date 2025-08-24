package br.com.saymon.service;

import java.util.List;

import br.com.saymon.dao.PessoaDAO;
import br.com.saymon.exception.NegocioException;
import br.com.saymon.model.Pessoa;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PessoaService {
	
	@Inject
	private PessoaDAO pessoaDAO;

    @PostConstruct
    public void init() {
    	pessoaDAO.setClasse(Pessoa.class);
    }

    @Transactional
    public void salvar(Pessoa pessoa) {
        try {
        	if(pessoa.getId() != null) {
        		pessoaDAO.atualizar(pessoa);
        	}
        	else {
        		pessoaDAO.salvar(pessoa);
        	}
        } catch (PersistenceException e) {
            throw new NegocioException("Não foi possível salvar a pessoa.");
        }
    }

    public List<Pessoa> listar() {
        return pessoaDAO.listar();
    }
    
    public void excluir(Pessoa pessoa) {
    	try {
    		pessoaDAO.excluir(pessoa);
        } catch (PersistenceException e) {
            throw new NegocioException("Não foi possível excluir a pessoa.");
        }
    }
    
    public Pessoa buscarPorId(Long id) {
    	return pessoaDAO.buscarPorId(id);
    }

}
