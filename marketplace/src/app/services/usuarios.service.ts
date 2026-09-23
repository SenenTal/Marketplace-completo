import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/response/apiResponse';
import { HttpClient } from '@angular/common/http';
import { Usuarios } from '../models/usuarios/usuarios.model';
import { UserDTO } from '../models/usuarios/usuario.dto';
import { UserAccessDTO } from '../models/usuarios/usuario-access.dto';
import { UserAdminDTO } from '../models/usuarios/usuarios-admin.dto';
import { User1DTO } from '../models/usuarios/usuario1.dto';
import {newUser} from '../models/usuarios/newUser.dto';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  private url = 'http://localhost:8003/usuarios'

  constructor(private http: HttpClient) { }

  listarUsuarios():Observable<ApiResponse<Usuarios[]>>{
    return this.http.get<ApiResponse<Usuarios[]>>(`${this.url}`);
  }

  obtenerUsuarioPorId(id:number):Observable<ApiResponse<Usuarios>>{
    return this.http.get<ApiResponse<Usuarios>>(`${this.url}/${id}`);
  }

  crearUsuario(newUsuario:UserDTO):Observable<ApiResponse<newUser>>{
    return this.http.post<ApiResponse<newUser>>(`${this.url}`, newUsuario);
  }

  borrarUsuario(id: number):Observable<ApiResponse<null>>{
    return this.http.delete<ApiResponse<null>>(`${this.url}/${id}`);
  }

  iniciarSesion(usuario: UserAccessDTO):Observable<ApiResponse<Usuarios>>{
    return this.http.post<ApiResponse<Usuarios>>(`${this.url}/sesion`, usuario);
  }

  hacerAdmin(name: String):Observable<ApiResponse<UserAdminDTO>>{
    return this.http.put<ApiResponse<UserAdminDTO>>(`${this.url}/admin/${name}`, null);
  }

  hacerUser(name: String):Observable<ApiResponse<UserAdminDTO>>{
    return this.http.put<ApiResponse<UserAdminDTO>>(`${this.url}/user/${name}`, null);
  }

  actualizarUsuario(usuario: UserDTO, id:number):Observable<ApiResponse<Usuarios>>{
    return this.http.put<ApiResponse<Usuarios>>(`${this.url}/${id}`, usuario);
  }

  validarCredenciales(usuario: UserAccessDTO, id: number):Observable<ApiResponse<Boolean>>{
    return this.http.post<ApiResponse<Boolean>>(`${this.url}/validar/${id}`, usuario);
  }

  recargarDineroElectronico(id: number, dinero: number):Observable<ApiResponse<number>>{
    return this.http.put<ApiResponse<number>>(`${this.url}/recargar/${id}/${dinero}`, null);
  }
}
