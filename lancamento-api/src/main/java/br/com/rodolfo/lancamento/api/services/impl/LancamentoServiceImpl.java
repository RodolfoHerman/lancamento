package br.com.rodolfo.lancamento.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.models.Pessoa;
import br.com.rodolfo.lancamento.api.repositories.LancamentoRepository;
import br.com.rodolfo.lancamento.api.repositories.PessoaRepository;
import br.com.rodolfo.lancamento.api.services.LancamentoService;
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

    @Override
    public List<Lancamento> listar() {
        
        return this.lancamentoRepository.findAll();
    }

    @Override
    public Optional<Lancamento> listarPorId(Long id) {
        
        return this.lancamentoRepository.findById(id);
    }

    @Override
    public Lancamento criar(Lancamento lancamento) {

        Optional<Pessoa> pessoa = this.pessoaRepository.findById(lancamento.getPessoa().getId());

        if(pessoa.isEmpty() || pessoa.get().isInativo()) {

            throw new PessoaInativaOuInexistenteException();
        }

        return this.lancamentoRepository.save(lancamento);
    }
    
}