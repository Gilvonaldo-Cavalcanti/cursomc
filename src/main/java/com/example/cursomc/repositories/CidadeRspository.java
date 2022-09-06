package com.example.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.cursomc.domain.Cidade;

@Repository
public interface CidadeRspository extends JpaRepository<Cidade, Integer>{

}
