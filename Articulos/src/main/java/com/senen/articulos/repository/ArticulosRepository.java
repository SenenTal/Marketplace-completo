/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.articulos.repository;

import com.senen.articulos.DTO.ArticulosCategoriaDTO;
import com.senen.articulos.DTO.ArticulosDTO;
import com.senen.articulos.DTO.ArticulosUsuariosDTO;
import com.senen.articulos.entities.Articulos;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author senen
 */
@Repository
public interface ArticulosRepository extends JpaRepository<Articulos, Long>{
    
    @Query(value = "SELECT * FROM fn_llamar_articulos()", nativeQuery = true)
    List<ArticulosCategoriaDTO> listarArticulos();
    
    @Transactional
    @Query(value = "SELECT * FROM fn_crear_articulo(:titulo, :descripcion, "
            + ":precio, :categoria, :ubicacion, :imagen, :id_usuario)", nativeQuery = true)
    Articulos crearArticulo(@Param("titulo") String titulo, @Param("descripcion") String descripcion,
            @Param("precio") float precio, @Param("categoria") String categoria, 
            @Param("ubicacion") String ubicacion, @Param("imagen") String imagen, 
            @Param("id_usuario") Long idUsuario);
    
    @Modifying
    @Transactional
    @Query(value = "CALL sp_delete_articulo_id(:id)", nativeQuery = true)
    void borrarArticulo(@Param("id") Long id);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_buscar_por_categoria(:categoria)", nativeQuery = true)
    List<ArticulosCategoriaDTO> buscarPorCategoria(@Param("categoria") String categoria);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_buscar_nombre_articulo(:titulo)", nativeQuery = true)
    List<ArticulosCategoriaDTO> buscarPorTitulo(@Param("titulo") String titulo);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_obtener_articulo_por_id(:id)", nativeQuery = true)
    ArticulosDTO buscarPorId(@Param("id") Long id_articulo);
    
    @Transactional
    @Query(value = """
                   SELECT * FROM fn_modificar_articulo_1(
                           CAST(:id_articulo AS BIGINT),
                           CAST(:id_usuario AS BIGINT),
                           CAST(:titulo AS VARCHAR),
                           CAST(:descripcion AS VARCHAR),
                           CAST(:precio AS REAL),
                           CAST(:categoria AS VARCHAR),
                           CAST(:estado_articulo AS BOOLEAN),
                           CAST(:ubicacion AS VARCHAR),
                           CAST(CURRENT_TIMESTAMP AS TIMESTAMP)
                       )
                   """, nativeQuery = true)
    ArticulosCategoriaDTO modificarArticulo1(@Param("id_articulo") Long id_articulo, @Param("id_usuario") Long id_usuario,
            @Param("titulo") String titulo, @Param("descripcion") String descripcion, @Param("precio") float precio,
            @Param("categoria") String categoria, @Param("estado_articulo") boolean estadoArticulo, 
            @Param("ubicacion") String ubicacion);
    
    @Transactional
    @Query(value = """
                   SELECT * FROM fn_modificar_articulo_2(
                           CAST(:id_articulo AS BIGINT),
                           CAST(:id_usuario AS BIGINT),
                           CAST(:titulo AS VARCHAR),
                           CAST(:descripcion AS VARCHAR),
                           CAST(:precio AS REAL),
                           CAST(:categoria AS VARCHAR),
                           CAST(:estado_articulo AS BOOLEAN),
                           CAST(:ubicacion AS VARCHAR),
                           CAST(CURRENT_TIMESTAMP AS TIMESTAMP),
                           CAST(:imagen AS VARCHAR)
                       )
                   """, nativeQuery = true)
    ArticulosCategoriaDTO modificarArticulo2(@Param("id_articulo") Long id_articulo, @Param("id_usuario") Long id_usuario,
            @Param("titulo") String titulo, @Param("descripcion") String descripcion, @Param("precio") float precio,
            @Param("categoria") String categoria, @Param("estado_articulo") boolean estadoArticulo, 
            @Param("ubicacion") String ubicacion, @Param("imagen") String imagen);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_listar_articulos_usuario(:id_usuario)", nativeQuery = true)
    List<ArticulosCategoriaDTO> obtenerArticulosDeUsuario(@Param("id_usuario") Long id);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_listar_articulos_usuarios()", nativeQuery = true)
    List<ArticulosUsuariosDTO> obtenerArticulosYUsuarios();
    
    @Transactional
    @Query(value="SELECT * FROM fn_listar_articulos_vendidos_id(:id_usuario)", nativeQuery=true)
    List<ArticulosUsuariosDTO> obtenerArticulosVendidosDelUsuario(@Param("id_usuario") Long id);
    
}
