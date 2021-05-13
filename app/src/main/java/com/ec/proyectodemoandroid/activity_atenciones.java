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
import android.widget.TextView;
import android.widget.Toast;

import com.ec.proyectodemoandroid.controllers.AtencionesController;
import com.ec.proyectodemoandroid.modelos.Atenciones;

import java.util.ArrayList;
import java.util.List;

public class activity_atenciones extends AppCompatActivity {

    private RecyclerView recyclerView;

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

        listaAtenciones = new ArrayList<>();
        adaptadorAtenciones = new AdaptadorAtenciones(listaAtenciones);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorAtenciones);

        refrescarListaDeAtenciones();

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

}