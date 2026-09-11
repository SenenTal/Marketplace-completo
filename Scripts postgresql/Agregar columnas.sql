--Agregar columnas con una referencia 
ALTER TABLE ventas ADD COLUMN comprador_id BIGINT,
ADD CONSTRAINT fk_ventas_comprador FOREIGN KEY (comprador_id)
REFERENCES usuarios(id);

--Verifi