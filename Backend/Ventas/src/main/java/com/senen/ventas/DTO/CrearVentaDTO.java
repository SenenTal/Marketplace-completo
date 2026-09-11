/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.ventas.DTO;

import java.time.LocalDateTime;

/**
 *
 * @author senen
 */
public interface CrearVentaDTO {
    Long getIdArticulo();
    
    String getTitulo();
    
    float getCantidad();
    
    LocalDateTime getFechaVenta();
}
