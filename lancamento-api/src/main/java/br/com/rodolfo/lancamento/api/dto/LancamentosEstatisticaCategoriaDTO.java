package br.com.rodolfo.lancamento.api.dto;

import java.math.BigDecimal;

import br.com.rodolfo.lancamento.api.models.Categoria;

/**
 * LancamentoEstatisticaCategoriaDTO
 */
public class LancamentosEstatisticaCategoriaDTO {

    private Categoria categoria;

    private BigDecimal total;

    public LancamentosEstatisticaCategoriaDTO(Categoria categoria, BigDecimal total) {
        this.categoria = categoria;
        this.total = total;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    
}