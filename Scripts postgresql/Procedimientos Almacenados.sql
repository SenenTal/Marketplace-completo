--Procedimiento Almacenados para Articulos:
--Función para listar articulos: fn_llamar_articulos()
CREATE OR REPLACE FUNCTION fn_llamar_articulos()
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, descripcion VARCHAR, precio REAL, categoria VARCHAR, 
estado_articulo BOOLEAN, ubicacion VARCHAR, fecha_publicacion TIMESTAMP, imagen VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN 
	RETURN QUERY
	SELECT a.id, a.titulo, a.descripcion, a.precio, a.categoria, a.estado_articulo,
	a.ubicacion, a.fecha_publicacion, a.imagen FROM articulos a ORDER BY a.id ASC;
END;
$$;

--Función para crear un articulo, necesita el id de un usuario: fn_crear_articulo()
CREATE OR REPLACE FUNCTION fn_crear_articulo(p_titulo VARCHAR, p_descripcion VARCHAR, p_precio REAL, 
p_categoria VARCHAR, p_ubicacion VARCHAR, p_imagen VARCHAR, p_id_usuario BIGINT)
RETURNS TABLE(id BIGINT, titulo VARCHAR, descripcion VARCHAR, precio REAL, categoria VARCHAR, estado_articulo BOOLEAN,
ubicacion VARCHAR, fecha_publicacion TIMESTAMP, imagen VARCHAR, id_usuario BIGINT)
LANGUAGE 'plpgsql'
AS
$$
BEGIN 
	/*Confirmar si existe o no articulo con el mismo titulo*/
	IF EXISTS(SELECT 1 FROM articulos a WHERE a.titulo = p_titulo)
	THEN RAISE EXCEPTION 'Ya existe articulo con ese titulo' USING ERRCODE = 'P0001';
	END IF;

	/*Verificar la existencia del id de usuario*/
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_id_usuario)
	THEN RAISE EXCEPTION 'No existe el usuario' USING ERRCODE = 'P0001';
	END IF;

	/*Insertar datos en la tabla articulos*/
	INSERT INTO articulos(titulo, descripcion, precio, categoria, estado_articulo, ubicacion, 
	fecha_publicacion, imagen, id_usuario)
	VALUES(p_titulo, p_descripcion, p_precio, p_categoria, TRUE, p_ubicacion, 
	CURRENT_TIMESTAMP, p_imagen, p_id_usuario);
	
	RETURN QUERY
	SELECT a.id, a.titulo, a.descripcion, a.precio, a.categoria, a.estado_articulo,
	a.ubicacion, a.fecha_publicacion, a.imagen, a.id_usuario FROM articulos a WHERE a.titulo = p_titulo;
END;
$$;

--Crear función para listar los articulos del usuario
CREATE OR REPLACE FUNCTION fn_listar_articulos_usuario(p_id BIGINT)
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, descripcion VARCHAR, precio REAL, categoria VARCHAR, 
estado_articulo boolean, ubicacion VARCHAR, fecha_publicacion TIMESTAMP, imagen VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	/*Comprobar la existencia del usuario*/
	IF NOT EXISTS (SELECT 1 FROM usuarios u WHERE u.id = p_id)
	THEN RAISE EXCEPTION 'No existe el usuario' USING ERRCODE = 'P0001';
	END IF;
	/*Seleccionar los articulos del usuario*/
	RETURN QUERY
	SELECT a.id, a.titulo, a.descripcion, a.precio, a.categoria, a.estado_articulo, a.ubicacion,
	a.fecha_publicacion, a.imagen
	FROM articulos a WHERE a.id_usuario = p_id;
END;
$$;

--Función para buscar articulos por categoria: fn_buscar_por_categoria()
CREATE OR REPLACE FUNCTION fn_buscar_por_categoria(p_categoria VARCHAR)
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, descripcion VARCHAR, precio REAL, categoria VARCHAR, 
estado_articulo boolean, ubicacion VARCHAR, fecha_publicacion TIMESTAMP, imagen VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN QUERY
	SELECT a.id, a.titulo, a.descripcion, a.precio, a.categoria, a.estado_articulo,
	a.ubicacion, a.fecha_publicacion, a.imagen FROM articulos a 
	WHERE a.categoria ILIKE '%' || p_categoria || '%';
END;
$$;

