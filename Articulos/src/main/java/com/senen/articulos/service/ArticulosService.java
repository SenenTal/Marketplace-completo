/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.articulos.service;

import com.senen.articulos.DTO.ArticulosCategoriaDTO;
import com.senen.articulos.DTO.ArticulosDTO;
import com.senen.articulos.DTO.ArticulosUsuariosDTO;
import com.senen.articulos.DTO.InsertarArticulosDTO;
import com.senen.articulos.DTO.ModificarArticulo1DTO;
import com.senen.articulos.DTO.ModificarArticulo2DTO;
import com.senen.articulos.entities.Articulos;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author senen
 */
@Service
public interface ArticulosService {
    
    ArticulosDTO obtenerArticulo(Long id);
    
    List<ArticulosCategoriaDTO> obtenerArticulos();
    
    ArticulosCategoriaDTO actualizarArticulo1(Long idArticulo, ModificarArticulo1DTO articulo);
    
    ArticulosCategoriaDTO actualizarArticulo2(Long idArticulo, 
            ModificarArticulo1DTO articulo, MultipartFile imagen);
    
    void eliminarArticulo(Long id);
    
    Articulos crearArticulo(InsertarArticulosDTO newArticulo, MultipartFile imagen);
    
    List<ArticulosCategoriaDTO> obtenerArticulosPorUsuario(Long id);
    
    List<ArticulosCategoriaDTO> buscarPorCategoria(String categoria);
    
    List<ArticulosCategoriaDTO> buscarPorTitulo(String titulo);
    
    List<ArticulosUsuariosDTO> obtenerArticulosYUsuarios();
    
    List<ArticulosUsuariosDTO> obtenerArticulosVendidos(Long id);
    
}
