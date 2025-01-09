package com.example.proyectosLibros.model;

public enum Idioma {

    ESPAÑOL("es"),
    INGLES ("en"),
    FRANCES("fr"),
    PORTUGUES("pt"),
    LATIN("la"),
    ALEMAN("de"),
    ITALIANO("it");

    private String idiomaLibro;

    Idioma (String idiomaLibro){
        this.idiomaLibro = idiomaLibro;
    }

    public static Idioma fromString(String text) {

        for (Idioma idioma: Idioma.values()) {
            if (idioma.idiomaLibro.equalsIgnoreCase(text)) {
                return idioma;
            }
        }

        throw new IllegalArgumentException();
    }

    public static Idioma fromTotalString(String text) {
        for (Idioma idioma: Idioma.values()) {
            if (idioma.idiomaLibro.equalsIgnoreCase(text)) {
                return idioma;
            }
        }

        throw new IllegalArgumentException();
    }


    public static void mostrarOpciones() {
        System.out.println("Idiomas disponibles:");
        for (Idioma idioma : Idioma.values()) {
            System.out.println("- " + idioma.name() + " (" + idioma.idiomaLibro + ")");
        }
    }

}
