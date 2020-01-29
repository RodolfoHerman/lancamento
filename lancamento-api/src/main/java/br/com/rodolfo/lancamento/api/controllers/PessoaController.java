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

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Lista as pessoas da base de dados
     * @return List
     */
    @GetMapping
    // hasAuthority -> permissão (escopo) do usuário logado.
    // #oauth2.hasScope -> permissão (escopo) do cliente (Aplicação Angular o Mobile)
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public List<Pessoa> listar() {

        return this.pessoaService.listar();
    }

    /**
     * Lista a pessoa através do id
     * @param id
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
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
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    public ResponseEntity<?> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

        Pessoa pessoaSalva = this.pessoaService.criar(pessoa);

        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    /**
     * Deletar uma pessoa através do id
     * @param id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
    public void deletarPorId(@PathVariable Long id) {

        this.pessoaService.deletarPorId(id);
    }

    /**
     * Atualizar uma pessoa através do id
     * @param id
     * @param pessoa
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {

        Pessoa pessoaAtualizada = this.pessoaService.atualizar(id, pessoa);

        return ResponseEntity.ok().body(pessoaAtualizada);
    }

    /**
     * Atualizar a propriedade ativo da pessoa (atualização parcial)
     * @param id
     * @param ativo
     */
    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {

        this.pessoaService.atualizarPropriedadeAtivo(id, ativo);
    }

}