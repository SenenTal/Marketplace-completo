/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.articulos.service.impl;

import com.senen.articulos.DTO.ArticulosCategoriaDTO;
import com.senen.articulos.DTO.ArticulosDTO;
import com.senen.articulos.DTO.ArticulosUsuariosDTO;
import com.senen.articulos.DTO.InsertarArticulosDTO;
import com.senen.articulos.DTO.ModificarArticulo1DTO;
import com.senen.articulos.DTO.ModificarArticulo2DTO;
import com.senen.articulos.entities.Articulos;
import com.senen.articulos.exception.DatabaseOperationException;
import com.senen.articulos.repository.ArticulosRepository;
import com.senen.articulos.service.ArticulosService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author senen
 */
@Service
public class ArticulosServiceImpl implements ArticulosService {

    @Autowired
    private ArticulosRepository repository;

    @Override
    public ArticulosDTO obtenerArticulo(Long id) {
        try {
            return repository.buscarPorId(id);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public List<ArticulosCategoriaDTO> obtenerArticulos() {
        try {
            return repository.listarArticulos();
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public ArticulosCategoriaDTO actualizarArticulo1(Long idArticulo, ModificarArticulo1DTO articulo) {
        //Hay que obtener los datos del cuerpo de ModificarArticuloDTO
        try {
            return repository.modificarArticulo1(idArticulo, articulo.getIdUsuario(), articulo.getTitulo(),
                    articulo.getDescripcion(), articulo.getPrecio(), articulo.getCategoria(),
                    articulo.getEstadoArticulo(), articulo.getUbicacion());
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public ArticulosCategoriaDTO actualizarArticulo2(Long idArticulo,
            ModificarArticulo1DTO articulo, MultipartFile imagen) {
        //Obtener los datos de ModificarArticuloDTO
        try {
            String nombreArchivo = null;
            if (imagen.isEmpty() || imagen == null) {
                throw new RuntimeException("Archivo vacío");
            }
            String archivo = imagen.getOriginalFilename();
            nombreArchivo = UUID.randomUUID() + "_" + (archivo != null ? archivo : "file");

            Path carpeta = Paths.get("C:\\Users\\senen\\Documents\\ExamenTecnico\\Imagenes");
            //Comprobación de la ruta
            if (!Files.exists(carpeta)) {
                Files.createDirectories(carpeta);
            }

            Path destino = carpeta.resolve(nombreArchivo);

            Files.copy(
                    imagen.getInputStream(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );
            return repository.modificarArticulo2(idArticulo, articulo.getIdUsuario(), articulo.getTitulo(),
                    articulo.getDescripcion(), articulo.getPrecio(), articulo.getCategoria(),
                    articulo.getEstadoArticulo(), articulo.getUbicacion(), nombreArchivo);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public void eliminarArticulo(Long id) {
        try {
            repository.borrarArticulo(id);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public Articulos crearArticulo(InsertarArticulosDTO newArticulo, MultipartFile imagen) {
        try {
            String nombreArchivo = null;
            if (imagen.isEmpty() || imagen == null) {
                throw new RuntimeException("Archivo vacío");
            }
            String archivo = imagen.getOriginalFilename();
            nombreArchivo = UUID.randomUUID() + "_" + (archivo != null ? archivo : "file");
            //C:\Users\senen\Documents\ExamenTecnico\Imagenes
            Path carpeta = Paths.get("C:\\Users\\senen\\Documents\\ExamenTecnico\\Imagenes");
            //Comprobación de la ruta
            if (!Files.exists(carpeta)) {
                Files.createDirectories(carpeta);
            }

            Path destino = carpeta.resolve(nombreArchivo);

            Files.copy(
                    imagen.getInputStream(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return repository.crearArticulo(newArticulo.getTitulo(), newArticulo.getDescripcion(),
                    newArticulo.getPrecio(), newArticulo.getCategoria(),
                    newArticulo.getUbicacion(), nombreArchivo, newArticulo.getIdUsuario());
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public List<ArticulosCategoriaDTO> obtenerArticulosPorUsuario(Long id) {
        try {
            return repository.obtenerArticulosDeUsuario(id);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public List<ArticulosCategoriaDTO> buscarPorCategoria(String categoria) {
        try {
            return repository.buscarPorCategoria(categoria);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public List<ArticulosCategoriaDTO> buscarPorTitulo(String titulo) {
        try {
            return repository.buscarPorTitulo(titulo);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public List<ArticulosUsuariosDTO> obtenerArticulosYUsuarios() {
        try {
            return repository.obtenerArticulosYUsuarios();
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public List<ArticulosUsuariosDTO> obtenerArticulosVendidos(Long id) {
        try {
            return repository.obtenerArticulosVendidosDelUsuario(id);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

}
