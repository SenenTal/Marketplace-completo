/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.usuarios.controller;

import com.senen.usuarios.dto.AccesoDTO;
import com.senen.usuarios.dto.UserAccessDTO;
import com.senen.usuarios.dto.UserAdminDTO;
import com.senen.usuarios.dto.UserDTO;
import com.senen.usuarios.entities.Usuarios;
import com.senen.usuarios.response.ApiResponse;
import com.senen.usuarios.service.UsuariosService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author senen
 */
@CrossOrigin(origins={"http://localhost:4000"})
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService service;

    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> obtenerUsuarios() {
        List<Usuarios> listado = service.obtenerUsuarios();
        if (listado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse(true, "Listado de usuarios vacía", HttpStatus.NO_CONTENT));
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse(true, "Listado de usuarios", HttpStatus.FOUND,
                            listado));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> obtenerUsuarioPorId(@PathVariable("id") @Valid Long id) {
        Usuarios usuario = service.obtenerUsuario(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ApiResponse<>(false, "Usuario no existe", HttpStatus.NOT_FOUND));
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    new ApiResponse<>(true, "Usuario encontrado", HttpStatus.OK, usuario));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> crearUsuario(@RequestBody @Valid UserDTO newUser) {
        Usuarios newUsuario = service.crearUsuario(newUser.getUsuario(),
                newUser.getPassword(), newUser.getNickname());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Usuario creado", HttpStatus.CREATED, newUsuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> borrarUsuario(@PathVariable("id") @Valid Long id) {
        Usuarios usuario = service.obtenerUsuario(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Usuario no existe", HttpStatus.NOT_FOUND));
        } else {
            service.eliminarUsuario(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
            new ApiResponse<>(true, "Usuario: " + usuario.getUser() + " ha sido eliminado", 
                    HttpStatus.NO_CONTENT));
        }
    }
    
    @PostMapping("/sesion")
    public ResponseEntity<ApiResponse<?>> iniciarSesion(@RequestBody @Valid UserAccessDTO usuario){
        Usuarios access = service.obtenerAcceso(usuario.getUsername(), usuario.getPassword());
        if(access == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new ApiResponse<>(false,"No autorizado",HttpStatus.UNAUTHORIZED, access));
        }else{
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
            new ApiResponse<>(true,"Ha iniciado sesion",HttpStatus.ACCEPTED, access));
    }
    }
        
    @PutMapping("/admin/{usuario}")
    public ResponseEntity<ApiResponse<?>> hacerAdmin(@PathVariable("usuario") @Valid String usuario)
    {
        UserAdminDTO newUsuario = service.hacerAdmin(usuario);
        if(newUsuario == null){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ApiResponse<>(false, "Petición no realizada", HttpStatus.NOT_IMPLEMENTED));
        } else{
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(true, "Nuevo administrador/a de página: " + newUsuario.getUsuario(), 
                            HttpStatus.ACCEPTED, newUsuario));
        }
    }
    
    @PutMapping("/user/{usuario}")
    public ResponseEntity<ApiResponse<?>> hacerUser(@PathVariable("usuario") @Valid String usuario)
    {
        UserAdminDTO newUsuario = service.hacerUser(usuario);
        if(newUsuario == null){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ApiResponse<>(false, "Petición no realizada", HttpStatus.NOT_IMPLEMENTED));
        } else{
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(true, "Roles de usuario asignados a usuario: " + newUsuario.getUsuario(), 
                            HttpStatus.ACCEPTED, newUsuario));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> actualizarUsuario(@PathVariable("id") @Valid Long id,@RequestBody @Valid UserDTO user){
        Usuarios usuarioUpdated = service.modificarUsuario(id, user.getUsuario(), user.getPassword(), 
                user.getNickname());
        if(usuarioUpdated == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Petición fallida", HttpStatus.BAD_REQUEST));
        }else{
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(true, "Usuario actualizado", HttpStatus.ACCEPTED,
                    usuarioUpdated));
        }
    }
    
    @PostMapping("validar/{id}")
    public ResponseEntity<ApiResponse<?>> validarCredenciales(@PathVariable("id") @Valid Long id,
            @RequestBody @Valid UserAccessDTO usuario){
        AccesoDTO acceso = service.validarCredenciales(usuario, id);
        if(acceso.getAcceso()){
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(acceso.getAcceso(),"Validación permitida",
                    HttpStatus.ACCEPTED, true));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(acceso.getAcceso(), "No autorizado",
                    HttpStatus.UNAUTHORIZED, false));
        }
    }
}
