package br.com.rodolfo.lancamento.api.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.lancamento.api.event.RecursoCriadoEvent;
import br.com.rodolfo.lancamento.api.models.Usuario;
import br.com.rodolfo.lancamento.api.repositories.UsuarioRepository;

/**
 * UsuarioController
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(this.usuarioRepository.findById(id).get());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletearPorId(@PathVariable("id") Long id) {

        this.usuarioRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody Usuario usuario, HttpServletResponse response) {

        Usuario usuarioSalvo = this.usuarioRepository.save(usuario);

        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }
    
}