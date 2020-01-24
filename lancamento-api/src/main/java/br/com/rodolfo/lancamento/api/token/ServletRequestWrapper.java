package br.com.rodolfo.lancamento.api.token;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;

/**
 * Necessário para realizar a inclusão do header 'refreshToken' na requisição
 * (HttpServletRequest). Isso ocorre porque o objeto HttpServletRequest vem
 * trancado (locked)
 */
public class ServletRequestWrapper extends HttpServletRequestWrapper {

    private String refreshToken;

    public ServletRequestWrapper(HttpServletRequest request, String refreshToken) {
        
        super(request);
        
        this.refreshToken = refreshToken;
    }

    @Override
    public Map<String, String[]> getParameterMap() {

        ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
        map.put("refresh_token", new String[]{ this.refreshToken });
        map.setLocked(true);

        return map;
    }
    
}