package com.aluracursos.screenmatch.repositorio;

import com.aluracursos.screenmatch.modelos.Categoria;
import com.aluracursos.screenmatch.modelos.Episodio;
import com.aluracursos.screenmatch.modelos.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

// SerieRepository es una interfaz que extiende JpaRepository, lo cual permite
// realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) en la entidad Serie.
public interface SerieRepository extends JpaRepository<Serie,Long> {

    // Todas las series
    @Query("SELECT s FROM Serie s")
    List<Serie> todasLasSeries();

    // Busca una serie cuyo título contenga la cadena nombreSerie, ignorando mayúsculas y minúsculas.
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    // Obtiene las 8 series con la evaluación más alta, ordenadas en orden descendente.
    List<Serie> findTop8ByOrderByEvaluacionDesc();

    // Busca todas las series que pertenecen a un género específico representado por la entidad Categoria.
    List<Serie> findByGenero(Categoria categoria);
    //List<Serie> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, Double evaluacion);

    // Usa una consulta JPQL para buscar series que tengan un número de temporadas menor o igual a
    // totalTemporadas y una evaluación mayor o igual a evaluacion.
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadaYEvaluacion(int totalTemporadas, Double evaluacion);

    // Usa una consulta JPQL para buscar episodios cuyo título contenga la cadena nombreEpisodio,
    // utilizando una comparación insensible a mayúsculas y minúsculas (ILIKE).
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    // Usa una consulta JPQL para buscar los 8 episodios mejor evaluados de una serie específica,
    // ordenados en orden descendente por evaluación.
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);

    // Reto manos en la masa de Alura. Configurar el Endpoint "/series/id/temporadas/top".
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5EpisodiosTemporadaSerie(Long id);

    // Para obtener las ultimas series agregadas de la base de datos utilizando JPQL.
    @Query("SELECT s FROM Serie s " + "JOIN s.episodios e " + "GROUP BY s " + "ORDER BY MAX(e.fechaDeLanzamiento) DESC LIMIT 8")
    List<Serie> ultimasSeriesAgregadas();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<Episodio> episodiosPorTemporada(Long id, Integer numeroTemporada);
}
