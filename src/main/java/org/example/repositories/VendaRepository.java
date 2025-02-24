package org.example.repositories;

import org.example.entities.Produto;
import org.example.entities.Venda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends CrudRepository<Venda, Integer> {

    @Query("SELECT MONTH(v.data), COUNT(v) FROM Venda v WHERE YEAR(v.data) = :ano GROUP BY MONTH(v.data) ORDER BY MONTH(v.data)")
    List<Object[]> contarVendasPorMes(@Param("ano") int ano);
}
