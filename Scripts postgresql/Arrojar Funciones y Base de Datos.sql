--Arrojar Funciones (Todas):
--Ventas:
DROP FUNCTION fn_crear_venta(BIGINT);
DROP FUNCTION fn_obtener_ventas();
DROP FUNCTION fn_obtener_ganancias_usuario(BIGINT);
DROP FUNCTION fn_obtener_ventas_usuario(BIGINT);
DROP FUNCTION fn_verificar_venta(BIGINT);

--Usuarios:
DROP FUNCTION fn_hacer_admin(VARCHAR);
DROP PROCEDURE sp_borrar_usuario(BIGINT);
DROP FUNCTION fn_obtener_usuario_por_id(BIGINT);
DROP FUNCTION fn_llamar_usuarios();
DROP FUNCTION fn_crear_usuario(VARCHAR, VARCHAR, VARCHAR);
DROP PROCEDURE sp_delete_usuario(INTEGER);
DROP FUNCTION fn_iniciar_sesion(VARCHAR, VARCHAR);
DROP FUNCTION fn_update_usuario(BIGINT, VARCHAR, VARCHAR, VARCHAR);
DROP FUNCTION fn_hacer_user(VARCHAR);
DROP FUNCTION fn_validar_credenciales(BIGINT, VARCHAR, VARCHAR);

--Articulos:
DROP FUNCTION fn_crear_articulo(VARCHAR, VARCHAR, REAL, VARCHAR, VARCHAR, VARCHAR, BIGINT);
DROP FUNCTION fn_listar_articulos_usuario(BIGINT);
DROP FUNCTION fn_llamar_articulos();
DROP FUNCTION fn_buscar_por_categoria(VARCHAR);
DROP PROCEDURE sp_delete_articulo_id(BIGINT);
DROP FUNCTION fn_buscar_nombre_articulo(VARCHAR);
DROP FUNCTION fn_obtener_articulo_por_id(BIGINT);
DROP FUNCTION fn_modificar_articulo_1(BIGINT, BIGINT, VARCHAR, VARCHAR, REAL, VARCHAR, BOOLEAN,
VARCHAR, TIMESTAMP);
DROP FUNCTION fn_modificar_articulo_2(BIGINT, BIGINT, VARCHAR, VARCHAR, REAL, VARCHAR, BOOLEAN,
VARCHAR, TIMESTAMP, VARCHAR);
DROP FUNCTION fn_listar_articulos_usuarios();
DROP FUNCTION fn_listar_articulos_vendidos();
DROP FUNCTION fn_listar_articulos_vendidos_id(BIGINT);

--Arrojar las 3 bases de datos:
DROP TABLE articulos;
DROP TABLE usuarios;
DROP TABLE ventas;