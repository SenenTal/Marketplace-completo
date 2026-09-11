import {Component, OnInit, ChangeDetectorRef, ChangeDetectionStrategy} from '@angular/core';
import {UsuariosService} from '../../services/usuarios.service';
import {Usuarios} from '../../models/usuarios/usuarios.model';
import {ActivatedRoute, Router} from '@angular/router';
import {UserDTO} from '../../models/usuarios/usuario.dto';
import Swal from 'sweetalert2';
import {UserAccessDTO} from '../../models/usuarios/usuario-access.dto';
import {AuthService} from '../../services/auth.service';
import {User1DTO} from '../../models/usuarios/usuario1.dto';

@Component({
  selector: 'app-actualizar-user',
  standalone: false,
  templateUrl: './actualizar-user.component.html',
  styleUrl: './actualizar-user.component.css'
})
export class ActualizarUserComponent implements OnInit {
  id: number;
  user: Usuarios = {
    id: 0,
    user: '',
    nickname: '',
    password: '',
    role: '',
    dinero_electronico: 0,
    ubicacion: ''
  };
  userU: UserDTO = {
    usuario: '',
    password: '',
    nickname: '',
    ubicacion: ''
  };
  userA: UserAccessDTO = {
    username: '',
    password: ''
  };
  formularioActualizacion = false;
  ubicacionBloqueada = true;
  botonUbicacion = 'Modificar ubicación'

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.obtenerUsuario();
  }

  constructor(private usuariosService: UsuariosService,
              private route: ActivatedRoute,
              private router: Router,
              private auth: AuthService, private cdr: ChangeDetectorRef
  ) {
  }

  obtenerUsuario() {
    this.usuariosService.obtenerUsuarioPorId(this.id)
      .subscribe({
        next: (resp) => {
          this.user = resp.data;

          console.log(`${this.user.ubicacion}`);
          this.userU.ubicacion = this.user.ubicacion;
        }, error: (error) => {
          console.log(error);
        }
      });
  }

  //Llamar a actualizar usuario
  actualizarUsuario() {
    console.log(`${this.userU.usuario}, ${this.userU.nickname}, ${this.userU.password}, ${this.userU.ubicacion}`);
    if (!this.userU.usuario ||
      !this.userU.nickname ||
      !this.userU.password ||
      !this.userU.ubicacion) {
      Swal.fire('Error', 'Falta por rellenar los campos', 'info');
    } else if (this.userU.password.length <= 4) {
      Swal.fire('info', 'Contraseña pequeña, escriba mas de 4 caracteres', 'info');
    } else if (this.userU.usuario.length <= 4) {
      Swal.fire('info', 'Nombre de usuario corto, escriba mas de 4 caracteres', 'info');
    } else if (this.user.nickname.length <= 3) {
      Swal.fire('info', 'Apodo corto, escriba mas de 3 caracteres', 'info');
    } else if (!this.userU.ubicacion) {
      Swal.fire('info', 'Falta indicar la ubicación', 'info');
    } else {
      this.usuariosService.actualizarUsuario(this.userU, this.user.id).subscribe({
        next: (resp) => {
          Swal.fire(
            'Success',
            `Usuario Actualizado: ${this.user.user}`,
            'success'
          )
          const updateUser = {
            ...this.user,
            user: this.userU.usuario,
            nickname: this.userU.nickname
          };
          this.auth.setUser(updateUser);
          this.router.navigate(['/articulos']);
        },
        error: (error) => {
          Swal.fire(
            'Ocurrio algo mal',
            error,
            'error'
          )
          this.vaciarFormularioActualizaciones();
        }
      })
    }
  }

  //Validar credenciales antes de hacer aparecer la pantalla de modificar datos
  //de usuarios
  validacion() {
    console.log(`${this.userA.username} y ${this.userA.password}`)
    if (!this.userA.username || !this.userA.password) {
      Swal.fire(
        'Notificación',
        'Escriba datos correctos',
        'info'
      )
      this.vaciarCredenciales();
    } else {
      this.usuariosService.validarCredenciales(this.userA, this.id).subscribe({
        next: (resp) => {
          if (resp.success) {
            //Si las credenciales estan correctas aparecer pantalla de modificación de datos
            this.formularioActualizacion = true;
            Swal.fire(
              'Acceso a la modificación',
              'Puedes modificar tus datos',
              'success'
            )
            this.vaciarCredenciales();
          } else {
            Swal.fire(
              'Notificación',
              'Credenciales incorrectas',
              'info'
            )
            this.vaciarCredenciales();
          }
        },
        error: (error) => {
          Swal.fire(
            'Error',
            `Mensaje: ${error.error.message}`,
            'error'
          )
          this.vaciarCredenciales();
        }
      })
    }
  }

  vaciarCredenciales() {
    this.userA.password = '';
    this.userA.username = '';
    this.cdr.markForCheck();
  }

  vaciarFormularioActualizaciones() {
    this.userU.password = '';
    this.userU.nickname = '';
    this.userU.usuario = '';
    this.cdr.markForCheck();
  }
  modificarUbicacion(){
    if(!this.ubicacionBloqueada){
      this.botonUbicacion = 'Modificar ubicación';
      this.ubicacionBloqueada = true;
    } else {
      this.botonUbicacion = 'Bloquear ubicación';
      this.ubicacionBloqueada = false;
    }
  }

}
