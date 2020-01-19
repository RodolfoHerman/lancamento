package br.com.rodolfo.lancamento.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Captura as exceção das entidades (Entity)
 */
@ControllerAdvice // Controller Advice - observa toda a aplicação
public class LancamentoExceptionHandler extends ResponseEntityExceptionHandler {


    @Autowired
    private MessageSource messageSource;

    /**
     * Captura as mensagens (requisição) que não conseguiu realizar leitura (Validador JSON do properties)
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        String mensagemUsuario = this.messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

        List<LancamentoErro> erros = Arrays.asList(new LancamentoErro(mensagemUsuario, mensagemDesenvolvedor));

        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Captura os parâmetros inválidos que estão anotados com Bean Validation
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        List<LancamentoErro> erros = this.criarListaDeErros(ex.getBindingResult());
                
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Criação de um método customizado para tratar a exceção EmptyResultDataAccessException
     */
    @ExceptionHandler({ EmptyResultDataAccessException.class })
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {

        String mensagemUsuario = this.messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();

        List<LancamentoErro> erros = Arrays.asList(new LancamentoErro(mensagemUsuario, mensagemDesenvolvedor));

        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    private List<LancamentoErro> criarListaDeErros(BindingResult bindingResult) {

        List<LancamentoErro> erros = new ArrayList<>();

        bindingResult.getFieldErrors().stream().forEach(fieldErro -> {
            
            String mensagemUsuario = this.messageSource.getMessage(fieldErro, LocaleContextHolder.getLocale());
            String mensagemDesenvolvedor = fieldErro.toString();

            erros.add(new LancamentoErro(mensagemUsuario, mensagemDesenvolvedor));
        });

        return erros;
    }

}