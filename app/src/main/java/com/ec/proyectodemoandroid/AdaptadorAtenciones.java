package com.ec.proyectodemoandroid;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ec.proyectodemoandroid.modelos.Atenciones;

import java.util.List;

public class AdaptadorAtenciones extends RecyclerView.Adapter<AdaptadorAtenciones.MyViewHolder>{

    private List<Atenciones> listaAtenciones;

    public void setListaDeOrdenes(List<Atenciones> listaAtenciones) {
        this.listaAtenciones = listaAtenciones;
    }

    public AdaptadorAtenciones(List<Atenciones> obj) {
        this.listaAtenciones = obj;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaItem= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_atencion, viewGroup, false);
        return new MyViewHolder(filaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener nuestra lista gracias al índice i
        Atenciones obj = listaAtenciones.get(i);
        // Obtener los datos de la lista
        String tipoplaga = obj.getTipoplaga();
        String detallecontrol = obj.getDetallecontrol();
        String fechaatencion = obj.getEstado() + " " + obj.getFechaatencion();
        float id = obj.getId();
        // Y poner a los TextView los datos con setText
        myViewHolder.tipoplaga.setText(tipoplaga);
        myViewHolder.detallecontrol.setText(detallecontrol);
        myViewHolder.fechaatencion.setText(fechaatencion);
    }

    @Override
    public int getItemCount() {
        return listaAtenciones.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tipoplaga, detallecontrol, fechaatencion;

        MyViewHolder(View itemView) {
            super(itemView);
            this.tipoplaga = itemView.findViewById(R.id.txtTipoplagaitem);
            this.detallecontrol = itemView.findViewById(R.id.txtDetallecontrolitem);
            this.fechaatencion = itemView.findViewById(R.id.txtFechaatencionitem);
        }
    }
}
