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

--Mostrar estructura de ventas
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_schema = 'public'
AND table_name = 'ventas';

--Mostrar estructura de usuarios
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_schema = 'public'
AND table_name = 'usuarios';

--Mostrar todos los procedimientos almacenados (Stored procedures):
SELECT nspname AS schema_name, proname AS procedure_name 
FROM pg_catalog.pg_namespace n
JOIN pg_catalog.pg_proc p ON p.pronamespace = n.oid 
WHERE p.prokind = 'procedure' AND n.nspname = 'public';
--Mostrar todas las funciones almacenadas (Function):
SELECT 
    n.nspname AS schema_name,
    p.proname AS function_name,
    pg_get_function_arguments(p.oid) AS arguments,
    pg_get_functiondef(p.oid) AS definition
FROM pg_proc p
JOIN pg_namespace n ON p.pronamespace = n.oid
WHERE n.nspname = 'public';
--Borrar Todas las funciones almacenadas:
DO $$ 
DECLARE 
    r RECORD;
BEGIN
    FOR r IN (
        SELECT n.nspname AS schemaname, p.proname AS funcname, pg_get_function_identity_arguments(p.oid) AS args
        FROM pg_proc p
        JOIN pg_namespace n ON n.oid = p.pronamespace
        WHERE n.nspname = 'public' -- Cambia 'public' por tu esquema si es necesario
    ) LOOP
        EXECUTE format('DROP FUNCTION IF EXISTS %I.%I(%s) CASCADE;', r.schemaname, r.funcname, r.args);
    END LOOP;
END $$;
--Borrar todas los procedimientos almacenados:
DO $$ 
DECLARE
    r RECORD;
BEGIN
    FOR r IN (
        SELECT p.proname, pg_get_function_identity_arguments(p.oid) AS args
        FROM pg_proc p
        JOIN pg_namespace n ON p.pronamespace = n.oid
        WHERE n.nspname = 'public' 
          AND p.prokind = 'p' -- 'p' indica procedimiento almacenado
    ) LOOP
        EXECUTE 'DROP PROCEDURE IF EXISTS public.' || quote_ident(r.proname) || '(' || r.args || ') CASCADE;';
    END LOOP;
END $$;

