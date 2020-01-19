package br.com.rodolfo.lancamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.repositories.lancamentoquery.LancamentoRepositoryQuery;

/**
 * LancamentoRepository
 * 
 * https://www.baeldung.com/spring-data-jpa-query
 */
public interface LancamentoRepository extends JpaRepository<Lancamento,Long>, LancamentoRepositoryQuery{

    
}