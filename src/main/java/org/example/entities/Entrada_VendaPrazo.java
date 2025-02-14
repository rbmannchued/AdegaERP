package org.example.entities;

import jakarta.persistence.*;
import lombok.Generated;

import java.util.Date;

@Entity
public class Entrada_VendaPrazo {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "venda_id"
    )
    private Venda venda;

    private Float total;

    private Date data;

    private String forma_pagamento;

    private Float valor_recebido;

    private Float troco;

    private Float taxa;

    private Float valor_final;

    public Entrada_VendaPrazo() {

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
    public Venda getVenda() {
        return venda;
    }

    @Generated
    public void setVenda(Venda venda) {
        this.venda = venda;
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
    public Date getData() {
        return data;
    }

    @Generated
    public void setData(Date data) {
        this.data = data;
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
    public Float getTaxa() {
        return taxa;
    }

    @Generated
    public void setTaxa(Float taxa) {
        this.taxa = taxa;
    }

    @Generated
    public Float getValor_final() {
        return valor_final;
    }

    @Generated
    public void setValor_final(Float valor_final) {
        this.valor_final = valor_final;
    }

    public Entrada_VendaPrazo(final int id,
                              final Venda venda,
                              final Date data,
                              final String forma_pagamento,
                              final Float valor_recebido,
                              final Float troco,
                              final Float taxa,
                              final Float valor_final){
        this.id = id;
        this.venda = venda;
        this.data = data;
        this.forma_pagamento = forma_pagamento;
        this.valor_recebido = valor_recebido;
        this.troco = troco;
        this.taxa = taxa;
        this.valor_final = valor_final;
    }
}
