/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.senen.ventas.repository;

import com.senen.ventas.DTO.CrearVentaDTO;
import com.senen.ventas.DTO.TotalDTO;
import com.senen.ventas.DTO.VentasUsuarioDTO;
import com.senen.ventas.entities.Ventas;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author senen
 */
@Repository
public interface VentasRepository extends JpaRepository<Ventas, Long> {
    
    @Query(value = "SELECT * FROM fn_crear_venta(:id_articulo)", nativeQuery = true)
    CrearVentaDTO crearVenta(@Param("id_articulo") Long id);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_obtener_ganancias_usuario(:id_usuario)", nativeQuery = true)
    TotalDTO obtenerGanancias(@Param("id_usuario") Long id);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_obtener_ventas()", nativeQuery = true)
    List<Ventas> obtenerTodasLasVentas();
    
    @Transactional
    @Query(value = "SELECT * FROM fn_obtener_ventas_usuario(:id_usuario)", nativeQuery= true)
    List<VentasUsuarioDTO> obtenerVentasUsuario(@Param("id_usuario") Long id);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_verificar_venta(:id)", nativeQuery=true)
    Ventas obtenerVentaPorArticuloId(@Param("id") Long id);
    
}
