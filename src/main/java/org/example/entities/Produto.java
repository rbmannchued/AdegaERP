package org.example.entities;

import jakarta.persistence.*;
import lombok.Generated;

import java.util.List;

@Entity
public class Produto {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;

    private String descricao;

    private Float quantidade;

    private Float preco;

    private String codigo_barras;

    private Boolean nf;

    @OneToMany(
            mappedBy = "produto"
    )
    private List<Produto_Vendido> produtosVendidosList;

    @Generated
    public int getId() {
        return id;
    }

    @Generated
    public void setId(int id) {
        this.id = id;
    }

    @Generated
    public String getDescricao() {
        return descricao;
    }

    @Generated
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Generated
    public Float getQuantidade() {
        return quantidade;
    }

    @Generated
    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }

    @Generated
    public Float getPreco() {
        return preco;
    }

    @Generated
    public void setPreco(Float preco) {
        this.preco = preco;
    }

    @Generated
    public String getCodigo_barras() {
        return codigo_barras;
    }

    @Generated
    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    @Generated
    public Boolean getNf() {
        return nf;
    }

    @Generated
    public void setNf(Boolean nf) {
        this.nf = nf;
    }

    @Generated
    public Produto(final int id, final String descricao, final Float quantidade, final Float preco, final String codigo_barras, final Boolean nf, List<Produto_Vendido> produtosVendidosList){
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
        this.codigo_barras = codigo_barras;
        this.nf = nf;
        this.produtosVendidosList = produtosVendidosList;
    }

    public Produto() {

    }

    @Generated
    public List<Produto_Vendido> getProdutosVendidosList() {
        return produtosVendidosList;
    }

    @Generated
    public void setProdutosVendidosList(List<Produto_Vendido> produtosVendidosList) {
        this.produtosVendidosList = produtosVendidosList;
    }
}
