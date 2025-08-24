package br.com.saymon.dao;

import br.com.saymon.model.CargoVencimentos;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CargoVencimentosDAO extends GenericDAO<CargoVencimentos> {
	
	public CargoVencimentosDAO() {
        setClasse(CargoVencimentos.class);
    }

}
