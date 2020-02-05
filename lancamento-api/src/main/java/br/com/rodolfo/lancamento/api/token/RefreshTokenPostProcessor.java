package br.com.rodolfo.lancamento.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import br.com.rodolfo.lancamento.api.config.property.LancamentoProperty;

/**
 * RefreshTokenPostProcessor OAuth2AccessToken -> é o tipo de dado que será
 * interceptado quando a requisição estiver voltando
 */
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Autowired
	private LancamentoProperty property;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        
        // Filtro para interceptar apenas o retorno da solicitação de tokens
        return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		
		String refreshToken = body.getRefreshToken().getValue();
		adicionarRefreshTokenNoCookie(refreshToken, req, resp);
		removerRefreshTokenDoBody(token);
		
		return body;
	}

	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
        
        token.setRefreshToken(null);
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
        
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        
        // O Cookie só será acessível através de HTTP
        refreshTokenCookie.setHttpOnly(true);
        // Será um Cookie acessível apenas em HTTPS
        refreshTokenCookie.setSecure(this.property.getSeguranca().isEnableHttps());
        // Para qual caminho esse Cookie deve ser enviado pelo browser automaticamente
        refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
        // Expiração do Cookie em 30 dias
        refreshTokenCookie.setMaxAge(2592000);
        
        // Adicionar o cookie na resposta
        resp.addCookie(refreshTokenCookie);
	}

}