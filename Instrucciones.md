Recomiendo que al momento de descargar el contenido de este repositorio, cambiar la ruta dentro del método WebConfig.class,
del microservicio Articulos: 
en .addResourceLocations dentro de addResourceHandlers. 

Para definir la ruta 'imagenes' donde guardará/actualizará las imagenes obtenidas 
y llamadas desde Articulos.

Ejemplo: file:///C:/Users/senen/Documents/ExamenTecnico/Imagenes/. Y lo insertas
dentro de .addResourceLocations("aquí");
--De esta manera: "file:///C:/Users/senen/Documents/ExamenTecnico/Imagenes/". 

@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations(
  --Así:                  "file:///C:/Users/senen/Documents/ExamenTecnico/Imagenes/");
    }


Segunda cosa importante: en el pgadmin (postgresql) hay que ejecutar los scripts, que ya vienen en
la carpeta 'Scripts postgresql'.
ya que vienen definidos los procedimientos almacenados que llaman los 3 microservicios
que ejecutan/automatizan los procesos. Sino repositoty no servirá en Spring Boot.

Tercera cosa importante: 