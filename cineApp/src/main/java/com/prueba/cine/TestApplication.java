package com.prueba.cine;

import com.prueba.cine.modelo.Usuario;
import com.prueba.cine.modelo.Genero;
import com.prueba.cine.modelo.Director;
import com.prueba.cine.repositorio.UsuarioRepositorio;
import com.prueba.cine.repositorio.GeneroRepositorio;
import com.prueba.cine.repositorio.DirectorRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(
            UsuarioRepositorio usuarioRep,
            GeneroRepositorio generoRep,
            DirectorRepositorio directorRep,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            // 1. user 1 (admin)
            if (usuarioRep.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); 
                usuarioRep.save(admin);
                System.out.println(">>> Usuario 'admin' creado con éxito.");
            }

            // 1.1 usuario prof
            if (usuarioRep.findByUsername("profesor").isEmpty()) {
                Usuario profesor = new Usuario();
                profesor.setUsername("profesor");
                profesor.setPassword(passwordEncoder.encode("unpa2026")); 
                usuarioRep.save(profesor);
                System.out.println(">>> Usuario 'profesor' creado con éxito.");
            }

            // 2. Autocompletar generos (Solo si la tabla está vacia)
            if (generoRep.count() == 0) {
                Genero g1 = new Genero(); g1.setTitulo("Acción");
                Genero g2 = new Genero(); g2.setTitulo("Comedia");
                Genero g3 = new Genero(); g3.setTitulo("Drama");
                Genero g4 = new Genero(); g4.setTitulo("Aventura");
                Genero g5 = new Genero(); g5.setTitulo("Ciencia Ficción");
                Genero g6 = new Genero(); g6.setTitulo("Terror");
                Genero g7 = new Genero(); g7.setTitulo("Suspenso");
                
                // Guardamos todos de golpe usando Arrays.asList
                generoRep.saveAll(Arrays.asList(g1, g2, g3, g4, g5, g6, g7));
                System.out.println(">>> Géneros por defecto insertados.");
            }

            // 3. Autocompletar directores (vacia)
            if (directorRep.count() == 0) {
                Director d1 = new Director(); d1.setNombre("Christopher Nolan");
                Director d2 = new Director(); d2.setNombre("Steven Spielberg");
                Director d3 = new Director(); d3.setNombre("Quentin Tarantino");
                Director d4 = new Director(); d4.setNombre("Martin Scorsese");
                Director d5 = new Director(); d5.setNombre("James Cameron");
                
                directorRep.saveAll(Arrays.asList(d1, d2, d3, d4, d5));
                System.out.println(">>> Directores por defecto insertados.");
            }
        };
        
    }
}