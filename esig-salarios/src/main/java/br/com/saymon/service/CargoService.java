package br.com.saymon.service;

import java.util.List;

import br.com.saymon.dao.CargoDAO;
import br.com.saymon.model.Cargo;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CargoService {
	
	@Inject
	private CargoDAO cargoDAO;

    @PostConstruct
    public void init() {
    	cargoDAO.setClasse(Cargo.class);
    }

    public List<Cargo> listar() {
        return cargoDAO.listar();
    }
    
    public Cargo buscarPorId(Long id) {
    	return cargoDAO.buscarPorId(id);
    }

}
