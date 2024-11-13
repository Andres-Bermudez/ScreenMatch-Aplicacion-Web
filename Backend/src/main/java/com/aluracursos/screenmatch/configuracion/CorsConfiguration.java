package com.aluracursos.screenmatch.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * La clase CorsConfiguration en Spring Framework proporciona una forma
 *          programática de configurar las reglas de CORS para tu aplicación.
 *
 * Con esta clase, puedes especificar:
 *
 *   1. Qué orígenes tienen permiso para acceder a los recursos.
 *   2. Qué métodos HTTP (GET, POST, PUT, DELETE, etc.) están permitidos.
 *   3. Qué encabezados (headers) pueden ser utilizados.
 *   4. Si se permite el uso de credenciales (cookies, headers de autenticación, etc.).
 *   5. El tiempo de vida de la configuración en caché.
*/

// @Configuration en Spring Framework se utiliza para indicar que una clase es
// una fuente de definiciones de beans. Estas clases son utilizadas por Spring
// para generar y gestionar beans y sus dependencias en el contenedor de
// inversión de control (IoC).
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    /*
     * CORS es un protocolo que define cómo se puede acceder a los recursos
     * de una aplicación web desde otro dominio.
    */

    // Este método se utiliza para agregar configuraciones de mapeo de CORS al
    // registro de CORS de Spring.
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // El asterisco doble "/**" es una expresión que indica que esta
        // configuración se aplica a todas las rutas en la aplicación:
        registry.addMapping("/**")

                // Esta línea especifica que solo se permite el origen http://127.0.0.1:5500
                // para realizar solicitudes a la aplicación. Esto significa que solo las
                // solicitudes procedentes de esta dirección IP y puerto serán permitidas
                // por la configuración CORS:
                .allowedOrigins("http://127.0.0.1:5500")

                // Esta línea especifica los métodos HTTP permitidos para las solicitudes:
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS",
                        "HEAD", "TRACE", "CONNECT");
    }
}

