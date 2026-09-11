--Llamar a todos los articulos
SELECT * FROM fn_llamar_articulos();
--Llamar articulos con los usuarios correspondientes que los publicaron
SELECT * FROM fn_listar_articulos_usuarios();
--Mostrar articulos vendidos
SELECT * FROM fn_listar_articulos_vendidos();

--Llamar usuarios
SELECT * FROM fn_llamar_usuarios();
--Mostrar las ventas
SELECT * FROM fn_obtener_ventas();