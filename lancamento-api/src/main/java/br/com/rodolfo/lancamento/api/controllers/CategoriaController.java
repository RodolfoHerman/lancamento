package br.com.rodolfo.lancamento.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.lancamento.api.models.Categoria;
import br.com.rodolfo.lancamento.api.services.CategoriaService;

/**
 * CategoriaController
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Lista as categorias da base de dados
     * @return List
     */
    @GetMapping
    public List<Categoria> listar() {

        return this.categoriaService.listar();
    }


}