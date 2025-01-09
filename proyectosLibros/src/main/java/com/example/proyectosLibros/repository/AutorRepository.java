package com.example.proyectosLibros.repository;

import com.example.proyectosLibros.model.Autor;
import com.example.proyectosLibros.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombre(String nombreAutor);
    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libro")
    List<Autor> findAllAutorWithLibros();

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :fechaBuscada AND (a.fechaMuerte >= :fechaBuscada OR a.fechaMuerte IS NULL)")
    List<Autor> findAutoresByFecha(@Param("fechaBuscada") Integer fechaBuscada);

}
