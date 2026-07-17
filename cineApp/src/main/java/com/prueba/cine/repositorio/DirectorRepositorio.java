package com.prueba.cine.repositorio;

import com.prueba.cine.modelo.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepositorio extends JpaRepository<Director, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}