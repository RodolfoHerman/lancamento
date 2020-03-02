package br.com.rodolfo.lancamento.api.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.rodolfo.lancamento.api.dto.LancamentosEstatisticaCategoriaDTO;
import br.com.rodolfo.lancamento.api.dto.LancamentosEstatisticaDiaDTO;
import br.com.rodolfo.lancamento.api.dto.LancamentosEstatisticaPessoaDTO;
import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.models.Pessoa;
import br.com.rodolfo.lancamento.api.repositories.LancamentoRepository;
import br.com.rodolfo.lancamento.api.repositories.PessoaRepository;
import br.com.rodolfo.lancamento.api.repositories.filters.LancamentoFilter;
import br.com.rodolfo.lancamento.api.repositories.projections.LancamentoResumo;
import br.com.rodolfo.lancamento.api.services.LancamentoService;
import br.com.rodolfo.lancamento.api.services.exception.LancamentoInexistenteException;
import br.com.rodolfo.lancamento.api.services.exception.PessoaInativaOuInexistenteException;

/**
 * LancamentoServiceImpl
 */
@Service
public class LancamentoServiceImpl implements LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    private final DateTimeFormatter DATA_FORMATO_PADRAO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Page<Lancamento> listar(LancamentoFilter lancamentoFilter, Pageable pageable) {

        return this.lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    @Override
    public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {

        return this.lancamentoRepository.resumir(lancamentoFilter, pageable);
    }

    @Override
    public Optional<Lancamento> listarPorId(Long id) {

        return this.lancamentoRepository.findById(id);
    }

    @Override
    public Lancamento criar(Lancamento lancamento) {

        this.validarPessoa(lancamento);

        return this.lancamentoRepository.save(lancamento);
    }

    @Override
    public void deletarPorId(Long id) {

        this.lancamentoRepository.deleteById(id);
    }

    @Override
    public Lancamento atualizar(Long id, Lancamento lancamento) {

        Lancamento lancamentoSalvo = this.buscarLancamentoExistente(id);

        // Verificar se a pessoa foi atualizada
        if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {

            validarPessoa(lancamento);
        }

        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");

        return this.lancamentoRepository.save(lancamentoSalvo);
    }

    @Override
    public List<LancamentosEstatisticaCategoriaDTO> porCategoria(String mesReferencia) {

        if (mesReferencia.equals("")) {

            return this.lancamentoRepository.porCategoria(LocalDate.now());
        }

        return this.lancamentoRepository.porCategoria(LocalDate.parse(mesReferencia, this.DATA_FORMATO_PADRAO));
    }

    @Override
    public List<LancamentosEstatisticaDiaDTO> porDia(String mesReferencia) {
        
        if(mesReferencia.equals("")) {

            this.lancamentoRepository.porDia(LocalDate.now());
        }

        return this.lancamentoRepository.porDia(LocalDate.parse(mesReferencia, this.DATA_FORMATO_PADRAO));
    }

    @Override
    public List<LancamentosEstatisticaPessoaDTO> porPessoa(String inicio, String fim) {
        
        if(inicio.equals("") || fim.equals("")) {

            LocalDate dataAtual = LocalDate.now();

            LocalDate inicioTemp = dataAtual.withDayOfMonth(1);
            LocalDate fimTemp = dataAtual.withDayOfMonth(dataAtual.lengthOfMonth());

            this.lancamentoRepository.porPessoa(inicioTemp, fimTemp);
        }

        LocalDate parseInicio = LocalDate.parse(inicio, this.DATA_FORMATO_PADRAO);
        LocalDate parseFim = LocalDate.parse(fim, this.DATA_FORMATO_PADRAO);

        return this.lancamentoRepository.porPessoa(parseInicio, parseFim);
    }

    /**
     * Validar a existência de uma pessoa contida no lançamento
     * 
     * @param lancamento
     */
    private void validarPessoa(Lancamento lancamento) {

        Optional<Pessoa> pessoa = Optional.empty();

        if (lancamento.getPessoa().getId() != null) {

            pessoa = this.pessoaRepository.findById(lancamento.getPessoa().getId());
        }

        if (pessoa.isEmpty() || pessoa.get().isInativo()) {

            throw new PessoaInativaOuInexistenteException();
        }
    }

    /**
     * Buscar lançamento contido na base de dados ou lança uma excessão
     * 
     * @param id
     * @return Lancamento
     */
    private Lancamento buscarLancamentoExistente(Long id) {

        return this.lancamentoRepository.findById(id).orElseThrow(() -> new LancamentoInexistenteException());
    }
    
}