package br.com.rodolfo.lancamento.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.rodolfo.lancamento.api.dto.LancamentosEstatisticaCategoriaDTO;
import br.com.rodolfo.lancamento.api.dto.LancamentosEstatisticaDiaDTO;
import br.com.rodolfo.lancamento.api.dto.LancamentosEstatisticaPessoaDTO;
import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.repositories.filters.LancamentoFilter;
import br.com.rodolfo.lancamento.api.repositories.projections.LancamentoResumo;
import net.sf.jasperreports.engine.JRException;

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

    /**
     * Busca e retorna uma lista de LancamentosEstatisticaCategoriaDTO por mês de referência
     * @param data
     * @return List
     */
    public List<LancamentosEstatisticaCategoriaDTO> porCategoria(LocalDate data);

    /**
     * Busca e retorna uma lista de LancamentosEstatisticaDiaDTO por dia de referência
     * @param data
     * @return List
     */
    public List<LancamentosEstatisticaDiaDTO> porDia(LocalDate data);

    /**
     * Busca e retorna uma lista de LancamentosEstatisticaPessoaDTO pelo intervalo informado
     * @param dataInicio
     * @param dataFim
     * @return List
     */
    public List<LancamentosEstatisticaPessoaDTO> porPessoa(LocalDate dataInicio, LocalDate dataFim);

    /**
     * Retorna os bytes do relatório em PDF
     * @param dataInicio
     * @param dataFim
     * @return byte[]
     * @throws JRException
     */
    public byte[] relatorioPorPessoa(LocalDate dataInicio, LocalDate dataFim) throws JRException;
}