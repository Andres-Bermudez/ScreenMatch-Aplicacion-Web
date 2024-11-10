package com.aluracursos.screenmatch.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEpisodio(
        @JsonAlias("Title") String titulo,
        @JsonAlias("Episode")Integer numeroEpisodio,
        @JsonAlias("imdbRating")String evaluacion,
        @JsonAlias("Released")String fechaDeLanzamiento
) {
    @Override
    public String toString() {
        return "\n\nDATOS DEL EPISODIO: " +
               "\nTitulo: " + titulo +
               "\nNumero de episodio: " + numeroEpisodio +
               "\nEvaluacion: " + evaluacion +
               "\nFechaDeLanzamiento: " + fechaDeLanzamiento;
    }
}
