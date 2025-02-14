package org.example.repositories;

import org.example.entities.Entrada_VendaPrazo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Entrada_VendaPrazoRepository extends CrudRepository<Entrada_VendaPrazo, Integer> {
}
