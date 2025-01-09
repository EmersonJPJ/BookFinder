package com.example.proyectosLibros.principal;

import com.example.proyectosLibros.model.*;
import com.example.proyectosLibros.repository.AutorRepository;
import com.example.proyectosLibros.repository.LibroRepository;
import com.example.proyectosLibros.service.ConsumoAPI;
import com.example.proyectosLibros.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;


public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    //private final String URL_BASE = "https://gutendex.com/books/84/";

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;


    public Principal(LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


    public void mostrarMenu() {
        var opcion = -1;

        while(opcion != 0){
                var menu = """
                        1- Buscar libro por titulo en el API
                        2- Listar libros registrados
                        3- Listar autores registrados
                        4- Listar autores vivos en un determinado año
                        5- Listar libros por idioma
                        0- Salir
                        """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    almacenaDB();
                    break;
                case 2:
                    consultarBD();
                    break;
                case 3:
                    consultarAutoresBD();
                    break;
                case 4:
                    consultarPorFecha();
                    break;
                case 5:
                    consultarPorIdioma();
                    break;
                case 0:
                    System.out.println("Gracias por utilizar la aplicación. Cerrando el servicio...");
                    break;
                default:
                    System.out.println("Opción inválida, intente con otra opción");
                    break;

            }
        }
    }


    public DatosLibros getLibroAPI(){
        System.out.println("Escribe el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine().toLowerCase().replace(" ", "%20");

        var json = consumoApi.obtenerDatos(URL_BASE+nombreLibro);
        System.out.println(URL_BASE+nombreLibro);

        System.out.println(json);

        Datos datos = conversor.obtenerDatos(json,Datos.class);
        System.out.println(datos);


        var nombreLibroBusqueda = nombreLibro.replace("%20", " ").toUpperCase(); // Reconstruye el nombre para el filtro

        return datos.resultados().stream()
                .filter(datosLibros -> datosLibros.titulo() != null)
                .filter(datosLibros -> datosLibros.titulo().toUpperCase().contains(nombreLibroBusqueda))
                .findFirst()
                .orElse(null);

    }



    public void almacenaDB() {
        DatosLibros datos = getLibroAPI();

        System.out.printf("""
            Detalles del libro encontrado:
            Título: %s
            Autor(es): %s
            Idioma(s): %s
            Descargas: %.2f
            """,
                datos.titulo(),
                datos.autor().stream()
                        .map(datosAutor -> datosAutor.nombre())
                        .collect(Collectors.joining(", ")),
                String.join(", ", datos.idioma()),
                datos.descargas()
        );

        if (datos == null) {
            System.out.println("El libro no existe en el API.");
            return;
        }
        List<Autor> autores = datos.autor().stream()
                .map(datosAutor -> autorRepository.findByNombre(datosAutor.nombre())
                        .orElseGet(() -> {

                            Autor nuevoAutor = new Autor();
                            nuevoAutor.setNombre(datosAutor.nombre());
                            nuevoAutor.setFechaNacimiento(datosAutor.fechaNacimiento());
                            nuevoAutor.setFechaMuerte(datosAutor.fechaMuerte());
                            autorRepository.save(nuevoAutor);
                            return nuevoAutor;
                        })
                ).collect(Collectors.toList());

        try {
            Libros libro = new Libros(datos);
            libro.setAutor(autores.get(0));
            libroRepository.save(libro);
            System.out.println("Este libro se almacenó en la BD: " + datos);


        } catch(DataIntegrityViolationException e) {
            System.out.println("""
                       Ese libro ya está registrado, intente con otro:
                       """
                    +datos);
        }
    }


    private void consultarBD() {
        List<Libros> todosLosLibros = libroRepository.findAll();

        System.out.println("\nLibros registrados en la Base de Datos:\n");
        todosLosLibros.forEach(s -> System.out.println(
                """
                Título: %s - Autor: %s - Idiomas: %s - Descargas: %.2f
                """.formatted(
                        s.getTitulo(),
                        s.getAutor().getNombre(),
                        s.getIdioma(),
                        s.getDescargas()
                )
        ));
    }


    private void consultarAutoresBD() {
        List<Autor> autores = autorRepository.findAllAutorWithLibros();

        System.out.println("\nAutores registrados en la BD: \n");
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(autor -> {
                    System.out.println("Autor: " + autor.getNombre());
                    System.out.println("Año de nacimiento: " + autor.getFechaNacimiento());
                    System.out.println("Año de muerte: " + (autor.getFechaMuerte() != null ? autor.getFechaMuerte() : "Desconocido"));

                    if (!autor.getLibro().isEmpty()) {
                        System.out.println("Libros:");
                        autor.getLibro().forEach(libro -> System.out.println("  - " + libro.getTitulo()));
                    } else {
                        System.out.println("No tiene libros asociados.");
                    }
                    System.out.println();
                });
    }


    private void consultarPorFecha() {
        System.out.println("Digite el año para consultar los autores vivos de esa época: ");

        var fechaIngresada = teclado.nextLine();

        try {
            int fecha = Integer.parseInt(fechaIngresada.trim());
            List<Autor> autoresVivos = autorRepository.findAutoresByFecha(fecha);

            if (!autoresVivos.isEmpty()) {
                System.out.println("\nAutores vivos en el año " + fecha + ":\n");
                autoresVivos.forEach(autor -> {
                    String fechaMuerte = (autor.getFechaMuerte() != null) ? String.valueOf(autor.getFechaMuerte()) : "Aún vivo";
                    System.out.println(
                            String.format("%s - Año de nacimiento: %d  - Año de fallecimiento: %s",
                                    autor.getNombre(),
                                    autor.getFechaNacimiento(),
                                    fechaMuerte)
                    );
                });
            } else {
                System.out.println("No hay autores vivos en el año " + fecha + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nEsa fecha no es válida\n");
        }
    }


    private void consultarPorIdioma() {
        try {
            Idioma.mostrarOpciones();

            System.out.println("\nPor favor digita el idioma de los libros que deseas consultar:");

            var idioma = teclado.nextLine();
            var idiomaEscogido = Idioma.fromString(idioma.toLowerCase());

            Long cantidadLibrosPorIdioma = libroRepository.contarLibrosPorIdioma(idiomaEscogido);
            System.out.println("\n\n La cantidad de libro disponibles en " + idioma + " : " + cantidadLibrosPorIdioma + "\n\n");

            if (cantidadLibrosPorIdioma > 0) {
                List<Libros> idiomaLibros = libroRepository.findByIdioma(idiomaEscogido);
                System.out.println("\n\n Libros disponibles en el idioma " + idioma + " que estan registrados en la BD:");
                idiomaLibros.forEach(libro -> System.out.println(" - " + libro.getTitulo()));
            } else {
                System.out.println("No hay libros disponibles en el idioma " + idioma + ".");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("No esta disponible ese idioma");
        } catch (Exception e) {
            System.out.println("Hubo un error al consultar los libros: " + e.getMessage());
        }
    }






}





