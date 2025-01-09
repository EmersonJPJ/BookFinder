package com.example.proyectosLibros.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json,Class<T> clase);
}
