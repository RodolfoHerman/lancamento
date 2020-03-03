package br.com.rodolfo.lancamento.api.services.impl;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

        List<LocalDate> dias = this.validarData(mesReferencia, mesReferencia, true);

        return this.lancamentoRepository.porCategoria(dias.get(0), dias.get(1));
    }

    @Override
    public List<LancamentosEstatisticaDiaDTO> porDia(String mesReferencia) {

        List<LocalDate> dias = this.validarData(mesReferencia, mesReferencia, true);

        return this.lancamentoRepository.porDia(dias.get(0), dias.get(1));
    }

    @Override
    public List<LancamentosEstatisticaPessoaDTO> porPessoa(String inicio, String fim) {

        List<LocalDate> dias = this.validarData(inicio, fim, false);

        return this.lancamentoRepository.porPessoa(dias.get(0), dias.get(1));
    }

    @Override
    public byte[] relatorioPorPessoa(String inicio, String fim) throws JRException {

        List<LocalDate> dias = this.validarData(inicio, fim, false);

        List<LancamentosEstatisticaPessoaDTO> dados = this.lancamentoRepository.porPessoa(dias.get(0), dias.get(1));

        Map<String,Object> parametros = new HashMap<>();

        parametros.put("DT_INICIO", Date.valueOf(dias.get(0)));
        parametros.put("DT_FIM", Date.valueOf(dias.get(1)));
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

        InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");

        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
            new JRBeanCollectionDataSource(dados)
        );

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    /**
     * Verifica e retorna a data para passar por parâmetro
     * @param inicio
     * @param fim
     * @return List
     */
    private List<LocalDate> validarData(String inicio, String fim, boolean mes) {
        
        if(inicio.equals("") || fim.equals("")) {

            LocalDate data = LocalDate.now();

            return Arrays.asList(data.withDayOfMonth(1), data.withDayOfMonth(data.lengthOfMonth()));
        }

        if(mes) {

            LocalDate data = LocalDate.parse(inicio, this.DATA_FORMATO_PADRAO);;

            return Arrays.asList(data.withDayOfMonth(1), data.withDayOfMonth(data.lengthOfMonth()));
        }

        LocalDate data1 = LocalDate.parse(inicio, this.DATA_FORMATO_PADRAO);
        LocalDate data2 = LocalDate.parse(fim, this.DATA_FORMATO_PADRAO);

        return Arrays.asList(data1, data2);
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