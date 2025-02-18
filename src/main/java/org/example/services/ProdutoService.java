package org.example.services;

import org.example.entities.Produto;
import org.example.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ProdutoService {
    ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public void salvar(Produto produto) {
        produtoRepository.save(produto);
    }

    @Transactional
    public List<Produto> buscarTodos() {
        return (List<Produto>) produtoRepository.findAll();
    }

    @Transactional
    public Produto inserirProduto(String descricao, float quantidade, float preco, String codigoBarras, boolean nf) {
        Produto produto = new Produto();
        produto.setDescricao(descricao);
        produto.setQuantidade(quantidade);
        produto.setPreco(preco);
        produto.setCodigo_barras(codigoBarras);
        produto.setNf(nf);

        return produtoRepository.save(produto); // Retorna o objeto salvo no banco
    }

    @Transactional
    public List<Produto> buscarPorDescricao(String descricao) {
        return produtoRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    @Transactional
    public void deletarProduto(int id) {
        produtoRepository.deleteById(id);
    }


}
