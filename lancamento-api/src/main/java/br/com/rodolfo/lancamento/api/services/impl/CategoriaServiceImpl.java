package br.com.rodolfo.lancamento.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public Optional<Categoria> listarPorId(Long id) {

        return this.categoriaRepository.findById(id);
    }

    @Override
    public Categoria criar(Categoria categoria) {

        return this.categoriaRepository.save(categoria);
    }

    @Override
    public void deletarPorId(Long id) {

        this.categoriaRepository.deleteById(id);
    }

    @Override
    public Categoria atualizar(Long id, Categoria categoria) {

        Optional<Categoria> categoriaSalva = this.categoriaRepository.findById(id);

        if(categoriaSalva.isEmpty()) {

            throw new EmptyResultDataAccessException(1);
        }

        BeanUtils.copyProperties(categoria, categoriaSalva.get(), "id");
        return this.categoriaRepository.save(categoriaSalva.get());
    }
}