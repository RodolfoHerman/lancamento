package br.com.rodolfo.lancamento.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.lancamento.api.models.Pessoa;

/**
 * PessoaRepository
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    
}