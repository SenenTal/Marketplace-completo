--Obtener los nombres de las tablas de una BD en postgresql
--Mostrar las tablas de la base de datos Marketplace
SELECT table_name FROM information_schema.tables 
WHERE table_schema = 'public' AND table_type = 'BASE TABLE';

--Mostrar todas las tablas de las bases de datos (public, pg_catalog y information_schema)
SELECT * FROM pg_catalog.pg_tables;

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

