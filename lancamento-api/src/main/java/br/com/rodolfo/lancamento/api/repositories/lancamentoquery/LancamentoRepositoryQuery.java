package br.com.rodolfo.lancamento.api.repositories.lancamentoquery;

import java.util.List;

import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.repositories.filters.LancamentoFilter;

/**
 * LancamentoRepositoryQuery
 */
public interface LancamentoRepositoryQuery {

    /**
     * Busca e retorna uma lista de Lancamento a partir do filtro
     * @param lancamentoFilter
     * @return List
     */
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);

}