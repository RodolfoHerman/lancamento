package br.com.rodolfo.lancamento.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfo.lancamento.api.models.Lancamento;
import br.com.rodolfo.lancamento.api.repositories.LancamentoRepository;
import br.com.rodolfo.lancamento.api.services.LancamentoService;

/**
 * LancamentoServiceImpl
 */
@Service
public class LancamentoServiceImpl implements LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

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

        return this.lancamentoRepository.save(lancamento);
    }
    
}