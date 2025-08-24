package br.com.saymon.service;

import java.math.BigDecimal;
import java.util.List;

import br.com.saymon.dao.PessoaDAO;
import br.com.saymon.dao.PessoaSalarioConsolidadoDAO;
import br.com.saymon.dao.VencimentosDAO;
import br.com.saymon.exception.NegocioException;
import br.com.saymon.model.Pessoa;
import br.com.saymon.model.PessoaSalarioConsolidado;
import br.com.saymon.model.TipoVencimento;
import br.com.saymon.model.Vencimentos;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PessoaSalarioConsolidadoService {
	
	@Inject
	private PessoaSalarioConsolidadoDAO pessoaSalarioConsolidadoDAO;
	@Inject
	private VencimentosDAO vencimentosDAO;
	@Inject
	private PessoaDAO pessoaDAO;

    @PostConstruct
    public void init() {
    	pessoaSalarioConsolidadoDAO.setClasse(PessoaSalarioConsolidado.class);
    }

    @Transactional
    public void salvar(PessoaSalarioConsolidado pessoa) {
        try {
        	if(pessoa.getId() != null) {
        		pessoaSalarioConsolidadoDAO.atualizar(pessoa);
        	}
        	else {
        		pessoaSalarioConsolidadoDAO.salvar(pessoa);
        	}
        } catch (PersistenceException e) {
            throw new NegocioException("Não foi possível salvar a pessoa.");
        }
    }

    public List<PessoaSalarioConsolidado> listar() {
        return pessoaSalarioConsolidadoDAO.listar();
    }
    
    public void excluir(PessoaSalarioConsolidado pessoa) {
    	try {
    		pessoaSalarioConsolidadoDAO.excluir(pessoa);
        } catch (PersistenceException e) {
            throw new NegocioException("Não foi possível excluir a pessoa.");
        }
    }
    
    public PessoaSalarioConsolidado buscarPorId(Long id) {
    	return pessoaSalarioConsolidadoDAO.buscarPorId(id);
    }
    
    // metodo que faz o calculo por pessoa
    public BigDecimal calcularSalarioPessoa(Pessoa pessoa) {
        List<Vencimentos> vencimentos = vencimentosDAO.listaVencimentosPorCargo(pessoa.getCargo().getId());
        
        BigDecimal total = BigDecimal.ZERO;
        for (Vencimentos v : vencimentos) {
            if (TipoVencimento.CREDITO == v.getTipo()) {
                total = total.add(v.getValor());
            } else if (TipoVencimento.DEBITO == v.getTipo()) {
                total = total.subtract(v.getValor());
            }
        }
        return total;
    }
    
    // metodo que faz o calculo geral
    // poderia otimizar esse processo usando uma unica native query para calcular tudo
    public void calcularSalarios() {
        // Limpa tabela consolidada
    	pessoaSalarioConsolidadoDAO.limpar();

        List<Pessoa> pessoas = pessoaDAO.listar();

        for (Pessoa pessoa : pessoas) {
        	BigDecimal salario = calcularSalarioPessoa(pessoa);

            PessoaSalarioConsolidado consolidado = new PessoaSalarioConsolidado();
            consolidado.setIdPessoa(pessoa.getId());
            consolidado.setNomePessoa(pessoa.getNome());
            consolidado.setNomeCargo(pessoa.getCargo().getNome());
            consolidado.setSalario(salario);
            
            try {
            	pessoaSalarioConsolidadoDAO.salvar(consolidado);
            } catch (PersistenceException e) {
                throw new NegocioException("Não foi possível salvar a pessoa.");
            }
        }
    }

}
