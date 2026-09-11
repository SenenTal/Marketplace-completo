--Agregar columna dinero_electronico en tabla usuarios
ALTER TABLE usuarios ADD COLUMN dinero_electronico REAL;

--Agregar columna ubicacion en tabla usuarios
ALTER TABLE usuarios ADD COLUMN ubicacion VARCHAR;

--SELECCIONAR TODOS LOS USUARIOS
SELECT * FROM usuarios u ORDER BY u.id ASC;
--Seleccionar todos los articulos
SELECT * FROM articulos a ORDER BY a.id ASC;
--Actualizar a 0 dinero_electronico en todos los usuarios.
UPDATE usuarios u SET dinero_electronico = 0 WHERE u.role = 'user';

--Seleccionar nombre, id de usuario y nombre, ubicacion de articulos
SELECT u.id, u.usuario, u.ubicacion, a.id, a.titulo, a.ubicacion FROM articulos a 
LEFT JOIN usuarios u ON u.id = a.id_usuario
ORDER BY a.id ASC;

--Agregar ciudad en la ubicacion del usuario
UPDATE usuarios u SET ubicacion = 'Guamuchil, Sinaloa'::VARCHAR WHERE u.id = 1;
UPDATE usuarios u SET ubicacion = 'Mazatlan, Sinaloa'::VARCHAR WHERE u.id = 2;
UPDATE usuarios u SET ubicacion = 'Culiacan, Sinaloa'::VARCHAR WHERE u.id = 3;
UPDATE usuarios u SET ubicacion = 'Guasave, Sinaloa'::VARCHAR WHERE u.id = 4;

--Probar fn_llamar_usuarios()
SELECT * FROM fn_llamar_usuarios();
--Probar fn_crear_usuario()
SELECT * FROM fn_crear_usuario('Javier', 'El Tostada', 'fuego');
SELECT * FROM fn_crear_usuario('Tulio', 'Dark Cloud', '123456');
--Probar fn_obtener_usuario_por_id()
SELECT * FROM fn_obtener_usuario_por_id(10);
--Probar fn_iniciar_sesion()
SELECT * FROM fn_iniciar_sesion('Senen','Mudkip');
--Probar fn_update_usuario()
SELECT * FROM fn_update_usuario('1'::BIGINT,'Senen'::VARCHAR,'Mudkip'::VARCHAR,'SenenTal'::VARCHAR);

--Probar fn_obtener_ganancias_usuario()
SELECT * FROM fn_obtener_ganancias_usuario(1);

--Probar fn_obtener_ventas()
SELECT * FROM fn_obtener_ventas();

--Probar a fn_listar_articulos()
SELECT * FROM fn_llamar_articulos();

--Probar a fn_listar_articulos_vendidos_id()
SELECT * FROM fn_listar_articulos_vendidos_id(1);

--Probar a fn_compras_usuario()
SELECT * FROM fn_compras_usuario(1);

--Probar a fn_asignar_ubicacion()
SELECT * FROM fn_asignar_ubicacion(4);
SELECT * FROM articulos a WHERE a.id_usuario = 4;

--Probar a fn_update_usuario()
SELECT * FROM fn_update_usuario(1,'Senen','Mudkip','Lord Senen','Guamuchil, Sinaloa');

--Probar a fn_crear_usuario()
SELECT * FROM fn_crear_usuario('Waldo'::VARCHAR,'Rockberto'::VARCHAR,'Gatitos'::VARCHAR,'Culiacan, Sinaloa'::VARCHAR);

--Probar a fn_obtener_usuario_por_id()
SELECT * FROM fn_obtener_usuario_por_id(1);

--Probar a fn_modificar_articulo_1()
SELECT * FROM fn_modificar_articulo_1('2'::BIGINT, '1'::BIGINT, 'Pley 5 Kontrol spaiderman'::VARCHAR, 
'descripcion'::VARCHAR, '1000'::REAL, 'Consolas y Videojuegos'::VARCHAR, true::BOOLEAN, 'Mazatlan, Sinaloa'::VARCHAR,
CURRENT_TIMESTAMP::TIMESTAMP);
SELECT * FROM fn_modificar_articulo_1('2'::BIGINT, '1'::BIGINT, 'Pley 5 Kontrol spaiderman'::VARCHAR,
'descripcion'::VARCHAR, '1000'::REAL, 'Consolas y Videojuegos'::VARCHAR, true::BOOLEAN, 'Mazatlan, Sinaloa'::VARCHAR,
CURRENT_TIMESTAMP::TIMESTAMP);

--Probar a fn_modificar_articulo_2()
SELECT * FROM fn_modificar_articulo_2('2'::BIGINT, '1'::BIGINT, 'Pley 5 Kontrol spaiderman'::VARCHAR,
'descripcion'::VARCHAR, '1000'::REAL, 'Consolas y Videojuegos'::VARCHAR, true::BOOLEAN, 'Mazatlan, Sinaloa'::VARCHAR,
CURRENT_TIMESTAMP::TIMESTAMP, '33188751-23c9-49f7-b033-ed4daead359d_spiderman mando ps5.png'::VARCHAR);

--Probar a fn_obtener_nombre_imagen()
SELECT * FROM fn_obtener_nombre_imagen(2);

--Probar a fn_listar_articulos_usuario()
SELECT * FROM fn_listar_articulos_usuario(1);

--Probar a fn_recargar_dinero()
SELECT * FROM fn_recargar_dinero(1::BIGINT, 1000::REAL);