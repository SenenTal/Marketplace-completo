--Crear Usuarios
CREATE TABLE usuarios(
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	usuario VARCHAR(255),
	nickname VARCHAR(255),
	contrasena VARCHAR(255),
	role VARCHAR(255) NOT NULL,
	dinero_electronico REAL,
	ubicacion VARCHAR(255)
);
--Crear Articulos
CREATE TABLE articulos(
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	titulo VARCHAR(255) NOT NULL,
	descripcion VARCHAR(255) NOT NULL,
	precio REAL NOT NULL,
	categoria VARCHAR(255) NOT NULL,
	estado_articulo BOOLEAN NOT NULL,
	ubicacion VARCHAR(255) NOT NULL,
	fecha_publicacion TIMESTAMP,
	imagen VARCHAR(255),
	id_usuario BIGINT NOT NULL,
	CONSTRAINT fk_articulo_usuario
    FOREIGN KEY(id_usuario)
    REFERENCES usuarios(id)
    ON DELETE CASCADE
);
--Crear Ventas
CREATE TABLE ventas(
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	cantidad REAL NOT NULL,
	articulo_id BIGINT NOT NULL,
	fecha_venta TIMESTAMP,
	comprador_id BIGINT,
	CONSTRAINT fk_venta_articulo
    FOREIGN KEY(articulo_id)
    REFERENCES articulos(id),
	CONSTRAINT fk_venta_usuario
	FOREIGN KEY(comprador_id)
	REFERENCES usuarios(id)
);

--Mostar estructura de articulos
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_schema = 'public'
AND table_name = 'articulos';

--Mostar estructura de ventas
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_schema = 'public'
AND table_name = 'ventas';

--Mostar estructura de usuarios
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_schema = 'public'
AND table_name = 'usuarios';