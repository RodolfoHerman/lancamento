package br.com.rodolfo.lancamento.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    // Validar o usuário para acessar a API
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{noop}admin") // necessário encodar para tiar o {noop}
            .roles("ROLE");

    }

    // Configurar a autorização das requisições
    @Override
    public void configure(HttpSecurity http) throws Exception {
        
        http.authorizeRequests()
                // As requisições para categorias não precisa estar autenticado
                .antMatchers("/categorias").permitAll()
                // O resto das requisições necessita estar autenticado
                .anyRequest().authenticated().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            // CSRF -> ataca via javascrit ao codigo na parte web
            .csrf().disable();
    }


}