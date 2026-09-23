package com.senen.ventas.DTO;

public class VentaDTO {
    private Long idArticulo;
    private Long idUsuario;

    public Long getIdArticulo(){
        return this.idArticulo;
    };
    public Long getIdUsuario(){
        return this.idUsuario;
    };

    public void setIdArticulo(Long idArticulo){
        this.idArticulo = idArticulo;
    }

    public void setIdUsuario(Long idUsuario){
        this.idUsuario = idUsuario;
    }
}
