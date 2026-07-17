package com.prueba.cine.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.prueba.cine.modelo.Pelicula;
import com.prueba.cine.modelo.Genero;
import com.prueba.cine.modelo.Actor;
import com.prueba.cine.modelo.Director;
import com.prueba.cine.servicio.PeliculaServicio;
import com.prueba.cine.servicio.GeneroServicio;
import com.prueba.cine.repositorio.ActorRepositorio;
import com.prueba.cine.repositorio.DirectorRepositorio;

@Controller
@RequestMapping("/peliculas")
public class PeliculaControlador {

    @Autowired
    private PeliculaServicio peliculaServ;

    @Autowired
    private GeneroServicio generoServ;

    // Inyectamos los nuevos repositorios para enviarlos a la vista
    @Autowired
    private ActorRepositorio actorRep;

    @Autowired
    private DirectorRepositorio directorRep;

    @GetMapping
    public String peliculas(Model model) {
        List<Pelicula> peliculas = peliculaServ.findAll();
        model.addAttribute("peliculas", peliculas);
        return "peliculas";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        // Enviamos las listas para los menús desplegables del HTML
        model.addAttribute("generos", generoServ.findAll());
        model.addAttribute("actores", actorRep.findAll());
        model.addAttribute("directores", directorRep.findAll());
        return "aggPelicula";
    }

    // AQUI ESTA LA CORRECCION: Le agregamos "/guardar" para que atrape el formulario
    @PostMapping("/guardar")
    public String savePelicula(@ModelAttribute("pelicula") Pelicula pelicula) {
        peliculaServ.save(pelicula);
        return "redirect:/peliculas";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Pelicula> peliculaOptional = peliculaServ.findById(id);
        if (peliculaOptional.isPresent()) {
            model.addAttribute("pelicula", peliculaOptional.get());
            // Enviamos las listas para los menús desplegables del HTML
            model.addAttribute("generos", generoServ.findAll());
            model.addAttribute("actores", actorRep.findAll());
            model.addAttribute("directores", directorRep.findAll());
            return "editar";
        } else {
            return "redirect:/peliculas"; 
        }
    }

    @PostMapping("/editar/{id}")
    public String updatePelicula(@PathVariable("id") Long id, @ModelAttribute("pelicula") Pelicula peliculaActualizada) {
        Optional<Pelicula> peliculaOptional = peliculaServ.findById(id);
        if (peliculaOptional.isPresent()) {
            Pelicula existingPelicula = peliculaOptional.get();
            
            existingPelicula.setTitulo(peliculaActualizada.getTitulo());
            existingPelicula.setAño(peliculaActualizada.getAño());
            
            // Ya no usamos setProtagonista()
            
            // Actualizar Género
            if (peliculaActualizada.getGenero() != null && peliculaActualizada.getGenero().getId() != null) {
                existingPelicula.setGenero(peliculaActualizada.getGenero());
            }

            // Actualizar Director
            if (peliculaActualizada.getDirector() != null && peliculaActualizada.getDirector().getId() != null) {
                existingPelicula.setDirector(peliculaActualizada.getDirector());
            }

            // Actualizar Actores (Al ser una lista, se reemplaza completa)
            if (peliculaActualizada.getActores() != null) {
                existingPelicula.setActores(peliculaActualizada.getActores());
            }
            
            peliculaServ.save(existingPelicula);
            return "redirect:/peliculas";
        } else {
            return "redirect:/peliculas";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePelicula(@PathVariable("id") Long id) {
        peliculaServ.deleteById(id);
        return "redirect:/peliculas";
    }
}