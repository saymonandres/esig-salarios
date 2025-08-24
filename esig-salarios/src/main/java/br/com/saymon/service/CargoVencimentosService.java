package br.com.saymon.service;

import br.com.saymon.dao.CargoVencimentosDAO;
import br.com.saymon.model.CargoVencimentos;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CargoVencimentosService {
	
	@Inject
	private CargoVencimentosDAO cargoVencimentosDAO;

    @PostConstruct
    public void init() {
    	cargoVencimentosDAO.setClasse(CargoVencimentos.class);
    }

}
