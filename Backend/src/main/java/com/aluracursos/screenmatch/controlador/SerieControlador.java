package com.aluracursos.screenmatch.controlador;

import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.repositorio.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Esta clase define un controlador REST en una aplicación Spring Boot
 * que maneja solicitudes HTTP relacionadas con la entidad Serie.
*/

// Esta anotación indica que la clase es un controlador REST, lo que
// significa que manejará solicitudes HTTP y devolverá respuestas JSON automáticamente.
@RestController
public class SerieControlador {

    // Esta anotación se utiliza para que Spring realice la inyección de dependencias.
    // Aquí, Spring inyecta una instancia del SerieRepository en el controlador para
    // que pueda acceder a los métodos de repositorio y realizar operaciones con la base de datos.
    @Autowired
    private SerieRepository repository;

    // Endpoint para Obtener la Lista de Series:
    // Esta anotación mapea las solicitudes HTTP GET a la URL /series a este método. Cuando
    // un cliente realiza una solicitud GET a /series, este método se ejecutará.
    @GetMapping("/series") // Endpoint al que se accede para obtener los datos
    public List<SerieDTO> getListaSeries() {
        return repository.findAll().stream()
                // Transforma cada objeto Serie en un objeto SerieDTO.
                .map(serie -> new SerieDTO(serie.getTitulo(), serie.getTotalTemporadas(), serie.getEvaluacion(),
                       serie.getPoster(), serie.getGenero(), serie.getActores(), serie.getSinopsis()))
                .collect(Collectors.toList());
    }

    @GetMapping("/test")
    // Este metodo se agrego para probar la dependencia de LiveReloading.
    public String testEndpoint() {
        return "Testeando esta aplicacion por medio de este Endpoint...";
    }
}
