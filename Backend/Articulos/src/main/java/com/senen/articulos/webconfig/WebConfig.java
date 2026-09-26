/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.articulos.webconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author senen
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${ruta.imagen.resource}")
    private String rutaImagen;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/imagenes/**")
                //Asi debe de apuntar para Docker. Asi apunta a la ruta imagenes dentro del proyecto
                //.addResourceLocations("file:/app/imagenes");
                .addResourceLocations(rutaImagen);
    }
}