package org.example.services;

import org.example.entities.Bebida;
import  org.example.repositories.BebidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BebidaService {
    BebidaRepository bebidaRepository;
    @Autowired
    public BebidaService(BebidaRepository bebidaRepository) {
        this.bebidaRepository = bebidaRepository;
    }

    @Transactional
    public void salvar(Bebida bebida) {
        bebidaRepository.save(bebida);
    }
    @Transactional
    public List<Bebida> buscarTodos() {
        return (List<Bebida>) bebidaRepository.findAll();
    }
    @Transactional
    public Bebida inserirBebida(String descricao, float quantidade, float preco, String codigoBarras, boolean nf) {
        Bebida bebida = new Bebida();
        bebida.setDescricao(descricao);
        bebida.setQuantidade(quantidade);
        bebida.setPreco(preco);
        bebida.setCodigo_barras(codigoBarras);
        bebida.setNf(nf);

        return bebidaRepository.save(bebida); // Retorna o objeto salvo no banco
    }

}
