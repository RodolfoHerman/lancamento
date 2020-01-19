package br.com.rodolfo.lancamento.api.repositories.lancamentoquery;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.repositories.filters.LancamentoFilter;

/**
 * LancamentoRepositoryImpl
 */
public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    // Para realizar consultas no banco
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
        
        // Responsável por construir as 'criterias'
        CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

        Root<Lancamento> root = criteria.from(Lancamento.class);

        // Criar as restrições
        Predicate[] predicates = this.criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = this.manager.createQuery(criteria);

        return query.getResultList();
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder, Root<Lancamento> root) {

        List<Predicate> predicates = new ArrayList<>();

        if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {

            predicates.add(
                // where descricao like %xxxxxxxx%
                builder.like(
                    builder.lower(root.get("descricao")),
                    "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"
                )
            );
        }

        if(!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoDe())) {

            predicates.add(
                builder.greaterThanOrEqualTo(
                    root.get("dataVencimento"),
                    lancamentoFilter.getDataVencimentoDe()
                )
            );
        }

        if(!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoAte())) {

            predicates.add(
                builder.lessThanOrEqualTo(
                    root.get("dataVencimento"),
                    lancamentoFilter.getDataVencimentoAte()
                )
            );
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
    
}