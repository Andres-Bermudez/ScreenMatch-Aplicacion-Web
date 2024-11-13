package com.aluracursos.screenmatch.servicios;

import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.modelos.Serie;
import com.aluracursos.screenmatch.repositorio.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// @Service se utiliza para declarar que una clase es un servicio que contiene la lógica
// de negocio y debe ser gestionada por el contenedor de Spring, facilitando así la
// organización y el mantenimiento del código en aplicaciones Spring.
@Service
public class SerieServicio {

    // Esta anotación se utiliza para que Spring realice la inyección de dependencias.
    // Aquí, Spring inyecta una instancia de SerieRepository en el controlador para
    // que pueda acceder a los métodos de repository y realizar operaciones con la base de datos.
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> getListaSeries() {
        return convertirAListaSerieDTO(repository.todasLasSeries());
    }

    public List<SerieDTO> getTopSeries() {
        return convertirAListaSerieDTO(repository.findTop8ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> getUltimosLanzamientos() {
        return convertirAListaSerieDTO(repository.ultimasSeriesAgregadas());
    }

    public SerieDTO getDatosSeriePorId(Long id) {
        Optional<Serie> seriePorId = repository.findById(id);
        if (seriePorId.isPresent()) {
            Serie s = seriePorId.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(),
                    s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis());
        }
        return null;
    }

    public List<SerieDTO> convertirAListaSerieDTO(List<Serie> listaSerie) {
        return listaSerie.stream()
                .map(serie -> new SerieDTO(serie.getId(), serie.getTitulo(), serie.getTotalTemporadas(), serie.getEvaluacion(),
                        serie.getPoster(), serie.getGenero(), serie.getActores(), serie.getSinopsis()))
                .collect(Collectors.toList());
    }
}
