package br.com.rodolfo.lancamento.api.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.lancamento.api.event.RecursoCriadoEvent;
import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.services.LancamentoService;

/**
 * LancamentoController
 */
@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Lista os lançamentos da base de dados
     * @return List
     */
    @GetMapping
    public List<Lancamento> listar() {

        return this.lancamentoService.listar();
    }

    /**
     * Lista o lancamento através do id
     * @param id
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id) {

        Optional<Lancamento> lancamento = this.lancamentoService.listarPorId(id);

        return lancamento.isPresent() ? ResponseEntity.ok().body(lancamento.get()) : ResponseEntity.badRequest().build();
    }

    /**
     * cria um novo lançamento na base de dados
     * @param lancamento
     * @param response
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {

        Lancamento lancamentoSalvo = this.lancamentoService.criar(lancamento);

        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }
}