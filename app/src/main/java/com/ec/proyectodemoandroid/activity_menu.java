package com.ec.proyectodemoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Base64;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ec.proyectodemoandroid.controllers.AtencionesController;
import com.ec.proyectodemoandroid.modelos.Atenciones;
import com.ec.proyectodemoandroid.modelos.AtencionesPipe;
import com.ec.proyectodemoandroid.modulos.VolleyMultipartRequest;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

public class activity_menu extends AppCompatActivity {

    private static final String ROOT_URL = "https://proyectodemo.test/api/atencion/store";
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private Bitmap bitmap;
    private String filePath;

    private PieChart menupiechart;

    private List<Atenciones> listaAtenciones;
    private AdaptadorAtenciones adaptadorAtenciones;

    private List<AtencionesPipe> listaAtencionesPipe;
    private AtencionesController atencionesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listaAtenciones = new ArrayList<>();
        adaptadorAtenciones = new AdaptadorAtenciones(listaAtenciones);
        atencionesController = new AtencionesController(activity_menu.this);

        menupiechart = findViewById(R.id.activity_menu_piechart);
        setupPieChart();
        loadPieChartData();
    }

    private void setupPieChart() {
        menupiechart.setDrawHoleEnabled(true);
        menupiechart.setUsePercentValues(true);
        menupiechart.setEntryLabelTextSize(12);
        menupiechart.setEntryLabelColor(Color.BLACK);
        menupiechart.setCenterText("Tipos de Plagas Identificadas");
        menupiechart.setCenterTextSize(24);
        menupiechart.getDescription().setEnabled(false);

        Legend l = menupiechart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {

        ArrayList<PieEntry> entries = new ArrayList<>();
        listaAtencionesPipe = atencionesController.obtenerAtencionesEstadistica();
        for (AtencionesPipe item: listaAtencionesPipe) {
            float val = Float.parseFloat(String.valueOf(item.getContador()));
            entries.add(new PieEntry(val, item.getTipoplaga()));
        }

        //entries.add(new PieEntry(0.15f, "Medical"));
        //entries.add(new PieEntry(0.10f, "Entertainment"));
        //entries.add(new PieEntry(0.25f, "Electricity and Gas"));
        //entries.add(new PieEntry(0.3f, "Housing"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Categoría de Plagas");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(menupiechart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        menupiechart.setData(data);
        menupiechart.invalidate();

        menupiechart.animateY(1400, Easing.EaseInOutQuad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.commonmenus  , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.mmDataBase){
            //p_descagaDataBase(getLc_usuario());
        }

        if(id==R.id.mnBuscar){
            startActivity(new Intent(activity_menu.this, activity_atenciones.class));
        }

        if(id==R.id.mnLectura){
            startActivity(new Intent(activity_menu.this, activity_capturadata.class));
        }

        if(id==R.id.mnSendData){
            p_enviarInformacion();
        }

        return super.onOptionsItemSelected(item);

    }


    private void p_enviarInformacion(){
        if (adaptadorAtenciones == null) return;
        listaAtenciones = atencionesController.obtenerAtenciones("estado", "0", true);
        Toast.makeText(activity_menu.this, "Se enviará "+ listaAtenciones.size() + " registros al servidor", Toast.LENGTH_SHORT).show();
        for (Atenciones item: listaAtenciones) {

        }

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, ROOT_URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                // parse success output
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {


        };

        //VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);

    }



    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageByte = outputStream.toByteArray();
        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }

}