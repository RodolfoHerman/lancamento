package br.com.rodolfo.lancamento.api.token;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * RefreshTokenPreProcessorFilter
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenPreProcessorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;

        // Filtrar apenas as requisições para a URI '/oauth/token' contendo o header com grant_type = refresh_token. Ou seja,
        // somente será utilizado o refresh token que está no cookie quando o grant_type for o igual a refresh_token 
        if(req.getRequestURI().equalsIgnoreCase("/oauth/token") 
                && req.getParameter("grant_type").equals("refresh_token")
                && req.getCookies() != null) {

            for(Cookie cookie: req.getCookies()) {

                // Nome do cookie que foi setado
                if(cookie.getName().equals("refreshToken")) {

                    String refreshToken = cookie.getValue();
                    req = new ServletRequestWrapper(req, refreshToken);
                }
            }
        }

        chain.doFilter(req, response);
    }    
}