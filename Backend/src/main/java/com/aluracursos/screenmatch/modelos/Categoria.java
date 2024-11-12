package com.aluracursos.screenmatch.modelos;

public enum Categoria {

    // Cada línea como ACCION("Action", "Acción") define una constante
    // del enum, asociando un nombre en inglés y en español a cada categoría.
    ACCION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIMEN("Crime", "Crimen");

    private String categoriaOmdb; // Almacena el nombre de la categoría en inglés.
    private String categoriaEspanol; // Almacena el nombre de la categoría en español.

    // Constructor
    Categoria (String categoriaOmdb, String categoriaEspanol){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    public static Categoria fromString(String text) {
        // Este método recorre todas las constantes del enum Categoria y compara
        // el texto ingresado con el campo categoriaOmdb (nombre en inglés) de
        // cada constante. Si encuentra una coincidencia, devuelve la constante
        // correspondiente. Si no, lanza una excepción IllegalArgumentException.
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("\nNinguna categoria encontrada: " + text);
    }

    public static Categoria fromEspanol(String text) {
        // Similar al método anterior, pero compara el texto ingresado con
        // el campo categoriaEspanol (nombre en español).
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEspanol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("\nNinguna categoria encontrada: " + text);
    }
}
