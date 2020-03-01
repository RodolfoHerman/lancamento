package br.com.rodolfo.lancamento.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.rodolfo.lancamento.api.models.enums.TipoLancamento;

/**
 * LancamentosEstatisticaDiaDTO
 */
public class LancamentosEstatisticaDiaDTO {

    private TipoLancamento tipoLancamento;

    private LocalDate dia;

    private BigDecimal total;

    public LancamentosEstatisticaDiaDTO(TipoLancamento tipoLancamento, LocalDate dia, BigDecimal total) {
        this.tipoLancamento = tipoLancamento;
        this.dia = dia;
        this.total = total;
    }

    public TipoLancamento getTipoLancamento() {
        return this.tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public LocalDate getDia() {
        return this.dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}