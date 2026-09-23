import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {UsuariosService} from '../../services/usuarios.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Usuarios} from '../../models/usuarios/usuarios.model';
import {ArticuloService} from '../../services/articulo.service';
import {ArticulosCategoriaDTO} from '../../models/articulos/articulos-categoria.dto';
import Swal from 'sweetalert2';
import {ArticulosUsuariosDTO} from '../../models/articulos/articulos-usuarios.dto';
import {VentasService} from '../../services/ventas.service';
import {AuthService} from '../../services/auth.service';
import {User1DTO} from '../../models/usuarios/usuario1.dto';
import {UserAccessDTO} from '../../models/usuarios/usuario-access.dto';
import { ElementRef, ViewChild } from '@angular/core';
import {ComprasDTO} from '../../models/ventas/compras.dto';

@Component({
  selector: 'app-options',
  standalone: false,
  templateUrl: './options.component.html',
  styleUrl: './options.component.css',
  changeDetection: ChangeDetectionStrategy.Eager
})
export class OptionsComponent implements OnInit {
  @ViewChild('btnCerrarModal') btnCerrarModal!: ElementRef<HTMLButtonElement>;
  id: number;
  user: Usuarios = {
    id: 0,
    user: '',
    nickname: '',
    role: '',
    password: '',
    dinero_electronico: 0,
    ubicacion: ''
  };
  articulos: ArticulosCategoriaDTO[] = [];
  articulosV: ArticulosUsuariosDTO[] = [];
  total: number = 0;
  acceso: UserAccessDTO = {
    username: '',
    password: ''
  }
  dinero_electronico: number = 0;
  accesoBloqueado: boolean = false;
  articulosComprados: ComprasDTO[] = [];

  constructor(private usuariosService: UsuariosService,
              private route: ActivatedRoute,
              private articulosService: ArticuloService,
              private router: Router,
              private ventasService: VentasService,
              private auth: AuthService, private cdr: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.obtenerCredenciales();
    this.obtenerArticulos();
    this.obtenerArticulosVendidos();
    this.obtenerGanancias();
    this.comprasUser();
  }

  obtenerCredenciales() {
    this.usuariosService.obtenerUsuarioPorId(this.id)
      .subscribe({
        next: (res) => {
          //console.log(`${this.user.usuario}, ${this.user.nickname}, ${this.user.ubicacion}`);
          //obtenemos los datos para el usuario
          this.user = res.data;
          console.log(`${this.user.user}`);
          this.user.id = this.id;
          this.cdr.markForCheck();
        }, error: (error) => {
          console.log(`${error.error.message}`);
        }
      });
  }

  obtenerArticulos() {
    this.articulosService.listarArticulosPorUsuarioId(this.id)
      .subscribe({
        next: (resp) => {
          this.articulos = resp.data;
          console.log(this.articulos);
          this.cdr.markForCheck();
        }
        , error: (error) => {
          console.log(`Error: ${error.error.message}`)
        }
      })
  }

