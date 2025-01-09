package com.example.proyectosLibros;

import com.example.proyectosLibros.principal.Principal;
import com.example.proyectosLibros.repository.AutorRepository;
import com.example.proyectosLibros.repository.LibroRepository;
import com.example.proyectosLibros.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectosLibrosApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;
	public static void main(String[] args) {
		SpringApplication.run(ProyectosLibrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository,autorRepository);
		principal.mostrarMenu();
	}
}
