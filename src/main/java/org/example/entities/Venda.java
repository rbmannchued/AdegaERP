package org.example.entities;

import jakarta.persistence.*;
import lombok.Generated;

import java.util.Date;
import java.util.List;

@Entity
public class Venda {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;

    private String cliente;

    private Date data;

    private Float total;

    private String forma_pagamento;

    private Float valor_recebido;

    private Float troco;

    private Float taxa;

    private Float valor_final;

    private Float desconto;

    private Boolean esta_pago;

    @OneToMany(
            mappedBy = "venda"
    )
    private List<Produto_Vendido> produtos_vendidos;

    @OneToMany(
            mappedBy = "venda"
    )
    private List<Entrada_VendaPrazo> entradas_vendaPrazo;

    public Venda() {

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
    public String getCliente() {
        return cliente;
    }

    @Generated
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    @Generated
    public Date getData() {
        return data;
    }

    @Generated
    public void setData(Date data) {
        this.data = data;
    }

    @Generated
    public Float getTotal() {
        return total;
    }

    @Generated
    public void setTotal(Float total) {
        this.total = total;
    }

    @Generated
    public String getForma_pagamento() {
        return forma_pagamento;
    }

    @Generated
    public void setForma_pagamento(String forma_pagamento) {
        this.forma_pagamento = forma_pagamento;
    }

    @Generated
    public Float getValor_recebido() {
        return valor_recebido;
    }

    @Generated
    public void setValor_recebido(Float valor_recebido) {
        this.valor_recebido = valor_recebido;
    }

    @Generated
    public Float getTroco() {
        return troco;
    }

    @Generated
    public void setTroco(Float troco) {
        this.troco = troco;
    }

    @Generated
    public Float getValor_final() {
        return valor_final;
    }

    @Generated
    public void setValor_final(Float valor_final) {
        this.valor_final = valor_final;
    }

    @Generated
    public Float getDesconto() {
        return desconto;
    }

    @Generated
    public void setDesconto(Float desconto) {
        this.desconto = desconto;
    }

    @Generated
    public Boolean getEsta_pago() {
        return esta_pago;
    }

    @Generated
    public void setEsta_pago(Boolean esta_pago) {
        this.esta_pago = esta_pago;
    }

    @Generated
    public List<Produto_Vendido> getProdutos_vendidos() {
        return produtos_vendidos;
    }

    @Generated
    public void setProdutos_vendidos(List<Produto_Vendido> produtos_vendidos) {
        this.produtos_vendidos = produtos_vendidos;
    }

    public Venda(final int id,
                 final String cliente,
                 final Date data,
                 final Float total,
                 final String forma_pagamento,
                 final Float valor_recebido,
                 final Float troco,
                 final Float taxa,
                 final Float valor_final,
                 final Float desconto,
                 final Boolean esta_pago, List<Entrada_VendaPrazo> entradasVendaPrazo){
        this.id = id;
        this.cliente = cliente;
        this.data = data;
        this.total = total;
        this.forma_pagamento = forma_pagamento;
        this.valor_recebido = valor_recebido;
        this.troco = troco;
        this.taxa = taxa;
        this.valor_final = valor_final;
        this.desconto = desconto;
        this.esta_pago = esta_pago;
        entradas_vendaPrazo = entradasVendaPrazo;
    }

    @Generated
    public Float getTaxa() {
        return taxa;
    }

    @Generated
    public void setTaxa(Float taxa) {
        this.taxa = taxa;
    }

    @Generated
    public List<Entrada_VendaPrazo> getEntradas_vendaPrazo() {
        return entradas_vendaPrazo;
    }

    @Generated
    public void setEntradas_vendaPrazo(List<Entrada_VendaPrazo> entradas_vendaPrazo) {
        this.entradas_vendaPrazo = entradas_vendaPrazo;
    }
}
