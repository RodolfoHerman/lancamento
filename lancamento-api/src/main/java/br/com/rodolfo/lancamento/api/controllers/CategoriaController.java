package br.com.rodolfo.lancamento.api.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    /**
     * Lista a categoria através do id
     * @param id
     * @return Categoria
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id) {
        
        Optional<Categoria> categoria = this.categoriaService.listarPorId(id);

        return categoria.isPresent() ? ResponseEntity.ok().body(categoria.get()) : ResponseEntity.badRequest().build();
    }
    
    /**
     * Cria uma nova categoria na base de dados
     * @param categoria
     * @return Categoria
     */
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Categoria categoria) {

        Categoria categoriaSalva = this.categoriaService.criar(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
            .buildAndExpand(categoriaSalva.getId()).toUri();

        return ResponseEntity.created(uri).body(categoriaSalva);
    }


}