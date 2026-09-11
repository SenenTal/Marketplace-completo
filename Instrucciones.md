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


--03 de septiembre de 2026
-El proyecto aún le falta mucho. Tiene varias carencias y funcionalidades que le faltan:
Lista de modificaciones:

-------------Modificaciones y seguimiento del proyecto-----------------
-Los filtros de busqueda tanto por nombre como por categoria. Deben coincidir, si es que estan los 2
espacios de busqueda seleccionados.
-Al proyecto se le debe agregar una pantalla para ubicaciones, y el registro de la base de datos debe ser 
modificada. Cambiar la ubicación/ciudad que aparezca y se pida ese registro al momento de registrar la cuenta 
del usuario en Marketplace. No en el registro del nuevo articulo en venta.
-Conectar a OAuth2 en este proyecto, para tener un microservicio que se conecte a keycloak. Por medio de peticiones.
Y tokens para llamar al servicio y verificar la sesión por medio de keycloak.
-Hacer modificaciones tanto de backend y frontend. Al crear un articulo, habrá que especificar cuantas unidades tienes para
esa unidad (cantidad de stock).
-También agregar tamaños/tallas, es decir, para ropa, calzado.
-Esconder/encriptar las contraseñas de los usuarios registrados. Y a su vez poder modificar las credenciales en la pantalla
de actualizar usuario.
-Tener una pantalla para poder subir/recargar dinero digital (para hacer las compras de usuario) para el usuario.
-Hacer columnas para tabla ventas.
-Crear una ventana para ver lista de articulos comprados (basado en listas de compras de mercado libre, amazon, temu, etc)
-Borrar de la carpeta 'Imagenes', la imagen pasada (en caso de cambiar la imagen al momento de actualizar
registro en articulos) al momento de sustituir por otra imagen.
-En la base de datos. Modificar el procedimiento almacenado que actualiza el usuario. Y que actualice la ubicación tanto del usuario
como de los articulos publicados por el usuario.
-Programar un modal en el componente options, para actualizar/recargar el dinero electronico del usuario. 

-------Cosas que le faltan para la prueba Docker-------
-Hacer pruebas con Docker-compose. Checar como hacer que obtenga (ya sea por un servicio api) la dirección de las imagenes
de los articulos (Articulos tiene WebConfig, una ruta configurada para solamente obtener la ruta de carpeta Imagenes. 
Y los nombres de las imagenes que se obtienen de la base solamente obtiene las fotos ya que en el frontend tiene:
'ruta del directorio' + 'nombre de la imagen')
-Una imagen / contenedor para el proyecto de Angular
-Una imagen para el nuevo servicio para peticiones keycloak
-