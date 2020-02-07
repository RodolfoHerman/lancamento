package br.com.rodolfo.lancamento.api.config.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import br.com.rodolfo.lancamento.api.security.UsuarioSistema;

/**
 * CustomTokenEnhancer
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        
        // Com o authentication.getPrincipal() conseguimos recuperar o usuário logado na aplicação
        // Realizamos o cast para o UsuarioSistema (ele extends o User) e conseguimos recuperar os atributos
        // do objeto Usuario - ex.: getNome()
        UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();

        // Com os dados do objeto Usuario pode-se construir um mapa para associar os atributos da classe
        Map<String, Object> addInfo = new HashMap<>();
        addInfo.put("nome", usuarioSistema.getUsuario().getNome());

        // Com o mapa criado, podemos adicionar ao Token o novo atributo desejado
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);

        // Retorna o Token melhorado (Enhance) com a nova informação
        return accessToken;
    }

    
}