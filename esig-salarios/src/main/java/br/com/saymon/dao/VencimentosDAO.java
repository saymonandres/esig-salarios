package br.com.saymon.dao;

import java.util.List;

import br.com.saymon.model.Vencimentos;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VencimentosDAO extends GenericDAO<Vencimentos> {
	
	public VencimentosDAO() {
        setClasse(Vencimentos.class);
    }
	
	public List<Vencimentos> listaVencimentosPorCargo(Long idCargo) {
		return em.createQuery(" SELECT v FROM Vencimentos v "
							+ " JOIN CargoVencimentos cv ON v.id = cv.vencimentos.id "
							+ " JOIN Cargo c ON c.id = cv.cargo.id "
							+ " WHERE c.id = :idCargo", Vencimentos.class)
	             .setParameter("idCargo", idCargo)
	             .getResultList();
	}

}
