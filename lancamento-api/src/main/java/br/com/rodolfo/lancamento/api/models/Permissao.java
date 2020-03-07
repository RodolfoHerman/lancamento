package br.com.rodolfo.lancamento.api.models;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "permissao")
public class Permissao {

    @Id
    private Long id;

    private String descricao;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissoes")
    private List<Usuario> usuarios;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Permissao)) {
            return false;
        }
        Permissao permissao = (Permissao) o;
        return Objects.equals(id, permissao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
