package br.com.rodolfo.lancamento.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.repositories.filters.LancamentoFilter;
import br.com.rodolfo.lancamento.api.repositories.projections.LancamentoResumo;

/**
 * LancamentoService
 */
public interface LancamentoService {

    /**
     * Busca e retorna uma lista de lançamentos
     * @param lancamentoFilter
     * @param pageable
     * @return Page
     */
    Page<Lancamento> listar(LancamentoFilter lancamentoFilter, Pageable pageable);
    
    /**
     * Busca e retorna um lançamento través do id
     * @param id
     * @return Optional
     */
    Optional<Lancamento> listarPorId(Long id);
 
    /**
     * Cria e retorna um lançamento no banco de dados
     * @param lancamento
     * @return Lancamento
     */
    Lancamento criar(Lancamento lancamento);

    /**
     * Deleta um lançamento através do id
     * @param id
     */
    void deletarPorId(Long id);

    /**
     * Busca e retorna uma lista de lançamentos resumisdos
     * @param lancamentoFilter
     * @param pageable
     * @return Page
     */
    Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

    /**
     * Atualiza um lançamento a partir de seu ID
     * @param id
     * @param lancamento
     * @return Lancamento
     */
    Lancamento atualizar(Long id, Lancamento lancamento);
}