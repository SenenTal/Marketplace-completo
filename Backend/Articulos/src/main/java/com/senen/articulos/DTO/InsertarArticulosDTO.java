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

    public Long getIdUsuario() {
        return idUsuario;
    }

    
}
