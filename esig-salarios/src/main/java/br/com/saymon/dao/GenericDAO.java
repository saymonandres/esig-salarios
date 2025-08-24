package br.com.saymon.dao;

import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Dependent
public class GenericDAO<T> {

    @PersistenceContext(unitName = "esigPU")
    protected EntityManager em;

    private Class<T> classe;

    public void setClasse(Class<T> classe) {
        this.classe = classe;
    }

    @Transactional
    public void salvar(T entidade) {
        em.persist(entidade);
    }

    @Transactional
    public void atualizar(T entidade) {
        em.merge(entidade);
    }

    @Transactional
    public void excluir(T entidade) {
        T merged = em.contains(entidade) ? entidade : em.merge(entidade);
        em.remove(merged);
    }

    public T buscarPorId(Long id) {
        return em.find(classe, id);
    }

    public List<T> listar() {
        return em.createQuery("SELECT e FROM " + classe.getSimpleName() + " e order by e.id asc", classe).getResultList();
    }
    
    @Transactional
    public void limpar() {
        em.createQuery("DELETE FROM " + classe.getSimpleName() + " e").executeUpdate();
    }
}
