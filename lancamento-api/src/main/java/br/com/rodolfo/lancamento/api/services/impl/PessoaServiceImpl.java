package br.com.rodolfo.lancamento.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.rodolfo.lancamento.api.models.Pessoa;
import br.com.rodolfo.lancamento.api.repositories.PessoaRepository;
import br.com.rodolfo.lancamento.api.services.PessoaService;

/**
 * PessoaServiceImpl
 */
@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public List<Pessoa> listar() {
        
        return this.pessoaRepository.findAll();
    }

    @Override
    public Optional<Pessoa> listarPorId(Long id) {
        
        return this.pessoaRepository.findById(id);
    }

    @Override
    public Pessoa criar(Pessoa pessoa) {
        
        return this.pessoaRepository.save(pessoa);
    }

    @Override
    public void deletarPorId(Long id) {

        this.pessoaRepository.deleteById(id);
    }

    @Override
    public Pessoa atualizar(Long id, Pessoa pessoa) {

        Optional<Pessoa> pessoaSalva = this.pessoaRepository.findById(id);

        if(pessoaSalva.isEmpty()) {

            throw new EmptyResultDataAccessException(1);
        }

        BeanUtils.copyProperties(pessoa, pessoaSalva.get(), "id");
        return this.pessoaRepository.save(pessoaSalva.get());
    }
}