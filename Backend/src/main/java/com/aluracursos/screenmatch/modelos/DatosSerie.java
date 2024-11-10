package com.aluracursos.screenmatch.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons")Integer totalTemporadas,
        @JsonAlias("imdbRating") String evaluacion,
        @JsonAlias("Poster")String poster,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Actors")String actores,
        @JsonAlias("Plot")String sinopsis
) {
    @Override
    public String toString() {
        return "\nDATOS DE LA SERIE: " +
                "\nTitulo: " + titulo +
                "\nTotal de temporadas: " + totalTemporadas +
                "\nEvaluacion: " + evaluacion +
                "\nImagen: " + poster +
                "\nGenero: " + genero +
                "\nActores: " + actores +
                "\nSinopsis: " + sinopsis;
    }
}
