/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.articulos.DTO;

import java.time.LocalDateTime;

/**
 *
 * @author senen
 */
public class InsertarArticulosDTO {

    String titulo;
    String descripcion;
    float precio;
    String categoria;
    boolean estadoArticulo;
    String ubicacion;
    Long idUsuario;

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isEstadoArticulo() {
        return estadoArticulo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    
}
