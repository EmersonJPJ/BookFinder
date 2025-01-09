package com.example.proyectosLibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;


    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    private double descargas;


    public Libros(){}

    public Libros(DatosLibros datosLibro){
        this.titulo = datosLibro.titulo();
        this.autor = autor;
        this.descargas=datosLibro.descargas();


        if (!datosLibro.idioma().isEmpty()){
            this.idioma = idioma.fromString(datosLibro.idioma().get(0));
        }else{
            throw new IllegalArgumentException(("El idioma no es válido"));
        }
    }

    @Override
    public String toString() {
        return """
                Título del libro: %s
                Autor del libro : %s
                idioma del libro: %f
                Descargas del libro: %d
                """
                .formatted(titulo, autor, idioma, descargas);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public double getDescargas() {
        return descargas;
    }

    public void setDescargas(double descargas) {
        this.descargas = descargas;
    }


}
