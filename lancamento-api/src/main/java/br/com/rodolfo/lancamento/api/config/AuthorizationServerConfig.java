package br.com.rodolfo.lancamento.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import br.com.rodolfo.lancamento.api.config.token.CustomTokenEnhancer;

/**
 * AuthorizationServerConfig
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // Responsável por gerenciar a autenticação (tratar o usuário e a senha da
    // aplicação)
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        
        // Token com mais detalhes a respeito do usuário - ex.: Inserir o nome do usuário no token
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        // Incrementar funcionalidades ao token
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenHenhancer(), this.accessTokenConverter()));
        
        endpoints
            .tokenStore(tokenStore())
            // Como o token foi melhorado (Enhance) é necessário substituir a atribuição
            // .accessTokenConverter(accessTokenConverter())
            .tokenEnhancer(tokenEnhancerChain)

            // Sempre qd pedir um novo access token utilizando o refresh token, um novo refresh token será enviado.
            // Então, enquanto o usuário estiver usando a aplicação todos os dias, o refresh token não irá expirar.
            // Se não setar para FALSE o refresh token terá o tempo de apenas 24h, necessitando q o usuário logue de novo.
            .reuseRefreshTokens(false)
            .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("angular")
                .secret("{noop}@angul@r0") // necessario encodar para tiar o {noop}
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(1800)
                .refreshTokenValiditySeconds(3600 * 24)
            .and()
                .withClient("mobile")
                .secret("{noop}m0b1l30") // necessario encodar para tiar o {noop}
                .scopes("read")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(1800)
                .refreshTokenValiditySeconds(3600 * 24);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("rodolfo");

        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {

        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public TokenEnhancer tokenHenhancer() {
        return new CustomTokenEnhancer();
    }

}