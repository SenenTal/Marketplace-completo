package com.senen.articulos.DTO;

import java.time.LocalDateTime;

public interface ComprasDTO {
    public String getTitulo();
    public String getDescripcion();
    public Long getPrecio();
    public LocalDateTime getFecha();
    public String getImagen();
}
