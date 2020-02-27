package br.com.rodolfo.lancamento.api.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rodolfo.lancamento.api.models.Usuario;
import br.com.rodolfo.lancamento.api.services.UsuarioService;

/**
 * AppUserDetailsService
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> usuarioOptional = this.usuarioService.buscarPorEmail(email);

        Usuario usuario = usuarioOptional
                .orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha iválido(s)!"));

        return new UsuarioSistema(usuario, getPermissoes(usuario));
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
        
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        
        usuario.getPermissoes().forEach(permissao -> authorities.add(new SimpleGrantedAuthority(permissao.getDescricao().toUpperCase())));

        return authorities;
    }

    
}