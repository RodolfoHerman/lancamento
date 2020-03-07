package br.com.rodolfo.lancamento.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfo.lancamento.api.models.Usuario;
import br.com.rodolfo.lancamento.api.repositories.UsuarioRepository;
import br.com.rodolfo.lancamento.api.services.UsuarioService;

/**
 * UsuarioServiceImpl
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {

        return this.usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> buscarPelaDescricaoDaPermissaoDoUsuario(String permissaoDescricao) {
        
        return this.usuarioRepository.findByPermissoesDescricao(permissaoDescricao);
    }

    
}