package dev.ruben.funkos.config.auth.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            // Ajustamos una configuración específica para cada serie de métodos
            // Así por cada fuente podemos permitir lo que queremos
            // Por ejemplo ene esta configuración solo permitirmos el dominio producto
            // Permitimos solo un dominio
            // e indicamos los verbos que queremos usar
            // Debes probar con uncliente desde ese puerto
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/funkos/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .maxAge(3600);
                registry.addMapping("/api/users/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .maxAge(3600);
                registry.addMapping("/api/auth/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .maxAge(3600);
            }

        };
    }
}
