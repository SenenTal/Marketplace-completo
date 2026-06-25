/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.articulos.controller;

import com.senen.articulos.DTO.ArticulosCategoriaDTO;
import com.senen.articulos.DTO.ArticulosDTO;
import com.senen.articulos.DTO.ArticulosUsuariosDTO;
import com.senen.articulos.DTO.InsertarArticulosDTO;
import com.senen.articulos.DTO.ModificarArticulo1DTO;
import com.senen.articulos.entities.Articulos;
import com.senen.articulos.response.ApiResponse;
import com.senen.articulos.service.ArticulosService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author senen
 */
@CrossOrigin(origins={"http://localhost:4000"})
@RestController
@RequestMapping("/articulos")
public class ArticulosController {

    @Autowired
    private ArticulosService service;

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> obtenerArticulos() {
        List<ArticulosCategoriaDTO> listado = service.obtenerArticulos();
        if (listado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    new ApiResponse<>(true, "No hay articulos", HttpStatus.NO_CONTENT));
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    new ApiResponse<>(true, "Listado de articulos", HttpStatus.OK, listado));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> obtenerArticuloPorId(@PathVariable(name = "id") Long id) {
        ArticulosDTO articulo = service.obtenerArticulo(id);
        if (articulo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(true, "No encontrado o no existe", HttpStatus.NO_CONTENT));
        }
        return ResponseEntity.status(HttpStatus.OK).
                body(new ApiResponse<>(true, "Articulo encontrado", HttpStatus.OK, articulo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> eliminarArticuloPorId(@PathVariable(name = "id") Long id) {
        ArticulosDTO articulo = service.obtenerArticulo(id);
        if (articulo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "No se pudo eliminar el articulo", HttpStatus.NOT_IMPLEMENTED));
        } else {
            service.eliminarArticulo(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, "Articulo eliminado: " + articulo.getTitulo(), HttpStatus.NO_CONTENT));
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<ApiResponse<?>> obtenerArticulosConUsuarios() {
        List<ArticulosUsuariosDTO> listado = service.obtenerArticulosYUsuarios();
        if (listado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    new ApiResponse<>(true, "No hay articulos", HttpStatus.NO_CONTENT));
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    new ApiResponse<>(true, "Listado de articulos aisgnados por usuario", HttpStatus.OK, listado));
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> crearArticulo(@RequestPart @Valid InsertarArticulosDTO newArticulo,
            @RequestPart("imagen") MultipartFile imagen ) {
        Articulos nuevoArticulo = service.crearArticulo(newArticulo, imagen);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "nuevo articulo", HttpStatus.CREATED, nuevoArticulo));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<ApiResponse<?>> buscarPorTitulo(@PathVariable("titulo") @Valid String titulo) {
        List<ArticulosCategoriaDTO> listado = service.buscarPorTitulo(titulo);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Listado por titulo", HttpStatus.FOUND, listado));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<ApiResponse<?>> buscarPorCategoria(@PathVariable("categoria") @Valid String categoria) {
        List<ArticulosCategoriaDTO> listado = service.buscarPorCategoria(categoria);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Listado por categoria", HttpStatus.FOUND, listado));
    }

    @PutMapping("/1/{idArticulo}")
    public ResponseEntity<ApiResponse<?>> actualizarArticulo1(@PathVariable("idArticulo") @Valid Long idArticulo, @RequestBody @Valid ModificarArticulo1DTO articulo) {
        ArticulosCategoriaDTO articuloUpdated = service.actualizarArticulo1(idArticulo, articulo);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(true, "Articulo " + articuloUpdated.getTitulo() + " Actualizado",
                        HttpStatus.ACCEPTED, articuloUpdated));
    }

    @PutMapping("/2/{idArticulo}")
    public ResponseEntity<ApiResponse<?>> actualizarArticulo2(@PathVariable("idArticulo") @Valid Long idArticulo, 
            @RequestPart @Valid ModificarArticulo1DTO articulo, 
            @RequestPart @Valid MultipartFile imagen) {
        ArticulosCategoriaDTO articuloUpdated = service.actualizarArticulo2(idArticulo, articulo, imagen);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(true, "Articulo " + articuloUpdated.getTitulo() + " Actualizado",
                        HttpStatus.ACCEPTED, articuloUpdated));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<?>> obtenerArticulosPorIdUsuario(@PathVariable("idUsuario") @Valid Long idUsuario) {
        List<ArticulosCategoriaDTO> listado = service.obtenerArticulosPorUsuario(idUsuario);
        if (listado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, "Este usuario no ha implementado articulos", HttpStatus.NOT_FOUND));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, "Articulos de Usuario: " + idUsuario,
                            HttpStatus.FOUND, listado));
        }
    }
    
    @GetMapping("usuarios/vendidos/{idUsuario}")
    public ResponseEntity<ApiResponse<?>> obtenerArticulosVendidosPorIdUsuario(
            @PathVariable("idUsuario") @Valid Long idUsuario) {
        List<ArticulosUsuariosDTO> listado = service.obtenerArticulosVendidos(idUsuario);
        if (listado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, "Este usuario no ha implementado articulos", HttpStatus.NOT_FOUND));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, "Articulos de Usuario: " + idUsuario,
                            HttpStatus.FOUND, listado));
        }
    }
}
