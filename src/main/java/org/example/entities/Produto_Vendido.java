package org.example.entities;

import jakarta.persistence.*;
import lombok.Generated;

@Entity
public class Produto_Vendido {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "bebida_id"
    )
    private Bebida bebida;

    private Float quantidade;

    private Float preco_vendido;

    @ManyToOne
    @JoinColumn(
            name = "venda_id"
    )
    private Venda venda;

    public Produto_Vendido() {

    }

    @Generated
    public Integer getId() {
        return id;
    }

    @Generated
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated
    public Bebida getBebida() {
        return bebida;
    }

    @Generated
    public void setBebida(Bebida bebida) {
        this.bebida = bebida;
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
    public Float getPreco_vendido() {
        return preco_vendido;
    }

    @Generated
    public void setPreco_vendido(Float preco_vendido) {
        this.preco_vendido = preco_vendido;
    }

    @Generated
    public Produto_Vendido(final int id, final Float quantidade, final Float preco_vendido, final Bebida bebida, Venda venda){
        this.id = id;
        this.quantidade = quantidade;
        this.preco_vendido = preco_vendido;
        this.bebida = bebida;
        this.venda = venda;
    }

    @Generated
    public Venda getVenda() {
        return venda;
    }

    @Generated
    public void setVenda(Venda venda) {
        this.venda = venda;
    }
}
