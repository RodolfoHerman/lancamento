package br.com.rodolfo.lancamento.api.services;

import java.util.List;
import java.util.Optional;

import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.repositories.filters.LancamentoFilter;

/**
 * LancamentoService
 */
public interface LancamentoService {

    /**
     * Busca e retorna uma lista de lançamentos
     * @param lancamentoFilter
     * @return List
     */
    List<Lancamento> listar(LancamentoFilter lancamentoFilter);
    
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
}