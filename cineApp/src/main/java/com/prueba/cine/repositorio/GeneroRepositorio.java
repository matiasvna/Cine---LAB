package com.prueba.cine.repositorio;

import com.prueba.cine.modelo.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepositorio extends JpaRepository<Genero, Long> {
    // Ojo aquí: en la clase Genero la variable se llama "titulo", no "nombre"
    boolean existsByTituloIgnoreCase(String titulo); 
}