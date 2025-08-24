package br.com.saymon.dao;

import br.com.saymon.model.PessoaSalarioConsolidado;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaSalarioConsolidadoDAO extends GenericDAO<PessoaSalarioConsolidado> {
	
	public PessoaSalarioConsolidadoDAO() {
        setClasse(PessoaSalarioConsolidado.class);
    }

}