--Función para borrar un articulo
CREATE OR REPLACE PROCEDURE sp_delete_articulo_id(p_articulo_id BIGINT)
LANGUAGE 'plpgsql'
AS
$$
BEGIN 
	--Verificar si existe el articulo
	IF NOT EXISTS(SELECT 1 FROM articulos a WHERE a.id = p_articulo_id)
	THEN RAISE EXCEPTION 'No existe articulo' USING ERRCODE = 'P0001';
	END IF;
	--Si existe solamente lo elimina sin devolver nada
	IF EXISTS (SELECT 1 FROM articulos a WHERE a.id = p_articulo_id)
	THEN DELETE FROM articulos a WHERE a.id = p_articulo_id;
	END IF;
END;
$$;

--Crear Función: fn_buscar_nombre_articulo()
CREATE OR REPLACE FUNCTION fn_buscar_nombre_articulo(p_titulo VARCHAR)
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, descripcion VARCHAR, precio REAL, categoria VARCHAR, 
estado_articulo BOOLEAN, ubicacion VARCHAR, fecha_publicacion TIMESTAMP, imagen VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN 
	RETURN QUERY
	SELECT a.id, a.titulo, a.descripcion, a.precio, a.categoria, a.estado_articulo,
	a.ubicacion, a.fecha_publicacion, a.imagen FROM articulos a 
	WHERE a.titulo ILIKE '%' || p_titulo || '%';
END;
$$;

--Función para llamar al articulo por su id de articulo
CREATE OR REPLACE FUNCTION fn_obtener_articulo_por_id(p_id BIGINT)
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, descripcion VARCHAR, precio REAL, categoria VARCHAR, estado_articulo BOOLEAN,
ubicacion VARCHAR, fecha_publicacion TIMESTAMP, imagen VARCHAR, id_usuario BIGINT)
LANGUAGE 'plpgsql'
AS
$$
BEGIN 
	--Comprobar su existencia
	IF NOT EXISTS(SELECT 1 FROM articulos a WHERE a.id = p_id)
	THEN RAISE EXCEPTION 'Articulo no existe' USING ERRCODE = 'P0001';
	END IF;
	RETURN QUERY
	SELECT a.id, a.titulo, a.descripcion, a.precio, a.categoria, a.estado_articulo,
	a.ubicacion, a.fecha_publicacion, a.imagen, a.id_usuario FROM articulos a WHERE a.id = p_id;
END;
$$;

--Función para modificar/actualizar un articulo, sin modificar la imagen
CREATE OR REPLACE FUNCTION fn_modificar_articulo_1(p_articulo_id BIGINT, p_usuario_id BIGINT, p_titulo VARCHAR, 
p_descripcion VARCHAR, p_precio REAL, p_categoria VARCHAR, p_estado_articulo BOOLEAN, 
p_ubicacion VARCHAR, p_fecha_publicacion TIMESTAMP)
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, descripcion VARCHAR, precio REAL, categoria VARCHAR, estado_articulo BOOLEAN,
ubicacion VARCHAR, fecha_publicacion TIMESTAMP, imagen VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	--Primero comprobar si existe el articulo
	IF NOT EXISTS(SELECT 1 FROM articulos a WHERE a.id =p_articulo_id)
	THEN RAISE EXCEPTION 'Articulo no existe' USING ERRCODE = 'P0001';
	END IF;
	--Segundo comprobar su disponibilidad/ si este articulo esta vendido
	IF EXISTS(SELECT 1 FROM ventas v WHERE v.articulo_id = p_articulo_id)
	THEN RAISE EXCEPTION 'Este articulo ya esta vendido' USING ERRCODE = 'P0001';
	END IF;
	--Tercero, comprobar el usuario
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_usuario_id)
	THEN RAISE EXCEPTION 'No existe el usuario' USING ERRCODE = 'P0001';
	END IF;
	--Cuarto, si pasa las comprobaciones se puede modificar
	UPDATE articulos a SET titulo = p_titulo, descripcion = p_descripcion, precio = p_precio,
	categoria = p_categoria, estado_articulo = p_estado_articulo, ubicacion = p_ubicacion, 
	fecha_publicacion = CURRENT_TIMESTAMP WHERE a.id = p_articulo_id;
	RETURN QUERY
	SELECT a.id, a.titulo, a.descripcion, a.precio, a.categoria, a.estado_articulo,
	a.ubicacion, a.fecha_publicacion, a.imagen FROM articulos a WHERE a.id = p_articulo_id;
END;
$$;

