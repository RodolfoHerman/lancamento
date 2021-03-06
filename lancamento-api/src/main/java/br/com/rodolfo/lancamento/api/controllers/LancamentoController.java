package br.com.rodolfo.lancamento.api.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.lancamento.api.dto.LancamentosEstatisticaCategoriaDTO;
import br.com.rodolfo.lancamento.api.dto.LancamentosEstatisticaDiaDTO;
import br.com.rodolfo.lancamento.api.dto.LancamentosEstatisticaPessoaDTO;
import br.com.rodolfo.lancamento.api.event.RecursoCriadoEvent;
import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.repositories.filters.LancamentoFilter;
import br.com.rodolfo.lancamento.api.repositories.projections.LancamentoResumo;
import br.com.rodolfo.lancamento.api.services.LancamentoService;
import net.sf.jasperreports.engine.JRException;

/**
 * LancamentoController
 */
@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Lista os lançamentos da base de dados
     * @param lancamentoFilter
     * @return Page
     */
    @GetMapping
    // hasAuthority -> permissão (escopo) do usuário logado.
    // #oauth2.hasScope -> permissão (escopo) do cliente (Aplicação Angular o Mobile)
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<Lancamento> listar(LancamentoFilter lancamentoFilter, Pageable pageable) {

        return this.lancamentoService.listar(lancamentoFilter, pageable);
    }

    /**
     * Lista os lançamentos da base de dados
     * @param lancamentoFilter
     * @return Page
     */
    @GetMapping(params = "resumo")
    // hasAuthority -> permissão (escopo) do usuário logado.
    // #oauth2.hasScope -> permissão (escopo) do cliente (Aplicação Angular o Mobile)
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {

        return this.lancamentoService.resumir(lancamentoFilter, pageable);
    }

    /**
     * Lista o lancamento através do id
     * @param id
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<?> listarPorId(@PathVariable Long id) {

        Optional<Lancamento> lancamento = this.lancamentoService.listarPorId(id);

        return lancamento.isPresent() ? ResponseEntity.ok().body(lancamento.get()) : ResponseEntity.badRequest().build();
    }

    /**
     * cria um novo lançamento na base de dados
     * @param lancamento
     * @param response
     * @return ResponseEntity
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<?> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {

        Lancamento lancamentoSalvo = this.lancamentoService.criar(lancamento);

        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    /**
     * Deletar um lançamento através do id
     * @param id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
    public void deletarPorId(@PathVariable Long id) {

        this.lancamentoService.deletarPorId(id);
    }

    /**
     * Atualizar um lançamento dado seu ID
     * @param id
     * @param lancamento
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO')")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @Valid @RequestBody Lancamento lancamento) {

        Lancamento lancamentoSalvo = this.lancamentoService.atualizar(id, lancamento);

        return ResponseEntity.ok().body(lancamentoSalvo);
    }

    /**
     * Lista as estatisticas (valor gasto) dos lançamentos agrupados por categoria
     * @param data
     * @return List
     */
    @GetMapping("/estatisticas/por-categoria")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public List<LancamentosEstatisticaCategoriaDTO> porCategoria(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data) {

        return this.lancamentoService.porCategoria(data);
    }

    /**
     * Lista as estatisticas (valor gasto) dos lançamentos agrupados por tipo e dia
     * @param data
     * @return List
     */
    @GetMapping("/estatisticas/por-dia")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public List<LancamentosEstatisticaDiaDTO> porDia(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate data) {

        return this.lancamentoService.porDia(data);
    }

    /**
     * Lista as estatisticas de gastos por tipo de cada pessoa
     * @param dataInicio
     * @param dataFim
     * @return List
     */
    @GetMapping("/estatisticas/por-pessoa")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public List<LancamentosEstatisticaPessoaDTO> porPessoa(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {

        return this.lancamentoService.porPessoa(dataInicio, dataFim);
    }

    /**
     * Método que retorna o relátorio (PDF) por pessoa a partir do filtro de data
     * @param dataInicio
     * @param dataFim
     * @return ResponseEntity
     * @throws JRException
     */
    @GetMapping("/relatorios/por-pessoa")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<byte[]> relatorioPorPessoa(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) throws JRException {

        byte[] relatorio = this.lancamentoService.relatorioPorPessoa(dataInicio, dataFim);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
    }

}