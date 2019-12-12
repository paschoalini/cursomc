package com.paschoalini.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paschoalini.cursomc.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
