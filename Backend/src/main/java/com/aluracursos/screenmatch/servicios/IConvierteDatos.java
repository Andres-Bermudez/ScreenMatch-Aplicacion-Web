package com.aluracursos.screenmatch.servicios;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
