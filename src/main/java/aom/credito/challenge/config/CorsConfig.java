package aom.credito.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global de CORS para permitir que el frontend (por ejemplo, Angular)
 * consuma los endpoints expuestos por la API.
 *
 * Nota: Por simplicidad se permiten todos los orígenes para métodos de lectura.
 * En entornos productivos, reemplace allowedOrigins("*") por el/los origen(es)
 * explícitos (p. ej. "http://localhost:4200" o el dominio del front).
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*") // Reemplazar por orígenes específicos en PROD
                .allowedMethods("GET", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Content-Type")
                .maxAge(3600);
    }
}
