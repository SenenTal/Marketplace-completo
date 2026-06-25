/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.senen.usuarios.repository;

import com.senen.usuarios.dto.AccesoDTO;
import com.senen.usuarios.dto.UserAdminDTO;
import com.senen.usuarios.entities.Usuarios;
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
public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{
    
    @Query(value = "SELECT * FROM fn_llamar_usuarios()", nativeQuery = true)
    List<Usuarios> listarUsuarios();
    
    @Transactional
    @Query(value = "SELECT * FROM fn_obtener_usuario_por_id(:p_id)", nativeQuery = true)
    Usuarios obtenerUsuario(@Param("p_id")Long id);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_crear_usuario(:user, :nickname, :password)", nativeQuery = true)
    Usuarios crearUsuario(@Param("user") String user, @Param("nickname") String nickname,
            @Param("password") String password);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_hacer_admin(:user)", nativeQuery = true)
    UserAdminDTO hacerAdmin(@Param("user") String user);
    
    @Modifying
    @Transactional
    @Query(value = "CALL sp_borrar_usuario(:id)", nativeQuery = true)
    void borrarUsuario(@Param("id") Long id);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_iniciar_sesion(:user, :password)", nativeQuery= true)
    Usuarios iniciarSesion(@Param("user") String user, @Param("password") String password);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_update_usuario(:id, :user, :password, :nickname)", nativeQuery = true)
    Usuarios actualizarUsuario(@Param("id") Long id,@Param("user") String user, @Param("nickname") String nickname,
            @Param("password") String password);
    
    @Transactional
    @Query(value = "SELECT * FROM fn_hacer_user(:user)", nativeQuery = true)
    UserAdminDTO hacerUser(@Param("user") String user);
    
    @Transactional
    @Query(value="SELECT * FROM fn_validar_credenciales(:id, :user, :password)", nativeQuery=true)
    AccesoDTO validarCredenciales(@Param("id") Long id, @Param("user") String user, 
            @Param("password") String password);

}
