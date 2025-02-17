package org.example.repositories;

import org.example.entities.Bebida;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BebidaRepository extends CrudRepository<Bebida, Integer> {

      List<Bebida> findByDescricaoContainingIgnoreCase(String descricao);
}
