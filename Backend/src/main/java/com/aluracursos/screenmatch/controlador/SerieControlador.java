package com.aluracursos.screenmatch.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerieControlador {

    @GetMapping("/series")
    public String mostrarMensaje() {
        return "Este es mi primer mensaje utilizando Spring web." +
                "\nAutor: Andres Bermudez";
    }
}
