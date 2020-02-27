package br.com.rodolfo.lancamento.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.lancamento.api.models.Pessoa;

/**
 * PessoaRepository
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);
}