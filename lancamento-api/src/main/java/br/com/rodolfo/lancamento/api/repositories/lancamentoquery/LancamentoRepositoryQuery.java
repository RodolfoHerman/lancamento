package br.com.rodolfo.lancamento.api.repositories.lancamentoquery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.repositories.filters.LancamentoFilter;
import br.com.rodolfo.lancamento.api.repositories.projections.LancamentoResumo;

/**
 * LancamentoRepositoryQuery
 */
public interface LancamentoRepositoryQuery {

    /**
     * Busca e retorna uma lista de Lancamento a partir do filtro
     * @param lancamentoFilter
     * @param pageable
     * @return Page
     */
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

    /**
     * Busca e retorna uma lista de LancamentoResumo a partir do filtro
     * @param lancamentoFilter
     * @param pageable
     * @return Page
     */
    public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}