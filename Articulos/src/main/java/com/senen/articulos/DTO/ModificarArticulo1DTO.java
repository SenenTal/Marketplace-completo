/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.senen.articulos.DTO;

/**
 *
 * @author senen
 */
public class ModificarArticulo1DTO {
    Long idUsuario;
    String titulo;
    String descripcion;
    float precio;
    String categoria;
    boolean estadoArticulo;
    String ubicacion;

    public Long getIdUsuario() {
        return idUsuario;
    }

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

    public boolean getEstadoArticulo() {
        return estadoArticulo;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    
    
}
