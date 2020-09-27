package br.com.rodolfo.lancamento.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity
@EnableResourceServer
// Habilitar as seguranças nos métodos dos controllers dos recursos
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    // Validar o usuário para acessar a API
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Configurar a autorização das requisições
    @Override
    public void configure(HttpSecurity http) throws Exception {
        
        http.authorizeRequests()
                // As requisições para categorias não precisa estar autenticado
                .antMatchers("/categorias").permitAll()
                .antMatchers("/usuarios/**").permitAll()
                // O resto das requisições necessita estar autenticado
                .anyRequest().authenticated().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            // CSRF -> ataca via javascrit ao codigo na parte web
            .csrf().disable();
    }

    @Bean
    public MethodSecurityExpressionHandler createExpressionHandler() {

        // Hnadler para conseguir realizar a segurança do método com o OAuth2
        return new OAuth2MethodSecurityExpressionHandler();
    }
}