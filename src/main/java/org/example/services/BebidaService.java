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
}
