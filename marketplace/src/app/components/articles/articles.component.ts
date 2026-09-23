import {Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef} from '@angular/core';
import {ArticuloService} from '../../services/articulo.service';
import {ArticulosCategoriaDTO} from '../../models/articulos/articulos-categoria.dto';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {FiltroDTO} from '../../models/articulos/filtro.dto';

@Component({
  selector: 'app-articles',
  standalone: false,
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.css',
  changeDetection: ChangeDetectionStrategy.Eager
})
export class ArticlesComponent implements OnInit {

  page = 1;
  size = 8;
  totalPages = 0;
  articulosFiltrados: ArticulosCategoriaDTO[] = [];
  articulos: ArticulosCategoriaDTO[] = [];
  disponible: string[] = [];
  imageUrl = "http://localhost:8001/imagenes/";
  filtroTitulo: string = '';
  filtroCategoria: string = '';
  filtro: FiltroDTO = {
    titulo: '',
    categoria: ''
  }

  constructor(private service: ArticuloService,
              private router: Router, private cdr: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.filtrarArticulos();
  }

  listarArticulos() {
    this.service.listarArticulos().subscribe({
      next: (respuesta) => {
        console.log('RESPUESTA COMPLETA:', respuesta);
        console.log('DATA:', respuesta.data);
        console.log('DATA LENGTH:', respuesta.data.length);

        this.articulos = respuesta.data;
        this.page = 1;

        this.aplicarPaginacion();
        this.cdr.markForCheck();
      },
      error: (error) => {
        console.log("Error al obtener articulos ", error.error.message);
      }
    });
  }

  verArticulo(id: number) {
    console.log(`ìd del articulo: ${id}`)
    this.router.navigate(['/articulos', id])
  }


  aplicarPaginacion() {
    //Calcular cuantas páginas existen
    this.totalPages = Math.ceil(this.articulos.length / this.size);
    //Por default, dejarlo en la página 1 sin articulos
    if (this.totalPages === 0) {
      this.page = 1;
      this.articulosFiltrados = [];
      return;
    }
    // Evitar que page salga del rango
    if (this.page < 1) {
      this.page = 1;
    }

    if (this.page > this.totalPages) {
      this.page = this.totalPages;
    }

    const inicio = (this.page - 1) * this.size;
    const fin = inicio + this.size;

    this.articulosFiltrados = this.articulos.slice(inicio, fin);
    this.cdr.markForCheck();
    console.log(`inicio: ${inicio}, fin: ${fin}`);
  }

  paginaSiguiente() {
    if (this.page < this.totalPages) {
      this.page++;
      this.aplicarPaginacion();
    }
  }

  paginaAnterior() {
    if (this.page > 1) {
      this.page--;
      this.aplicarPaginacion();
    }
  }

  filtrarArticulos() {
    this.filtro
    {
      this.filtro.titulo = this.filtroTitulo.trim();
      this.filtro.categoria = this.filtroCategoria;
    };
    console.log(`Filtro: ${this.filtro}`)
    //Si no hay ningún filtro, obtiene todos los articulos
    if (this.filtroTitulo === '' && this.filtroCategoria === '') {
      this.listarArticulos();
      return;
    }
    this.service.filtroBusquedaArticulos(this.filtro).subscribe({
      next: (resp) => {
        this.articulos = resp.data;
        this.page = 1;
        this.aplicarPaginacion();
        console.log(`Resultados: ${this.articulos}`);
        this.cdr.markForCheck();
      },
      error: (error) => {
        console.log(`${error.error.message}`);
      }
    })
  }

}

/*listarPorTitulo() {
    //Filtrar los espacios en titulo
    if (this.filtroTitulo.trim() === '') {
      this.listarArticulos(); // volver a todos
      return;
    }
    this.service.buscarArticulosPorTitulo(this.filtroTitulo).subscribe({
      next: (resp) => {
        this.articulos = resp.data;
        console.log(this.articulos);
        this.page = 1;
        this.aplicarPaginacion();
        this.cdr.markForCheck();
      }, error: (error) => {
        Swal.fire('Error', `${error.error.message}` || 'Error Desconocido', 'error')
      }
    })
  }*/

/*listarPorCategoria() {
  if (this.filtroCategoria === '') {
    this.listarArticulos();
    return;
  }
  this.service.buscarArticulosPorCategoria(this.filtroCategoria).subscribe({
    next: (resp) => {
      this.articulos = resp.data;
      console.log(this.articulos);
      this.page = 1;
      this.aplicarPaginacion();
      this.cdr.markForCheck();
    }, error: (error) => {
      Swal.fire('Error', `${error.error.message}` || 'Error Desconocido', 'error');
    }
  })
}*/
