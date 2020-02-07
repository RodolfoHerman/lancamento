package br.com.rodolfo.lancamento.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.rodolfo.lancamento.api.models.Usuario;

/**
 * UsuarioSistema
 */
public class UsuarioSistema extends User {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;

    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        
        super(usuario.getEmail(), "{bcrypt}".concat(usuario.getSenha()), authorities);
        
        this.usuario = usuario;
    }

    public Usuario getUsuario() {

        return this.usuario;
    }


    
}