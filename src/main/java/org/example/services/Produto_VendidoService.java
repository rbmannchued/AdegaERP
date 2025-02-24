package org.example.services;

import jakarta.transaction.Transactional;
import org.example.entities.Produto;
import org.example.entities.Produto_Vendido;
import org.example.repositories.Produtos_VendidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Produto_VendidoService {
    Produtos_VendidosRepository produtos_VendidosRepository;

    @Autowired
    public void Produtos_VendidosRepository(Produtos_VendidosRepository produtoVendidoRepository) {
        this.produtos_VendidosRepository = produtoVendidoRepository;
    }

    @Transactional
    public void salvar(Produto_Vendido produtoVendido) {
        produtos_VendidosRepository.save(produtoVendido);
    }

    @Transactional
    public List<Produto_Vendido> findProdutosVendidosByVendaId(Integer vendaId) {
        return produtos_VendidosRepository.findByVendaId(vendaId);
    }

}
