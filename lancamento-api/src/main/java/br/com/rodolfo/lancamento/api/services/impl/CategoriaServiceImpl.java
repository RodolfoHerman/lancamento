package br.com.rodolfo.lancamento.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfo.lancamento.api.models.Categoria;
import br.com.rodolfo.lancamento.api.repositories.CategoriaRepository;
import br.com.rodolfo.lancamento.api.services.CategoriaService;

/**
 * CategoriaServiceImpl
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listar() {
        
        return this.categoriaRepository.findAll();
    }


    
}