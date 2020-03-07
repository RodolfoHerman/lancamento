package br.com.rodolfo.lancamento.api.services.impl;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
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
import br.com.rodolfo.lancamento.api.mail.Mailer;
import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.models.Pessoa;
import br.com.rodolfo.lancamento.api.models.Usuario;
import br.com.rodolfo.lancamento.api.repositories.LancamentoRepository;
import br.com.rodolfo.lancamento.api.repositories.PessoaRepository;
import br.com.rodolfo.lancamento.api.repositories.filters.LancamentoFilter;
import br.com.rodolfo.lancamento.api.repositories.projections.LancamentoResumo;
import br.com.rodolfo.lancamento.api.services.LancamentoService;
import br.com.rodolfo.lancamento.api.services.UsuarioService;
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

    private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
    
    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private Mailer mailer;

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
    public List<LancamentosEstatisticaCategoriaDTO> porCategoria(LocalDate data) {

        List<LocalDate> dias = this.getDatas(data);

        return this.lancamentoRepository.porCategoria(dias.get(0), dias.get(1));
    }

    @Override
    public List<LancamentosEstatisticaDiaDTO> porDia(LocalDate data) {

        List<LocalDate> dias = this.getDatas(data);

        return this.lancamentoRepository.porDia(dias.get(0), dias.get(1));
    }

    @Override
    public List<LancamentosEstatisticaPessoaDTO> porPessoa(LocalDate dataInicio, LocalDate dataFim) {

        return this.lancamentoRepository.porPessoa(dataInicio, dataFim);
    }

    @Override
    public byte[] relatorioPorPessoa(LocalDate dataInicio, LocalDate dataFim) throws JRException {

        List<LancamentosEstatisticaPessoaDTO> dados = this.lancamentoRepository.porPessoa(dataInicio, dataFim);

        Map<String,Object> parametros = new HashMap<>();

        parametros.put("DT_INICIO", Date.valueOf(dataInicio));
        parametros.put("DT_FIM", Date.valueOf(dataFim));
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

        InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");

        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
            new JRBeanCollectionDataSource(dados)
        );

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public void avisarSobreLancamentosVencidos() {

        List<Lancamento> vencidos = this.lancamentoRepository.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());

        List<Usuario> destinatarios = this.usuarioService.buscarPelaDescricaoDaPermissaoDoUsuario(DESTINATARIOS);

        this.mailer.avisarSoberLancamentosVencidos(vencidos, destinatarios);
    }

    /**
     * Retorna o período do início do mês até o último dia
     * @param data
     * @return
     */
    private List<LocalDate> getDatas(LocalDate data) {
        
        return Arrays.asList(data.withDayOfMonth(1), data.withDayOfMonth(data.lengthOfMonth()));
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