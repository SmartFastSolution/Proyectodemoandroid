package com.ec.proyectodemoandroid.modelos;

public class AtencionesPipe {

    private String tipoplaga;
    private long contador;

    public AtencionesPipe(String tipoplaga, long contador) {
        this.tipoplaga = tipoplaga;
        this.contador = contador;
    }

    public String getTipoplaga() {
        return tipoplaga;
    }

    public void setTipoplaga(String tipoplaga) {
        this.tipoplaga = tipoplaga;
    }

    public long getContador() {
        return contador;
    }

    public void setContador(long contador) {
        this.contador = contador;
    }


}
