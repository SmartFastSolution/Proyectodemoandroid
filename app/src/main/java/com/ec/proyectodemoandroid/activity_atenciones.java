package com.ec.proyectodemoandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ec.proyectodemoandroid.controllers.AtencionesController;
import com.ec.proyectodemoandroid.modelos.Atenciones;
import com.ec.proyectodemoandroid.modelos.Sectores;
import com.ec.proyectodemoandroid.modelos.TipoPlaga;

import java.util.ArrayList;
import java.util.List;

public class activity_atenciones extends AppCompatActivity{

    private RecyclerView recyclerView;

    private ArrayAdapter<String> adapter;
    private Spinner listEstados;
    private List<Atenciones> listaAtenciones;
    private AdaptadorAtenciones adaptadorAtenciones;
    private AtencionesController atencionesController;

    private TextView txtCriterio;
    private TextWatcher t_txt = null;

    private int cg_estados = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atenciones);

        atencionesController = new AtencionesController(activity_atenciones.this);

        recyclerView = findViewById(R.id.atencion_list);

        txtCriterio = (TextView) findViewById(R.id.txtCriterio);

        listEstados =  (Spinner) findViewById(R.id.listEstadosBusqueda);

        listaAtenciones = new ArrayList<>();
        adaptadorAtenciones = new AdaptadorAtenciones(listaAtenciones);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorAtenciones);

        p_cargarListas();
        //refrescarListaDeAtenciones();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView,
                new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Atenciones filaSeleccionada = listaAtenciones.get(position);
                Intent intent = new Intent(activity_atenciones.this, activity_capturadata.class);
                String idAte = String.valueOf(filaSeleccionada.getId());
                intent.putExtra("idAtencion", idAte);
                startActivity(intent);
            }
        }));



        t_txt = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                p_buscar(txtCriterio.getText().toString());
            }
        };
        txtCriterio.addTextChangedListener(t_txt);

        getSupportActionBar().setTitle("Lista de Atenciones");
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    private void refrescarListaDeAtenciones() {
        /*
         * ==========
         * Justo aquí obtenemos la lista de la BD
         * y se la ponemos al RecyclerView
         * ============
         *
         * */
        if (adaptadorAtenciones == null) return;

        listaAtenciones = atencionesController.obtenerAtenciones();
        cg_estados++;
        Toast.makeText(activity_atenciones.this, "Todas las atenciones", Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("RESULTADO DE BUSQUEDA: " + listaAtenciones.size() + " encontrados");
        adaptadorAtenciones.setListaDeOrdenes(listaAtenciones);
        adaptadorAtenciones.notifyDataSetChanged();
    }

    private void p_buscar(String p_valor){
        if (adaptadorAtenciones == null) return;
        listaAtenciones = atencionesController.obtenerAtenciones("tipoplaga", p_valor, true);
        if(listaAtenciones.size()==0)
            listaAtenciones = atencionesController.obtenerAtenciones("fechaatencion", p_valor, true);
        
        getSupportActionBar().setTitle("RESULTADO DE BUSQUEDA: " + listaAtenciones.size() + " encontrados");
        adaptadorAtenciones.setListaDeOrdenes(listaAtenciones);
        adaptadorAtenciones.notifyDataSetChanged();
    }

    private void p_buscarXestado(String p_valor){
        if (adaptadorAtenciones == null) return;
        listaAtenciones = atencionesController.obtenerAtenciones("estado", p_valor, true);
        getSupportActionBar().setTitle("RESULTADO DE BUSQUEDA: " + listaAtenciones.size() + " encontrados");
        adaptadorAtenciones.setListaDeOrdenes(listaAtenciones);
        adaptadorAtenciones.notifyDataSetChanged();
    }

    private void p_cargarListas(){
        List<String> sourceEstados =  new ArrayList<>();
        sourceEstados.add("Todos");
        sourceEstados.add("Detectado");
        sourceEstados.add("En Seguimiento");
        sourceEstados.add("Solucionado");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sourceEstados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listEstados.setAdapter(adapter);

        listEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    if(item.toString().equals("Todos"))
                        refrescarListaDeAtenciones();
                    else
                        p_buscarXestado(item.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

    }



}