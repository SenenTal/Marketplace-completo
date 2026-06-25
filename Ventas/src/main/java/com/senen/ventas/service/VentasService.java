/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.senen.ventas.service;

import com.senen.ventas.DTO.CrearVentaDTO;
import com.senen.ventas.DTO.TotalDTO;
import com.senen.ventas.DTO.VentasUsuarioDTO;
import com.senen.ventas.entities.Ventas;
import java.util.List;

/**
 *
 * @author senen
 */
public interface VentasService {
    
    List<VentasUsuarioDTO> obtenerVentasPorUsuario(Long id);
    
    CrearVentaDTO crearVenta(Long id);
    
    TotalDTO obtenerGanancias(Long id);
    
    List<Ventas> listarVentas();
    
}
