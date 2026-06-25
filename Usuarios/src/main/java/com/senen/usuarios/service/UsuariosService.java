/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.senen.usuarios.service;

import com.senen.usuarios.dto.AccesoDTO;
import com.senen.usuarios.dto.UserAccessDTO;
import com.senen.usuarios.dto.UserAdminDTO;
import com.senen.usuarios.dto.UserDTO;
import com.senen.usuarios.entities.Usuarios;
import java.util.List;

/**
 *
 * @author senen
 */
public interface UsuariosService {
    
    Usuarios obtenerUsuario(Long id);
    
    Usuarios obtenerAcceso(String username, String password);
    
    Usuarios crearUsuario(String username, String password, String nickname);
    
    List<Usuarios> obtenerUsuarios();
    
    void eliminarUsuario(Long id);
    
    UserAdminDTO hacerAdmin(String username);
    
    Usuarios modificarUsuario(Long id, String username, String password, String nickname);
    
    UserAdminDTO hacerUser(String username);
    
    AccesoDTO validarCredenciales(UserAccessDTO usuario, Long id);
    
}
