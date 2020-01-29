package br.com.rodolfo.lancamento.api.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.lancamento.api.event.RecursoCriadoEvent;
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

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Lista as categorias da base de dados
     * @return List
     */
    @GetMapping
    // hasAuthority -> permissão (escopo) do usuário logado.
    // #oauth2.hasScope -> permissão (escopo) do cliente (Aplicação Angular o Mobile)
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Categoria> listar() {

        return this.categoriaService.listar();
    }

    /**
     * Lista a categoria através do id
     * @param id
     * @return Categoria
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
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
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('wirte')")
    public ResponseEntity<?> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {

        Categoria categoriaSalva = this.categoriaService.criar(categoria);

        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    /**
     * Deletar uma categoria através do id
     * @param id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPorId(@PathVariable Long id) {
        
        this.categoriaService.deletarPorId(id);
    }

    /**
     * Atualizar uma categoria através do id
     * @param id
     * @param categoria
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {

        Categoria categoriaAtualizada = this.categoriaService.atualizar(id, categoria);

        return ResponseEntity.ok().body(categoriaAtualizada);
    }

}