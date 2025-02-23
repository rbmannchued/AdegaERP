package org.example.repositories;

import org.example.entities.Produto_Vendido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Produtos_VendidosRepository extends CrudRepository<Produto_Vendido, Integer> {


        @Query("SELECT pv FROM Produto_Vendido pv JOIN FETCH pv.produto WHERE pv.venda.id = :vendaId")
        List<Produto_Vendido> findByVendaId(@Param("vendaId") Integer vendaId);




}
