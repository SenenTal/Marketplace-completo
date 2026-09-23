/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.senen.articulos.DTO;

import java.time.LocalDateTime;

/**
 *
 * @author senen
 */
public interface ArticulosUsuariosDTO {
    
    Long getIdArticulo();
    
    String getTitulo();
    
    Float getPrecio();
    
    LocalDateTime getFechaVenta();
    
    String getUsername();
    
}
