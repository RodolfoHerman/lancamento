package br.com.rodolfo.lancamento.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Validar o usuário para acessar a API
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        auth.inMemoryAuthentication()
            // Com o Springsecurity 5+ é necessário informar o tipo de ENCODE, para esse caso o
            // ENCODE BASIC é nessário informar o tipo TEXTO com {noop}
            .withUser("admin").password("{noop}admin").roles("ROLE");
    }

    // Configurar a autorização das requisições
    @Override
    public void configure(HttpSecurity http) throws Exception {
        
        http.authorizeRequests()
                // As requisições para categorias não precisa estar autenticado
                .antMatchers("/categorias").permitAll()
                // O resto das requisições necessita estar autenticado
                .anyRequest().authenticated().and()
            .httpBasic().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            // CSRF -> ataca via javascrit ao codigo na parte web
            .csrf().disable();
    }
}