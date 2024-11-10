package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.credenciales.Credenciales;
import com.aluracursos.screenmatch.modelos.*;
import com.aluracursos.screenmatch.repositorio.SerieRepository;
import com.aluracursos.screenmatch.servicios.ConsumoAPI;
import com.aluracursos.screenmatch.servicios.ConvierteDatos;
import java.util.*;
import java.util.stream.Collectors;

public class Principal extends Credenciales {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series;
    private Optional<Serie> serieBuscada;

    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                        \n:::::::::::::::::::: INICIO ::::::::::::::::::::::
                            1. Buscar series.
                            2. Buscar episodios.
                            3. Mostrar series buscadas.
                            4. Buscar series por titulo.
                            5. Top 5 mejores series.
                            6. Buscar Series por categoría.
                            7. Filtrar series por temporadas y evaluación.
                            8. Buscar episodios por titulo.
                            9. Top 5 episodios por Serie.
                            0. Salir
                        """;
            System.out.print(menu + "\nTu eleccion: ");
            try {
                opcion = teclado.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nError. Recuerda elegir solo entre las opciones disponibles.");
            }
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriesPorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriesPorCategoria();
                    break;
                case 7:
                    filtrarSeriesPorTemporadaYEvaluacion();
                    break;
                case 8:
                    buscarEpisodiosPorTitulo();
                    break;
                case 9:
                    buscarTop5Episodios();
                    break;
                case 0:
                    System.out.println("\nCerrando la aplicación...");
                    break;
                default:
                    System.out.println("\nOpción inválida.");
            }
        }
    }

    private DatosSerie getDatosSerie() {
        System.out.print("\nEscribe el nombre de la serie que deseas buscar: ");
        var nombreSerie = teclado.nextLine();

        System.out.println("\nBuscando.....");
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&apikey=" + getAPI_KEY());
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.print("\nEscribe el nombre de la serie de la cual quieres ver los episodios: ");
        var nombreSerie = teclado.nextLine();

        System.out.println("\nBuscando.....");
        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + "&apikey=" + getAPI_KEY());
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriesPorTitulo(){
        System.out.print("\nEscribe el nombre de la serie que deseas buscar: ");
        var nombreSerie = teclado.nextLine();

        System.out.println("\nBuscando.....");
        serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if(serieBuscada.isPresent()){
            System.out.println("\nLa serie buscada es: " + serieBuscada.get());
        } else {
            System.out.println("\nError. Serie no encontrada.");
        }
    }

    private void buscarTop5Series(){
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s ->
                System.out.println("\nSerie: " + s.getTitulo() + " \nEvaluacion: " + s.getEvaluacion()) );
    }

    private void buscarSeriesPorCategoria(){
        System.out.print("\nEscriba el genero/categoría de la serie que desea buscar: ");
        var genero = teclado.nextLine();

        System.out.println("\nBuscando.....");
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);

        System.out.println("\nLas series de la categoría: " + genero);
        seriesPorCategoria.forEach(System.out::println);
    }

    public void filtrarSeriesPorTemporadaYEvaluacion(){
        System.out.print("\n¿Filtrar séries con cuántas temporadas?: ");
        var totalTemporadas = teclado.nextInt();
        teclado.nextLine();

        System.out.println("\n¿A partir de que valor evaluado buscas?: ");
        var evaluacion = teclado.nextDouble();
        teclado.nextLine();

        System.out.println("\nBuscando.....");
        List<Serie> filtroSeries = repositorio.seriesPorTemparadaYEvaluacion(totalTemporadas,evaluacion);
        System.out.println("\n*** Series filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println("\n" + s.getTitulo() + "\nEvaluacion: " + s.getEvaluacion()));
    }

    private void  buscarEpisodiosPorTitulo(){
        System.out.print("\nEscribe el nombre del episodio que deseas buscar: ");
        var nombreEpisodio = teclado.nextLine();

        System.out.println("\nBuscando.....");
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("\nSerie: %s \nTemporada %s \nEpisodio %s \nEvaluación %s",
                        e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
    }

    private void buscarTop5Episodios(){
        buscarSeriesPorTitulo();
        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorio.top5Episodios(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("\nSerie: %s \nTemporada %s \nEpisodio %s \nEvaluación %s",
                            e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));
        }
    }
}

