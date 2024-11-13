package com.aluracursos.screenmatch.dto;

import com.aluracursos.screenmatch.modelos.Categoria;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double evaluacion,
        String poster,
        Categoria genero,
        String actores,
        String sinopsis
) {
}