--Función para modificar/actualizar un articulo, que si modifica la imagen
CREATE OR REPLACE FUNCTION fn_modificar_articulo_2(p_articulo_id BIGINT, p_usuario_id BIGINT, p_titulo VARCHAR, 
p_descripcion VARCHAR, p_precio REAL, p_categoria VARCHAR, p_estado_articulo BOOLEAN, 
p_ubicacion VARCHAR, p_fecha_publicacion TIMESTAMP, p_imagen VARCHAR)
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, descripcion VARCHAR, precio REAL, categoria VARCHAR, estado_articulo BOOLEAN,
ubicacion VARCHAR, fecha_publicacion TIMESTAMP, imagen VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	--Primero comprobar si existe el articulo
	IF NOT EXISTS(SELECT 1 FROM articulos a WHERE a.id =p_articulo_id)
	THEN RAISE EXCEPTION 'Articulo no existe' USING ERRCODE = 'P0001';
	END IF;
	--Segundo comprobar su disponibilidad/ si este articulo esta vendido
	IF EXISTS(SELECT 1 FROM ventas v WHERE v.articulo_id = p_articulo_id)
	THEN RAISE EXCEPTION 'Este articulo ya esta vendido' USING ERRCODE = 'P0001';
	END IF;
	--Tercero, comprobar el usuario
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_usuario_id)
	THEN RAISE EXCEPTION 'No existe el usuario' USING ERRCODE = 'P0001';
	END IF;
	--Cuarto, si pasa las comprobaciones se puede modificar
	UPDATE articulos a SET titulo = p_titulo, descripcion = p_descripcion, precio = p_precio,
	categoria = p_categoria, estado_articulo = p_estado_articulo, ubicacion = p_ubicacion, 
	fecha_publicacion = CURRENT_TIMESTAMP, imagen = p_imagen WHERE a.id = p_articulo_id;
	RETURN QUERY
	SELECT a.id, a.titulo, a.descripcion, a.precio, a.categoria, a.estado_articulo,
	a.ubicacion, a.fecha_publicacion, a.imagen FROM articulos a WHERE a.id = p_articulo_id;
END;
$$;

--Función para listar articulos de cada usuario
CREATE OR REPLACE FUNCTION fn_listar_articulos_usuarios()
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, estado_articulo BOOLEAN, id_usuario BIGINT, username VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN QUERY
	SELECT a.id, a.titulo, a.estado_articulo, u.id, u.usuario
	FROM articulos a JOIN usuarios u ON a.id_usuario = u.id
	ORDER BY a.id ASC;
END;
$$;

--Creación de función para listar los articulos vendidos del usuario
CREATE OR REPLACE FUNCTION fn_listar_articulos_vendidos()
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, estado_articulo BOOLEAN, id_usuario BIGINT, username VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN QUERY
	SELECT a.id, a.titulo, a.estado_articulo, u.id, u.usuario
	FROM articulos a JOIN usuarios u ON a.id_usuario = u.id
	WHERE a.estado_articulo = FALSE;
END;
$$;

CREATE OR REPLACE FUNCTION fn_listar_articulos_vendidos_id(p_id BIGINT)
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, estado_articulo BOOLEAN, id_usuario BIGINT, username VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	--Verificar la existencia de usuario
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_id)
	THEN RAISE EXCEPTION 'No existe el usuario' USING ERRCODE = 'P0001';
	END IF;
	IF EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_id)
	THEN
	RETURN QUERY
	SELECT a.id, a.titulo, a.estado_articulo, u.id, u.usuario
	FROM articulos a JOIN usuarios u ON a.id_usuario = u.id
	JOIN ventas v ON v.comprador_id = u.id
	WHERE a.estado_articulo = FALSE AND u.id = p_id;
	END IF;
END;
$$;


--Funciones Almacenadas para Usuarios:
--Procedimiento Almacenado para crear un usuario, en role agrega por defecto 'user'
CREATE OR REPLACE FUNCTION fn_crear_usuario(p_user VARCHAR, p_nickname VARCHAR, p_password VARCHAR)
RETURNS TABLE(id BIGINT, usuario VARCHAR, nickname VARCHAR, contrasena VARCHAR, role VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	/*Comprobar si existe un usuario con el mismo nombre*/
	IF EXISTS(SELECT 1 FROM usuarios u WHERE u.usuario = p_user)
	THEN RAISE EXCEPTION 'Ya existe usuario con ese nombre' USING ERRCODE = 'P0001';
	END IF;
	/*Insertar usuario*/
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.usuario = p_user)
	THEN
	INSERT INTO usuarios(usuario, nickname, contrasena, role) VALUES(p_user, p_nickname, p_password, 'user');
	RETURN QUERY 
	SELECT u.id, u.usuario, u.nickname, u.contrasena, u.role FROM usuarios u WHERE u.usuario = p_user;
	END IF;
