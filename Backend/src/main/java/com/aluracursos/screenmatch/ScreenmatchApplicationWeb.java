package com.aluracursos.screenmatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// La anotación @SpringBootApplication es una de las anotaciones más importantes
// en una aplicación Spring Boot. Se utiliza para marcar la clase principal de
// la aplicación, la cual debe contener el método main desde donde se inicia la aplicación.
@SpringBootApplication
public class ScreenmatchApplicationWeb {

	public static void main(String[] args) {

		// Este método se utiliza para iniciar la aplicación Spring Boot
		// y poner en marcha el contenedor de Spring, configurando
		// automáticamente todos los componentes de la aplicación.
		SpringApplication.run(ScreenmatchApplicationWeb.class, args);
	}
}
