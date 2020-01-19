package br.com.rodolfo.lancamento.api.services;

import java.util.List;
import java.util.Optional;

import br.com.rodolfo.lancamento.api.models.Pessoa;

/**
 * PessoaService
 */
public interface PessoaService {

    /**
     * Busca e retorna uma lista de pessoas
     * @return List
     */
    List<Pessoa> listar();

    /**
     * Busca e retorna uma pessoa através do id
     * @param id
     * @return Optional
     */
    Optional<Pessoa> listarPorId(Long id);

    /**
     * Cria e retorna a pessoa no banco de dados
     * @param pessoa
     * @return Pessoa
     */
    Pessoa criar(Pessoa pessoa);
    
    /**
     * Deleta uma pessoa através do id
     * @param id
     */
    void deletarPorId(Long id);

    /**
     * Atualiza e retorna a pessoa no banco de dados
     * @param id
     * @param pessoa
     * @return Pessoa
     */
    Pessoa atualizar(Long id, Pessoa pessoa);

    /**
     * Atualliza a propriedade ativo de pessoa (atualização parcial)
     * @param id
     * @param ativo
     */
    void atualizarPropriedadeAtivo(Long id, Boolean ativo);

}