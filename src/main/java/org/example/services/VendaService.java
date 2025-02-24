package org.example.services;

import jakarta.transaction.Transactional;
import org.example.entities.Produto;
import org.example.entities.Venda;
import org.example.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {
    VendaRepository vendaRepository;
    @Autowired
    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    @Transactional
    public List<Venda> buscarTodos() {
        return (List<Venda>) vendaRepository.findAll();
    }
    @Transactional
    public void salvar(Venda venda) {
        vendaRepository.save(venda);
    }

    @Transactional
    public List<Object[]> contarVendasPorMes(int ano) {
        return vendaRepository.contarVendasPorMes(ano);
    }

}
