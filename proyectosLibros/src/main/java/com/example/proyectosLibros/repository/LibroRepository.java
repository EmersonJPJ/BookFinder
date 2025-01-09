package com.example.proyectosLibros.repository;

import com.example.proyectosLibros.model.Idioma;
import com.example.proyectosLibros.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LibroRepository extends JpaRepository<Libros,Long> {

    List<Libros> findAll();

    List<Libros> findByIdioma(Idioma idioma);


    @Query("SELECT COUNT(l) FROM Libros l WHERE l.idioma = :idioma")
    Long contarLibrosPorIdioma(@Param("idioma") Idioma idioma);


}
