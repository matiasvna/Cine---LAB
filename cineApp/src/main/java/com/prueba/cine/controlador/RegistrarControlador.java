package com.prueba.cine.controlador;

import com.prueba.cine.modelo.Actor;
import com.prueba.cine.modelo.Director;
import com.prueba.cine.modelo.Genero;
import com.prueba.cine.repositorio.ActorRepositorio;
import com.prueba.cine.repositorio.DirectorRepositorio;
import com.prueba.cine.repositorio.GeneroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrarControlador {

    @Autowired
    private ActorRepositorio actorRep;
    
    @Autowired
    private DirectorRepositorio directorRep;
    
    @Autowired
    private GeneroRepositorio generoRep;

    // --- RUTAS PARA ACTORES ---
    @GetMapping("/actores/new")
    public String formActor(Model model) {
        model.addAttribute("actor", new Actor());
        return "aggActor"; 
    }

    @PostMapping("/actores")
    public String saveActor(@ModelAttribute("actor") Actor actor, Model model) {
        if (actorRep.existsByNombreIgnoreCase(actor.getNombre().trim())) {
            model.addAttribute("error", "Este actor ya se encuentra registrado.");
            return "aggActor"; // Vuelve al formulario mostrando el error
        }
        actorRep.save(actor);
        return "redirect:/"; 
    }

    // --- RUTAS PARA DIRECTORES ---
    @GetMapping("/directores/new")
    public String formDirector(Model model) {
        model.addAttribute("director", new Director());
        return "aggDirector"; 
    }

    @PostMapping("/directores")
    public String saveDirector(@ModelAttribute("director") Director director, Model model) {
        if (directorRep.existsByNombreIgnoreCase(director.getNombre().trim())) {
            model.addAttribute("error", "Este director ya se encuentra registrado.");
            return "aggDirector";
        }
        directorRep.save(director);
        return "redirect:/";
    }

    // --- RUTAS PARA GÉNEROS ---
    @GetMapping("/generos/new")
    public String formGenero(Model model) {
        model.addAttribute("genero", new Genero());
        return "aggGenero"; 
    }

    @PostMapping("/generos")
    public String saveGenero(@ModelAttribute("genero") Genero genero, Model model) {
        if (generoRep.existsByTituloIgnoreCase(genero.getTitulo().trim())) {
            model.addAttribute("error", "Este género ya se encuentra registrado.");
            return "aggGenero";
        }
        generoRep.save(genero);
        return "redirect:/";
    }
}