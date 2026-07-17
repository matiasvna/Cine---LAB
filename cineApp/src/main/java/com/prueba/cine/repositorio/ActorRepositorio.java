package com.prueba.cine.repositorio;

import com.prueba.cine.modelo.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepositorio extends JpaRepository<Actor, Long> {
    // Verifica si el actor existe ignorando mayúsculas y minúsculas
    boolean existsByNombreIgnoreCase(String nombre);
}