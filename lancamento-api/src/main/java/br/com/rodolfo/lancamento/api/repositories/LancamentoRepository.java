package br.com.rodolfo.lancamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.lancamento.api.models.Lancamento;

/**
 * LancamentoRepository
 */
public interface LancamentoRepository extends JpaRepository<Lancamento,Long>{

    
}