END;
$$;

--Función para hacer admin a un usuario
CREATE OR REPLACE FUNCTION fn_hacer_admin(p_user VARCHAR)
RETURNS TABLE(id BIGINT, usuario VARCHAR, role VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
DECLARE
p_id BIGINT;
p_role VARCHAR;
BEGIN
	/*Verificar la existencia del usuario*/
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.usuario = p_user)
	THEN RAISE EXCEPTION 'Usuario no existente' USING ERRCODE = 'P0001';
	END IF;
	/*Verifica su rol asignado*/
	SELECT u.role, u.id INTO p_role, p_id FROM usuarios u WHERE u.usuario = p_user;
	IF p_role = 'user' THEN
	UPDATE usuarios u SET role = 'admin' WHERE u.usuario = p_user;
	END IF;
	IF p_role NOT IN ('user','admin')
	THEN RAISE EXCEPTION 'No tienes roles validos!' USING ERRCODE = 'P0001';
	END IF;
	RETURN QUERY
	SELECT u.id, u.usuario, u.role FROM usuarios u WHERE u.usuario = p_user;
END;
$$;

--Crear función almacenada fn_llamar_usuarios()
CREATE OR REPLACE FUNCTION fn_llamar_usuarios()
RETURNS TABLE(id BIGINT, usuario VARCHAR, nickname VARCHAR, contrasena VARCHAR, role VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN QUERY
	SELECT u.id, u.usuario, u.nickname, u.contrasena, u.role FROM usuarios u ORDER BY u.id ASC;
END;
$$;

--Procedimiento almacenadoa para Borrar usuario: sp_borrar_usuario()
CREATE OR REPLACE PROCEDURE sp_borrar_usuario(p_id BIGINT)
LANGUAGE 'plpgsql'
AS
$$
BEGIN 
	--Verificar existencia
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_id)
	THEN RAISE EXCEPTION 'Usuario no existe' USING ERRCODE = 'P0001';
	END IF;
	--Si existe solamente lo elimina sin devolver nada
	IF EXISTS (SELECT 1 FROM usuarios u WHERE u.id = p_id)
	THEN DELETE FROM usuarios u WHERE u.id = p_id;
	END IF;
END;
$$;

--Crear función alamcenada: fn_obtener_usuario_por_id()
CREATE OR REPLACE FUNCTION fn_obtener_usuario_por_id(p_id BIGINT)
RETURNS TABLE(id BIGINT, usuario VARCHAR, nickname VARCHAR, contrasena VARCHAR, role VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	--Verificar su existencia por id
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_id)
	THEN RAISE EXCEPTION 'No existe el usuario' USING ERRCODE = 'P0001';
	END IF;
	RETURN QUERY
	SELECT u.id, u.usuario, u.nickname, u.contrasena, u.role FROM usuarios u 
	WHERE u.id = p_id;
END;
$$;

--Crear función para borrar usuario por id.
--Nota: Si se borra usuario, se borran sus relaciones de articulos
--Y a su vez se borran los registros de ventas relacionados a id de articulo
CREATE OR REPLACE PROCEDURE sp_delete_usuario(p_usuario_id INTEGER)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_usuario_id)
	THEN RAISE EXCEPTION 'No existe el usuario' USING ERRCODE = 'P0001';
	END IF;
	IF EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_usuario_id)
	THEN DELETE FROM usuarios u WHERE u.id = p_usuario_id;
	END IF;
END;
$$;
--Función para iniciar sesión. fn_iniciar_sesion()
CREATE OR REPLACE FUNCTION fn_iniciar_sesion(p_user VARCHAR, p_password VARCHAR)
RETURNS TABLE(id BIGINT, usuario VARCHAR, nickname VARCHAR, contrasena VARCHAR, role VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
DECLARE
p_password_2 VARCHAR;
BEGIN
	--Primero verificar existencia
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.usuario = p_user)
	THEN RAISE EXCEPTION 'Este usuario no existe' USING ERRCODE = 'P0001';
	END IF;
	--Segundo verificar si la contraseña es correcta
	SELECT u.contrasena INTO p_password_2 FROM usuarios u WHERE u.usuario = p_user;
	IF p_password_2 != p_password THEN
	--Tercer regresar el resultado
		RAISE EXCEPTION 'Contraseña invalida' USING ERRCODE = 'P0001';
	ELSE
		RETURN QUERY
		SELECT u.id, u.usuario, u.nickname, u.contrasena, u.role FROM
		usuarios u WHERE u.usuario = p_user;
	END IF;
