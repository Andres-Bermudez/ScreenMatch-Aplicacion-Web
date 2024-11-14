package com.aluracursos.screenmatch.controlador;

import com.aluracursos.screenmatch.dto.EpisodioDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.servicios.SerieServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/*
 * El controlador es quien tiene contacto directo con el navegador.
 *
 * La única responsabilidad de un controlador es manejar la comunicación
 * y las rutas de la API.
 *
 * Esta clase define un controlador REST en una aplicación Spring Boot
 * que maneja solicitudes HTTP relacionadas con la entidad Serie.
*/

// Esta anotación indica que la clase es un controlador REST, lo que
// significa que manejará solicitudes HTTP y devolverá respuestas JSON automáticamente.
@RestController
@RequestMapping("/series") // @RequestMapping para que todas las urls mapeadas por el controlador de series tengan como prefijo el “/series”.
public class SerieControlador {

    // Esta anotación se utiliza para que Spring realice la inyección de dependencias.
    // Aquí, Spring inyecta una instancia del SerieServicio en el controlador para
    // que pueda acceder a los métodos de servicio y realizar operaciones con la base de datos.
    @Autowired
    private SerieServicio servicio;

    // Endpoint para Obtener la Lista de Series.
    // Esta anotación mapea las solicitudes HTTP GET a la URL /series a este método. Cuando
    // un cliente realiza una solicitud GET a /series, este método se ejecutará.
    @GetMapping()
    public List<SerieDTO> getListaSeries() {
        return servicio.getListaSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getTop5Series() {
        return servicio.getTopSeries();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> ultimasSeriesAgregadas() {
        return servicio.getUltimosLanzamientos();
    }

    // Uso de endpoints dinamicos para cargar los datos de cada
    // serie segun la que escoja el usuario.
    // Retornar los datos de una sola serie. Para buscar una serie,
    // necesitamos que su id sea pasado como parámetro. Usamos
    // el @PathVariable, que nos ayuda en ese objetivo.
    @GetMapping("/{id}")
    public SerieDTO getDatosSeriePorId(@PathVariable Long id) {
        return servicio.getDatosSeriePorId(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> getEpisodiosPorTemporada(@PathVariable Long id,
                                                      @PathVariable Integer numeroTemporada) {
        return servicio.getEpisodiosPorTemporada(id, numeroTemporada);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> getEpisodiosTemporadasSeries(@PathVariable Long id) {
        return servicio.getEpisodiosTemporadasSeries(id);
    }

    @GetMapping("/categoria/{genero}")
    public List<SerieDTO> getSeriesPorCategoria(@PathVariable String genero) {
        return servicio.getSeriesPorCategoria(genero);
    }

    // Reto manos en la masa de Alura. Configurar el Endpoint "/series/id/temporadas/top".
    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> getTop5EpisodiosTemporadaSerie(@PathVariable Long id) {
        return servicio.getTop5EpisodiosTemporadaSerie(id);
    }

    // Metodo para testear si esta funcionando nuestra aplicacion.
    @GetMapping("/test")
    // Este metodo se agrego para probar la dependencia de LiveReloading.
    public String testEndpoint() {
        return "Testeando esta aplicacion por medio de este Endpoint...";
    }
}
