package com.senen.articulos.DTO;

import java.time.LocalDateTime;

public interface ComprasDTO {
    Long getId();
    String getTitulo();
    Float getCosto();
    LocalDateTime getFechaVenta();
}