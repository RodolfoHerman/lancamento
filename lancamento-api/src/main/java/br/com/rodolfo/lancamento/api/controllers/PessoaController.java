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

import br.com.rodolfo.lancamento.api.models.Pessoa;
import br.com.rodolfo.lancamento.api.services.PessoaService;

/**
 * PessoaController
 */
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    /**
     * Lista as pessoas da base de dados
     * @return List
     */
    @GetMapping
    public List<Pessoa> listar() {

        return this.pessoaService.listar();
    }

    /**
     * Lista a pessoa através do id
     * @param id
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id) {

        Optional<Pessoa> pessoa = this.pessoaService.listarPorId(id);

        return pessoa.isPresent() ? ResponseEntity.ok().body(pessoa.get()) : ResponseEntity.badRequest().build();
    }


    /**
     * Criar uma nova pessoa na base de dados
     * @param pessoa
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Pessoa pessoa) {

        Pessoa pessoaSalva = this.pessoaService.criar(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
            .buildAndExpand(pessoaSalva.getId()).toUri();

        return ResponseEntity.created(uri).body(pessoaSalva);
    }

}