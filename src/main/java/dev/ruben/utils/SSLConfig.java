package dev.ruben.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class SSLConfig {

    private final ResourceLoader resourceLoader;
    public SSLConfig(@Qualifier("webApplicationContext")ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public void printKeystoreLocation() {
        try {
            Resource resource = resourceLoader.getResource("classpath:/cert/serverstore.jks");
            System.out.println("Ubicación del keystore: " + resource.getURL().getPath());
        } catch (Exception e) {
            System.err.println("Error al obtener la ubicación del keystore: " + e.getMessage());
        }
    }
}

