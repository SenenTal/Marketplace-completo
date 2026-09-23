import {Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ArticuloService} from '../../services/articulo.service';
import {ArticulosCategoriaDTO} from '../../models/articulos/articulos-categoria.dto';
import Swal from 'sweetalert2';
import {AuthService} from '../../services/auth.service';
import {VentasService} from '../../services/ventas.service';
import {ArticulosDTO} from '../../models/articulos/articulos.dto';
import {VentaDTO} from '../../models/ventas/venta.dto';
import {UsuariosService} from '../../services/usuarios.service';

@Component({
  selector: 'app-articles-detail',
  standalone: false,
  templateUrl: './articles-detail.component.html',
  styleUrl: './articles-detail.component.css',
  changeDetection: ChangeDetectionStrategy.Eager
})
export class ArticlesDetailComponent implements OnInit {
  idUsuario!: number;
  sesion: boolean = false;
  idArticulo!: number;
  saldo!: number;
  articulo: ArticulosDTO = {
    idArticulo: 0,
    titulo: '',
    descripcion: '',
    precio: 0,
    categoria: '',
    estadoArticulo: false,
    ubicacion: '',
    fechaPublicacion: new Date,
    imagen: '',
    idUsuario: 0
  }
  venta: VentaDTO = {
    idArticulo: 0,
    idUsuario: 0
  }
  articulosUsuario: ArticulosDTO[] = [];
  imageUrl = 'http://localhost:8001/imagenes'
  imagen: string = '';
  esPropietario: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private articuloService: ArticuloService,
    private ventasService: VentasService,
    private router: Router,
    private auth: AuthService,
    private cdr: ChangeDetectorRef,
    private usuarioService: UsuariosService
  ) {
  }

  ngOnInit(): void {
    this.auth.sesion$.subscribe(valor => {
      this.sesion = valor;
    })
    this.idUsuario = this.auth.getUserId();
    this.route.paramMap.subscribe(params => {
      this.idArticulo = Number(params.get('id'));
      this.llamarArticulo();
      this.dineroUsuario();
    });
  }

  //Llamar el articulo por Id
  llamarArticulo() {
    this.articuloService.articuloPorId(this.idArticulo).subscribe({
      next: (resp) => {
        this.articulo = resp.data;
        this.imagen = `${this.imageUrl}/${this.articulo.imagen}`
        //Comparar si el id del usuario es igual a id_usuario de articulo
        this.esPropietario = this.articulo.idUsuario === this.idUsuario;
        console.log(`${this.articulo.titulo}`);
        this.cdr.markForCheck();
      }, error: (error) => {
        Swal.fire(
          'Fallo de conexión',
          `${error.error?.message}` || 'Error desconocido',
          'error'
        )
      }
    })
  }

  //Para comprar producto
  comprarProducto() {
    if (!this.idUsuario) {
      Swal.fire('Error', 'Debes iniciar sesión', 'error');
      return;
    } else {
      Swal.fire({
        title: 'Antes de comprar',
        text: `¿Esta seguro de comprar ${this.articulo.titulo}?`,
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Comprar articulo',
        cancelButtonText: 'Cancelar compra'
      }).then((resultado) => {
        if (resultado.isConfirmed) {
          this.venta.idArticulo = this.articulo.idArticulo;
          this.venta.idUsuario = this.idUsuario;
          //Comparar dinero electronico con precio de articulo
          if(this.saldo < this.articulo.precio){
            Swal.fire('warning',`Su dinero no alcanza para comprar ${this.articulo.titulo}`,'warning');
          }else{
          this.ventasService.crearVenta(this.venta).subscribe(
            {
              next: (resp) => {
                Swal.fire('Compra hecha', `Usted compró: ${this.articulo.titulo}`, 'success')
                this.router.navigate(['/articulos']);
              },
              error: (resp) => {
                Swal.fire('Fallo en la venta', `${resp.error.message}` || 'Error desconocido', 'error');
              }
            }
          )
        }} else if (resultado.isDismissed) {
          return;
        }
      });
    }
  }
  dineroUsuario() {
    this.usuarioService.obtenerUsuarioPorId(this.idUsuario).subscribe(
      {
        next: (resp) => {
          this.saldo = resp.data.dinero_electronico;
          this.cdr.markForCheck();
          console.log(`${this.saldo}`)
        },
        error: (error) => {
          console.log(`${error.error.message}`);
        }
      }
    )
  }


  //Función para validar si el usuario le pertenece la publicación
  //Para deshabilitar el botón comprar (no puede comprar su propio articulo)
  // verificarUsuario() {
  //   if(this.idUsuario === this.articulo.idUsuario){
  //     this.esPropietario = true
  //   }
  // }
}
