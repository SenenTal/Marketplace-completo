/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.usuarios.service.impl;

import com.senen.usuarios.dto.AccesoDTO;
import com.senen.usuarios.dto.UserAccessDTO;
import com.senen.usuarios.dto.UserAdminDTO;
import com.senen.usuarios.dto.UserDTO;
import com.senen.usuarios.entities.Usuarios;
import com.senen.usuarios.exception.DatabaseOperationException;
import com.senen.usuarios.repository.UsuariosRepository;
import com.senen.usuarios.service.UsuariosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author senen
 */
@Service
public class UsuarioServiceImpl implements UsuariosService {

    @Autowired
    private UsuariosRepository repository;

    @Override
    public Usuarios obtenerUsuario(Long id) {
        try {
            return repository.obtenerUsuario(id);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public Usuarios obtenerAcceso(String username, String password) {
        try {
            Usuarios sesion = repository.iniciarSesion(username, password);
            return sesion;
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public Usuarios crearUsuario(String username, String password, String nickname) {
        try {
            System.out.println("ENTRO AL SERVICE CREAR USUARIO");
            return repository.crearUsuario(username, nickname, password);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public List<Usuarios> obtenerUsuarios() {
        try {
            return repository.listarUsuarios();
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public void eliminarUsuario(Long id) {
        try {
            repository.borrarUsuario(id);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public UserAdminDTO hacerAdmin(String username) {
        try {
            return repository.hacerAdmin(username);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public Usuarios modificarUsuario(Long id, String username, String password, String nickname) {
        try {
            return repository.actualizarUsuario(id,username, nickname, password);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }

    @Override
    public UserAdminDTO hacerUser(String username) {
        try {
            return repository.hacerUser(username);
        } catch (Exception ex) {
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }
    
    @Override
    public AccesoDTO validarCredenciales(UserAccessDTO usuario, Long id){
        try{
            return repository.validarCredenciales(id, usuario.getUsername(), usuario.getPassword());
        } catch(Exception ex){
            throw new DatabaseOperationException(ex.getLocalizedMessage(), ex.getCause());
        }
    }
}
