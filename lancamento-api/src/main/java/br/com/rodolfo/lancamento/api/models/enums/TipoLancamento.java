package br.com.rodolfo.lancamento.api.models.enums;

/**
 * TipoLancamento
 */
public enum TipoLancamento {

    RECEITA("Receita"),
    DESPESA("Despesa");

    private final String descricao;

    TipoLancamento(String descricao) {

        this.descricao = descricao;
    }

    public String getDescricao() {

        return this.descricao;
    }
}