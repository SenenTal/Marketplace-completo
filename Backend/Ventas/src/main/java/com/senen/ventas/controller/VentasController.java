/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.ventas.controller;

import com.senen.ventas.DTO.CrearVentaDTO;
import com.senen.ventas.DTO.TotalDTO;
import com.senen.ventas.DTO.VentaDTO;
import com.senen.ventas.DTO.VentasUsuarioDTO;
import com.senen.ventas.entities.Ventas;
import com.senen.ventas.response.ApiResponse;
import com.senen.ventas.service.VentasService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author senen
 */
@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/ventas")
public class VentasController {
    
    @Autowired
    private VentasService service;
    
    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> listarVentas(){
        List<Ventas> ventas = service.listarVentas();
        if(ventas.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, "No hay registros de ventas", HttpStatus.NO_CONTENT));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, "Listado de ventas", HttpStatus.OK, ventas));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> obtenerVentasDelUsuario(@PathVariable("id")@Valid Long id){
        List<VentasUsuarioDTO> ventasUsuario = service.obtenerVentasPorUsuario(id);
        if(ventasUsuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, "No hay registros de ventas", HttpStatus.NO_CONTENT));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, "Listado de ventas", HttpStatus.OK, ventasUsuario));
        }
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> crearVenta(@RequestBody VentaDTO venta){
        CrearVentaDTO newVenta = service.crearVenta(venta.getIdArticulo(), venta.getIdUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).
                body(new ApiResponse<>(true, "Venta creada", HttpStatus.CREATED, newVenta));
    }
    
    @GetMapping("/ganancias/{id}")
    public ResponseEntity<ApiResponse<?>> obtenerGananciasDeUsuario(@PathVariable("id") @Valid Long id){
        TotalDTO total = service.obtenerGanancias(id);
        if(total.getTotal() <= 0){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(true, "Este usuario no tiene ventas", HttpStatus.NO_CONTENT, total));
        } else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(true, "Ventas del usuario: " + total.getNombreUsuario(), 
                            HttpStatus.OK, total));
        }
    }


}
