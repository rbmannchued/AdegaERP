package org.example.repositories;

import org.example.entities.Produto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Integer> {

      List<Produto> findByDescricaoContainingIgnoreCase(String descricao);
}
