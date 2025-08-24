package br.com.saymon.service;

import br.com.saymon.dao.VencimentosDAO;
import br.com.saymon.model.Vencimentos;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VencimentosService {
	
	@Inject
	private VencimentosDAO vencimentosDAO;

    @PostConstruct
    public void init() {
    	vencimentosDAO.setClasse(Vencimentos.class);
    }

}
