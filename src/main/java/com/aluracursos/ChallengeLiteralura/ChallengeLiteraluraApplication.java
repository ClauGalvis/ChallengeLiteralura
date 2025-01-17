package com.aluracursos.ChallengeLiteralura;

import com.aluracursos.ChallengeLiteralura.principal.Principal;
import com.aluracursos.ChallengeLiteralura.repositorio.AutorRepository;
import com.aluracursos.ChallengeLiteralura.repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraApplication implements CommandLineRunner {
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;


	public static void main(String[] args) {

		SpringApplication.run(ChallengeLiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal Principal = new Principal(libroRepository, autorRepository);
		Principal.mostrarMenu();
	}

}
