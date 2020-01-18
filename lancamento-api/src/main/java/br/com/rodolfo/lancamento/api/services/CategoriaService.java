package br.com.rodolfo.lancamento.api.services;

import java.util.List;

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
    
}