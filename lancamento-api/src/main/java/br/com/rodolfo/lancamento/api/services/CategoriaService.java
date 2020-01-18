package br.com.rodolfo.lancamento.api.services;

import java.util.List;
import java.util.Optional;

import br.com.rodolfo.lancamento.api.models.Categoria;

/**
 * CategoriaService
 */
public interface CategoriaService {

    /**
     * Busca e retorna uma lista de categorias
     * @return List
     */
    List<Categoria> listar();

    /**
     * Busca e retorna uma categoria através do id
     * @param id
     * @return Optional
     */
    Optional<Categoria> listarPorId(Long id);

    /**
     * Cria e retorna a categoria no banco de dados
     * @param categoria
     * @return Categoria
     */
    Categoria criar(Categoria categoria);
    
    /**
     * Deleta uma categoria através do id
     * @param id
     */
    void deletarPorId(Long id);

}