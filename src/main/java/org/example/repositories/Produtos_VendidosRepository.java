package org.example.repositories;

import org.example.entities.Produto_Vendido;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Produtos_VendidosRepository extends CrudRepository<Produto_Vendido, Integer> {
}
