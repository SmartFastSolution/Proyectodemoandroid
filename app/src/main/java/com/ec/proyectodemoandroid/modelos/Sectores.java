package com.ec.proyectodemoandroid.modelos;

public class Sectores {

    private long idsector;
    private String detallesector;

    public Sectores(String detallesector) {
        this.detallesector = detallesector;
    }

    public Sectores(String detallesector, long idsector) {
        this.idsector = idsector;
        this.detallesector = detallesector;
    }

    public long getIdsector() {
        return idsector;
    }

    public void setIdsector(long idsector) {
        this.idsector = idsector;
    }

    public String getDetallesector() {
        return detallesector;
    }

    public void setDetallesector(String detallesector) {
        this.detallesector = detallesector;
    }
}
