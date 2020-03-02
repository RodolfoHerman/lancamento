package br.com.rodolfo.lancamento.api.dto;

import java.math.BigDecimal;

import br.com.rodolfo.lancamento.api.models.Pessoa;
import br.com.rodolfo.lancamento.api.models.enums.TipoLancamento;

/**
 * LancamentosEstatisticaPessoaDTO
 */
public class LancamentosEstatisticaPessoaDTO {

    private TipoLancamento tipo;

    private Pessoa pessoa;

    private BigDecimal total;

    public LancamentosEstatisticaPessoaDTO(TipoLancamento tipo, Pessoa pessoa, BigDecimal total) {
        this.tipo = tipo;
        this.pessoa = pessoa;
        this.total = total;
    }

    public TipoLancamento getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}