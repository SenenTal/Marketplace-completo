/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.ventas.service.impl;

import com.senen.ventas.DTO.CrearVentaDTO;
import com.senen.ventas.DTO.TotalDTO;
import com.senen.ventas.DTO.VentasUsuarioDTO;
import com.senen.ventas.entities.Ventas;
import com.senen.ventas.exception.DatabaseOperationException;
import com.senen.ventas.repository.VentasRepository;
import com.senen.ventas.service.VentasService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author senen
 */
@Service
public class VentasServiceImpl implements VentasService {

    @Autowired
    private VentasRepository repository;

    @Override
    public List<VentasUsuarioDTO> obtenerVentasPorUsuario(Long id) {
        try {
            return repository.obtenerVentasUsuario(id);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public CrearVentaDTO crearVenta(Long id) {
        try {
            return repository.crearVenta(id);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public TotalDTO obtenerGanancias(Long id) {
        try {
            return repository.obtenerGanancias(id);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public List<Ventas> listarVentas() {
        try {
            return repository.obtenerTodasLasVentas();
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }
    
}
