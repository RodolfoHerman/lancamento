package br.com.rodolfo.lancamento.api.exceptionhandler;

/**
 * LancamentoErro
 */
public class LancamentoErro {

    private String mensagemUsuario;
    private String mensagemDesenvolvedor;


    public LancamentoErro(String mensagemUsuario, String mensagemDesenvolvedor) {
        this.mensagemUsuario = mensagemUsuario;
        this.mensagemDesenvolvedor = mensagemDesenvolvedor;
    }


    public String getMensagemUsuario() {
        return this.mensagemUsuario;
    }

    public String getMensagemDesenvolvedor() {
        return this.mensagemDesenvolvedor;
    }

}