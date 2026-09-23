import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ventas } from '../models/ventas/ventas.model';
import { ApiResponse } from '../models/response/apiResponse';
import { CrearVentaDTO } from '../models/ventas/crear-ventas.dto';
import { VentasUsuarioDTO } from '../models/ventas/ventas-usuario.dto';
import { TotalDTO } from '../models/ventas/total.dto';
import { VentaDTO } from '../models/ventas/venta.dto';

@Injectable({
  providedIn: 'root'
})
export class VentasService {
  private url = 'http://localhost:8002/ventas'

  constructor(private http:HttpClient) { }

  listarVentas(): Observable<ApiResponse<Ventas>>{
    return this.http.get<ApiResponse<Ventas>>(`${this.url}`);
  }

  crearVenta(venta: VentaDTO): Observable<ApiResponse<CrearVentaDTO>>{
    /*const formData = new FormData();
    formData.append('venta', new Blob([JSON.stringify(venta)], {type: 'application/json'}));*/
    return this.http.post<ApiResponse<CrearVentaDTO>>(`${this.url}`, venta);
  }

  obtenerVentasUsuario(id: number): Observable<ApiResponse<VentasUsuarioDTO>>{
    return this.http.get<ApiResponse<VentasUsuarioDTO>>(`${this.url}/${id}`);
  }

  obtenerGananciasUsuario(id: number): Observable<ApiResponse<TotalDTO>>{
    return this.http.get<ApiResponse<TotalDTO>>(`${this.url}/ganancias/${id}`)
  }


}
