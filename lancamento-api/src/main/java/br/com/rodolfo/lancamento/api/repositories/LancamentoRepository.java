package br.com.rodolfo.lancamento.api.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.repositories.queries.lancamento.LancamentoRepositoryQuery;


/**
 * LancamentoRepository
 * 
 * https://www.baeldung.com/spring-data-jpa-query
 */
public interface LancamentoRepository extends JpaRepository<Lancamento,Long>, LancamentoRepositoryQuery{

    public List<Lancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);
    
}