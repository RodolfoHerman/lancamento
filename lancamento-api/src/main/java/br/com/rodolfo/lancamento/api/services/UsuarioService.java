package br.com.rodolfo.lancamento.api.services;

import java.util.Optional;

import br.com.rodolfo.lancamento.api.models.Usuario;

/**
 * UsuarioService
 */
public interface UsuarioService {

    /**
     * Busca e retorna um usuario através do email
     * @param email
     * @return Optional
     */
    Optional<Usuario> buscarPorEmail(String email);
}