END;
$$;

--Función para actualizar datos del usuario
CREATE OR REPLACE FUNCTION fn_update_usuario(p_id BIGINT, p_user VARCHAR, p_password VARCHAR, 
p_nickname VARCHAR)
RETURNS TABLE(id BIGINT, usuario VARCHAR, nickname VARCHAR, contrasena VARCHAR, role VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
DECLARE
BEGIN 
	--Realizar los cambios y devolver una tabla
	UPDATE usuarios u SET usuario = p_user, nickname = p_nickname, contrasena = p_password
	WHERE u.id = p_id;
	RETURN QUERY
	SELECT u.id, u.usuario, u.nickname, u.contrasena, u.role FROM usuarios u WHERE u.id = p_id;
END;
$$;


--Función para hacer usuario el rol de admin a rol de user
CREATE OR REPLACE FUNCTION fn_hacer_user(p_user VARCHAR)
RETURNS TABLE(id BIGINT, usuario VARCHAR, role VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
DECLARE
p_id BIGINT;
p_role VARCHAR;
BEGIN
	/*Verificar la existencia del usuario*/
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.usuario = p_user)
	THEN RAISE EXCEPTION 'Usuario no existente' USING ERRCODE = 'P0001';
	END IF;
	/*Verifica su rol asignado*/
	SELECT u.role, u.id INTO p_role, p_id FROM usuarios u WHERE u.usuario = p_user;
	IF p_role = 'admin' THEN
	UPDATE usuarios u SET role = 'user' WHERE u.usuario = p_user;
	END IF;
	IF p_role NOT IN ('user','admin')
	THEN RAISE EXCEPTION 'No tienes roles validos!' USING ERRCODE = 'P0001';
	END IF;
	RETURN QUERY
	SELECT u.id, u.usuario, u.role FROM usuarios u WHERE u.usuario = p_user;
END;
$$;

--Función para encontrar usuario por nombre de usuario:
CREATE OR REPLACE FUNCTION fn_buscar_por_usuario(p_usuario VARCHAR)
RETURNS TABLE(id BIGINT, usuario VARCHAR, nickname VARCHAR, contrasena VARCHAR, role VARCHAR)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.usuario = p_usuario)
	THEN RAISE EXCEPTION 'No existe usuario' USING ERRCODE = 'P0001';
	END IF;
	IF EXISTS(SELECT 1 FROM usuarios u WHERE u.usuario = p_usuario)
	THEN RETURN QUERY
	SELECT u.id, u.usuario, u.nickname, u.contrasena, u.role FROM 
	usuarios u WHERE u.usuario = p_usuario;
	END IF;
END;
$$;

--Crear función para validar credenciales (utilizado en actualizar-user), utilizando id, 
--username, password. Sirve para el formulario de validación antes de modificar las credenciales:
CREATE OR REPLACE FUNCTION fn_validar_credenciales(p_id BIGINT, p_user VARCHAR, p_password VARCHAR)
RETURNS TABLE(acceso BOOLEAN)
LANGUAGE 'plpgsql'
AS
$$
DECLARE
p_password_2 VARCHAR;
p_user_2 VARCHAR;
BEGIN
	--Primero verificar existencia
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_id)
	THEN RAISE EXCEPTION 'Este usuario no existe' USING ERRCODE = 'P0001';
	END IF;
	--Segundo verificar si la contraseña y el usuario son correctos
	SELECT u.contrasena, u.usuario INTO p_password_2, p_user_2 FROM usuarios u 
	WHERE u.usuario = p_user AND u.id = p_id;
	IF p_password_2 = p_password AND p_user_2 = p_user THEN
	--Tercer regresar el resultado
		RETURN QUERY SELECT TRUE;
	ELSE 
		RETURN QUERY SELECT FALSE;
	END IF;
END;
$$;


--Funciones Almacenadas para Ventas:
--Función almacenada para crear una Venta
CREATE OR REPLACE FUNCTION fn_crear_venta(p_id_articulo BIGINT)
RETURNS TABLE(id_articulo BIGINT, titulo VARCHAR, cantidad REAL, fecha_venta TIMESTAMP)
LANGUAGE 'plpgsql'
AS
$$
DECLARE 
 status BOOLEAN;
 p_precio REAL;
