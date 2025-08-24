package br.com.saymon.dao;

import br.com.saymon.model.Cargo;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CargoDAO extends GenericDAO<Cargo> {
	
	public CargoDAO() {
        setClasse(Cargo.class);
    }

}
