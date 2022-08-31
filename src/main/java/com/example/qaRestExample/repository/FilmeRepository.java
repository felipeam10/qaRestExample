package com.example.qaRestExample.repository;

import com.example.qaRestExample.model.Filme;
import org.springframework.data.repository.CrudRepository;

public interface FilmeRepository extends CrudRepository<Filme, Integer> {
}