BEGIN
	/*Verificar la existencia del articulo*/
	IF NOT EXISTS(SELECT 1 FROM articulos a WHERE a.id = p_id_articulo)
	THEN RAISE EXCEPTION 'Este articulo no existe' USING ERRCODE = 'P0001';
	END IF;
	/*Verificar la disponibilidad*/
	SELECT a.estado_articulo into status FROM articulos a WHERE a.id = p_id_articulo;
	IF status = FALSE
	THEN RAISE EXCEPTION 'Articulo no disponible para venta' USING ERRCODE = 'P0001';
	END IF;
	/*Realizar la venta*/
	--Seleccionar el precio e insertarlo en cantidad de Ventas
	SELECT a.precio INTO p_precio FROM articulos a WHERE a.id = p_id_articulo;
	INSERT INTO ventas(cantidad, articulo_id, fecha_venta) 
	VALUES(p_precio, p_id_articulo, CURRENT_TIMESTAMP);

	--Modifcar el articulo ya vendido en su disponibilidad en estado
	UPDATE articulos a SET estado_articulo = FALSE WHERE a.id = p_id_articulo;

	--Regresar los datos de tabla
	RETURN QUERY
	SELECT a.id, a.titulo, v.cantidad, v.fecha_venta FROM articulos a 
	JOIN ventas v ON v.articulo_id = a.id
	WHERE a.id = p_id_articulo;
END;
$$;

--Crear función fn_obtener_ganancias_usuario(). Función que obtiene todas las cantidades de sus articulos vendidos
CREATE OR REPLACE FUNCTION fn_obtener_ganancias_usuario(p_id_usuario BIGINT)
RETURNS TABLE(nombre_usuario VARCHAR, total REAL)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	/*Primero verificar existencia del usuario por medio del id*/
	IF NOT EXISTS(SELECT 1 FROM usuarios u WHERE u.id = p_id_usuario)
	THEN RAISE EXCEPTION 'Usuario no existe' USING ERRCODE = 'P0001';
	END IF;
	
	RETURN QUERY
	SELECT u.nickname, COALESCE(SUM(v.cantidad), 0) AS total
	FROM usuarios u JOIN articulos a ON a.id_usuario = u.id
	JOIN ventas v ON v.articulo_id = a.id
	WHERE u.id = p_id_usuario GROUP BY u.nickname;
END;
$$;

--Función para seleccionar todas las ventas
CREATE OR REPLACE FUNCTION fn_obtener_ventas()
RETURNS TABLE(id BIGINT, cantidad REAL, articulo_id BIGINT, fecha_venta TIMESTAMP)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN QUERY 
	SELECT v.id, v.cantidad, v.articulo_id, v.fecha_venta FROM ventas v ORDER BY v.id ASC;
END;
$$;

--Función para obtener las ventas del usuario según el id de usuario
CREATE OR REPLACE FUNCTION fn_obtener_ventas_usuario(p_id_usuario BIGINT)
RETURNS TABLE(id_venta BIGINT, titulo VARCHAR, cantidad REAL, fecha_venta TIMESTAMP)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN QUERY
	SELECT v.id, a.titulo, v.cantidad, v.fecha_venta FROM ventas v
	JOIN articulos a ON a.id = v.articulo_id
	JOIN usuarios u ON u.id = a.id_usuario
	WHERE u.id = p_id_usuario;
END;
$$;

--Verificar por id de articulo si existe en tabla venta, una cosa es estadoArticulo en false
--Y otra cosa es que se encuentra en venta (Si alguien compró el articulo viene a venta)
CREATE OR REPLACE FUNCTION fn_verificar_venta(p_id BIGINT)
RETURNS TABLE(id BIGINT, cantidad REAL, articulo_id BIGINT, fecha_venta TIMESTAMP)
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	--Mandas el id de articulo para verificar si se vendió el articulo
	IF NOT EXISTS(SELECT 1 FROM ventas v WHERE v.articulo_id = p_id)
	THEN RAISE EXCEPTION 'No se ha vendido este articulo: %', p_id 
	USING ERRCODE = 'P0001';
	END IF;
	IF EXISTS(SELECT 1 FROM ventas v WHERE v.articulo_id = p_id)
	THEN 
	RETURN QUERY
	SELECT v.id, v.cantidad, v.articulo_id, v.fecha_venta
	FROM ventas v WHERE v.articulo_id = p_id;
	END IF;
END;
$$;