package br.com.rodolfo.lancamento.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

/**
 * RecursoCriadoEvent
 */
public class RecursoCriadoEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1L;

    private HttpServletResponse response;
    private Long id;

    public RecursoCriadoEvent(Object source, HttpServletResponse response, Long id) {
        super(source);
        
        this.response = response;
        this.id = id;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public Long getId() {
        return this.id;
    }

}