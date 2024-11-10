package com.aluracursos.screenmatch.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosTemporadas(
        @JsonAlias("Season") Integer numero,
        @JsonAlias("Episodes") List<DatosEpisodio> episodios
) {
    @Override
    public String toString() {
        return "\nDATOS DE LA TEMPORADA: " +
                "\nNumero de temporada: " + numero +
                "\nEpisodios: " + episodios;
    }
}
