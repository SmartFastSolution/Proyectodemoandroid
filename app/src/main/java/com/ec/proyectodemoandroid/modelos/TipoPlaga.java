package com.ec.proyectodemoandroid.modelos;

public class TipoPlaga {

    private long idplaga;
    private String detalleplaga;

    public TipoPlaga(String detalleplaga) {
        this.detalleplaga = detalleplaga;
    }

    public TipoPlaga(String detalleplaga, long idplaga) {
        this.idplaga = idplaga;
        this.detalleplaga = detalleplaga;
    }

    public long getIdplaga() {
        return idplaga;
    }

    public void setIdplaga(long idplaga) {
        this.idplaga = idplaga;
    }

    public String getDetalleplaga() {
        return detalleplaga;
    }

    public void setDetalleplaga(String detalleplaga) {
        this.detalleplaga = detalleplaga;
    }

    @Override
    public String toString() {
        return "TipoPlaga{" +
                "detalleplaga='" + detalleplaga + '\'' +
                '}';
    }

}
