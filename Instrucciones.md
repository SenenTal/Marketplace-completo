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

--Lo mismo para las rutas dentro de ArticulosServiceImpl, hay 2 métodos donde guardan la imagen
del articulo. Para ser exactos en 'public Articulos crearArticulo' y 'ArticulosCategoriaDTO actualizarArticulo2',
en la clase: 'ArticulosServiceImpl' donde obtienen 
Path carpeta = Paths.get("C:\\Users\\senen\\Documents\\ExamenTecnico\\Imagenes");
--Solamente hay que cambiar la ruta (dependiendo donde se encuentre localizado) del directorio
que apunte a carpeta Imagenes dentro del proyecto.

Segunda cosa importante: en el pgadmin (postgresql) hay que crear la base Marketplace. Para
que los microservicios puedean obtener la base. (Ya que los microservicios apuntan a una base de datos
llamada Marketplace). Otra cosa, hay que
ejecutar los scripts, que ya vienen en la carpeta 'Scripts postgresql'.
Ya que vienen definidos los procedimientos almacenados que llaman los 3 microservicios
que ejecutan/automatizan los procesos. Sino repository no servirá en Spring Boot.

Tercera cosa importante: 
Cambiar las credenciales en application.properties de los microservicios:
spring.datasource.url=jdbc:postgresql://localhost:5432/Marketplace
spring.datasource.username='tuUsuario'
spring.datasource.password='tuClave'

Cuarta cosa importante, para el proyecto Marketplace (Angular), solamente
hay que utilizar 'npm install' para descargar las dependencias que necesita el proyecto.
Después de instalar todo en Marketplace, ejecutar con 'ng serve'.
No olvides también levantar los microservicios Usuarios, Articulos y Ventas.