package com.aluracursos.screenmatch.servicios;

public interface IConvierteDatos {

    // Método genérico que tiene como propósito convertir un
    // String en formato JSON a una instancia de una clase específica.
    <T> T obtenerDatos(String json, Class<T> clase);

    // <T>: Este es un parámetro de tipo genérico. Indica que el método
    // puede trabajar con cualquier tipo de dato. T es un marcador de
    // posición para el tipo real que será utilizado cuando el método sea invocado.
}
