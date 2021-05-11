package com.ec.proyectodemoandroid.modelos;

public class Atenciones {

    private long id;
    private String detallecontrol;
    private String tipoplaga;
    private String observaciones;
    private String acciones;
    private String fechaatencion;
    private String latitud;
    private String longitud;
    private String fotos;
    private String idusuario;

    public Atenciones(String detallecontrol, String tipoplaga, String observaciones, String acciones, String fechaatencion, String latitud, String longitud, String fotos, String idusuario) {
        this.detallecontrol = detallecontrol;
        this.tipoplaga = tipoplaga;
        this.observaciones = observaciones;
        this.acciones = acciones;
        this.fechaatencion = fechaatencion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fotos = fotos;
        this.idusuario = idusuario;
    }

    public Atenciones(String detallecontrol, String tipoplaga, String observaciones, String acciones, String fechaatencion, String latitud, String longitud, String fotos, String idusuario, long id) {
        this.id = id;
        this.detallecontrol = detallecontrol;
        this.tipoplaga = tipoplaga;
        this.observaciones = observaciones;
        this.acciones = acciones;
        this.fechaatencion = fechaatencion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fotos = fotos;
        this.idusuario = idusuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDetallecontrol() {
        return detallecontrol;
    }

    public void setDetallecontrol(String detallecontrol) {
        this.detallecontrol = detallecontrol;
    }

    public String getTipoplaga() {
        return tipoplaga;
    }

    public void setTipoplaga(String tipoplaga) {
        this.tipoplaga = tipoplaga;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getAcciones() {
        return acciones;
    }

    public void setAcciones(String acciones) {
        this.acciones = acciones;
    }

    public String getFechaatencion() {
        return fechaatencion;
    }

    public void setFechaatencion(String fechaatencion) {
        this.fechaatencion = fechaatencion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public String toString() {
        return "Atenciones{" +
                "detallecontrol='" + detallecontrol + '\'' +
                ", tipoplaga='" + tipoplaga + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", acciones='" + acciones + '\'' +
                ", fechaatencion='" + fechaatencion + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", fotos='" + fotos + '\'' +
                ", idusuario='" + idusuario + '\'' +
                '}';
    }
}