  eliminarUsuario() {
    Swal.fire({
      title: '¿Esta seguro?',
      text: 'Seguro que quiere darse de baja? ',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed)
        this.usuariosService.borrarUsuario(this.id).subscribe({
          next: (resp) => {
            if (resp.success) {
              this.cerrarSesion();
              Swal.fire(
                'Eliminado',
                `${resp.message}`,
                'success'
              )
            }
          }, error: (error) => {
            Swal.fire(
              'Error',
              `${error.error?.message}` || 'Error desconocido',
              'error'
            );
            console.log(`Error al eliminar: ${error}`);
          }
        })
    })
  }

  actualizarUsuario() {
    this.router.navigate(['/actualizarUsuario', this.id]);
  }

  modificarArticulo(id: number) {
    this.router.navigate(['/actualizarArticulo', id]);
  }

  eliminarArticulo(id: number) {
    Swal.fire({
      title: '¿Esta seguro?',
      text: 'Seguro que quiere eliminar:  ',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.articulosService.eliminarArticulo(id).subscribe({
          next: (resp) => {
            Swal.fire('Borrado', `${resp.message}`, 'success');
            this.obtenerArticulos();
            this.obtenerArticulosVendidos();
            this.obtenerGanancias();
          }, error: (error) => {
            Swal.fire('Error', `${error.error.message}` || 'Error desconocido', 'error');
            return;
          }
        })
      }
    })
  }

  obtenerArticulosVendidos() {
    this.articulosService.obtenerArticulosVendidosPorUsuario(this.id)
      .subscribe({
        next: (resp) => {
          this.articulosV = resp.data;
          console.log(this.articulosV);
          this.cdr.markForCheck();
        }, error: (error) => {
          console.log(`Error: ${error.error.message}`);
        }
      })
  }

  obtenerGanancias() {
    this.ventasService.obtenerGananciasUsuario(this.id).subscribe({
      next: (resp) => {
        this.total = resp.data.total;
        console.log(this.total);
        this.cdr.markForCheck();
      }, error: (error) => {
        console.log(`Error: ${error.error.message}`)
      }
    })
  }

  vaciarCredenciales() {
    this.acceso.password = "";
    this.acceso.username = "";
  }

  cerrarSesion() {
    this.auth.logout();
    //Debe redirigir a otra ruta que no sea options al cerrar sesion
    this.router.navigate(['/articulos'], {replaceUrl: true});
  }

  recargarDinero() {
    if (this.dinero_electronico <= 0) {
    Swal.fire('info', 'Agrega cantidad correcta', 'info');
    return;
    } else {
      this.usuariosService.recargarDineroElectronico(this.id, this.dinero_electronico).subscribe({
        next: (resp) => {
          Swal.fire(
            'info', `Dinero electrónico actualizado: ${resp.data}`, 'success'
          )
          this.accesoBloqueado = false;
          //Seleciona el elemento del botón cancelar. Para que seleccione 'data-bs-dismiss="modal"'
          //Para también cerrar
          this.btnCerrarModal.nativeElement.click();
          this.user.dinero_electronico = resp.data;
          this.cdr.markForCheck();
        },
        error: (error) => {
          Swal.fire(
            'Error',
            `${error.error?.message}` || 'Error desconocido',
            'error'
          );
          console.log(`Error: ${error.error.message}`)
        }
      })
    }
  }

  getAcceso() {
    if (!this.acceso.username) {
      Swal.fire(
        'Escriba en username',
        'Datos en blanco',
        'info'
      );
      return;
    } else if (!this.acceso.password) {
      Swal.fire(
        'Escriba en password',
        'Datos en blanco',
        'info'
      );
      return;
    } else {
      this.usuariosService.validarCredenciales(this.acceso, this.id).subscribe({
        next: (resp) => {
          //console.log(`${resp.data.user}, ${resp.data.password}`);
          if (resp.data === true) {
            this.accesoBloqueado = true;
            this.cdr.markForCheck();
          } else {
            return;
          }
        },
        error: (error) => {
          Swal.fire(
            'Error',
            `${error.error?.message}` || 'Error desconocido',
            'error'
          );
          this.vaciarCredenciales();
          this.cdr.markForCheck();
        }
      })
    }
  }

  evitarNegativos(event: KeyboardEvent): void {
    if (event.key === '-') {
      event.preventDefault();
    }
  }

  cancelarRecarga() {
    this.accesoBloqueado = false;
    this.vaciarCredenciales();
    this.dinero_electronico = 0;
    this.cdr.markForCheck();
  }

  comprasUser(){
    this.articulosService.articulosCompradosPorUsuario(this.id).subscribe({
      next: (resp) => {
        this.articulosComprados = resp.data;
        this.cdr.markForCheck();
      },
      error: (error) => {
        console.log(`${error.error.message}`);
      }
    })
  }

}
