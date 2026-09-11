/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.senen.usuarios.service;

import com.senen.usuarios.dto.*;
import com.senen.usuarios.entities.Usuarios;
import java.util.List;

/**
 *
 * @author senen
 */
public interface UsuariosService {
    
    Usuarios obtenerUsuario(Long id);
    
    UsuarioDTO obtenerAcceso(String username, String password);
    
    UsuarioDTO crearUsuario(String username, String password, String nickname, String ubicacion);
    
    List<Usuarios> obtenerUsuarios();
    
    void eliminarUsuario(Long id);
    
    UserAdminDTO hacerAdmin(String username);
    
    UsuarioDTO modificarUsuario(Long id, String username, String password, String nickname, String ubicacion);
    
    UserAdminDTO hacerUser(String username);
    
    AccesoDTO validarCredenciales(UserAccessDTO usuario, Long id);

    Float recargarDinero(Long id, Float dinero);
    
}
