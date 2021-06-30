package com.ec.proyectodemoandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ec.proyectodemoandroid.controllers.AtencionesController;
import com.ec.proyectodemoandroid.controllers.SectoresController;
import com.ec.proyectodemoandroid.controllers.TipoPlagaController;
import com.ec.proyectodemoandroid.modelos.Atenciones;
import com.ec.proyectodemoandroid.modelos.Sectores;
import com.ec.proyectodemoandroid.modelos.TipoPlaga;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.core.content.FileProvider.getUriForFile;

public class activity_capturadata extends AppCompatActivity implements LocationListener {

    private TextView lblGPSRun;

    private Button bntConfirmar, bntFotos;
    private Spinner listTipoAtencion, listSectores;
    private TextView txtDetalleControl, txtObservacionesControl, txtAccionesControl;

    protected LocationManager locationManager;
    private String lc_lat, lc_lon;
    private String mCurrentPhotoPath = "", lc_nom = "", outputData = "", lc_idAtencion = "", lc_fotos = "", lc_estado = "0";

    private List<TipoPlaga> listaTipoPlaga;
    private List<Sectores> listaSectores;
    private List<Atenciones> listaAtenciones;

    private TipoPlagaController tipoPlagaController;
    private SectoresController sectoresController;
    private AtencionesController atencionesController;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturadata);

        atencionesController = new AtencionesController(activity_capturadata.this);
        tipoPlagaController = new TipoPlagaController(activity_capturadata.this);
        sectoresController = new SectoresController(activity_capturadata.this);

        lblGPSRun = (TextView) findViewById(R.id.lblGps);

        listTipoAtencion = (Spinner) findViewById(R.id.listTipoAtencion);
        listSectores = (Spinner) findViewById(R.id.listSectores);
        bntConfirmar = findViewById(R.id.bntConfirmar);
        bntFotos = findViewById(R.id.bntFotos);


        txtDetalleControl = (TextView) findViewById(R.id.txtDetalleControl);
        txtObservacionesControl = (TextView) findViewById(R.id.txtObservacionesControl);
        txtAccionesControl = (TextView) findViewById(R.id.txtAccionesControl);

        bntConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_confirmarClic();
            }
        });
        bntFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_fotos();
            }
        });

        p_cargarListas();

        String idAtencion = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idAtencion = extras.getString("idAtencion");
            p_cargarAtencion(idAtencion);
        }
        lc_idAtencion = idAtencion;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        lblGPSRun.setFocusable(true);

        getSupportActionBar().setTitle("Captura de información");
    }

    private void p_confirmarClic(){

        AlertDialog dialog = new AlertDialog
                .Builder(activity_capturadata.this)
                .setPositiveButton("Sí, continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        p_confirmarLogica();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle("Confirmar")
                .setMessage("¿Desea confirmar los datos a enviar?")
                .create();
        dialog.show();
    }

    private void p_confirmarLogica(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String idtipoplaga = String.valueOf(listTipoAtencion.getSelectedItemId()+1);
            String tipoplaga = listTipoAtencion.getSelectedItem().toString();
            String idsector = String.valueOf(listSectores.getSelectedItemId()+1);
            String detallecontrol = txtDetalleControl.getText().toString();
            String observaciones = txtObservacionesControl.getText().toString();
            String acciones = txtAccionesControl.getText().toString();
            String fechaatencion = sdf.format(new Date());


            String ls_lat = "";
            try{ls_lat = lblGPSRun.getText().toString().equals("")? "" : lblGPSRun.getText().toString().split(";")[0].replace("GPS:","").trim();}catch (Exception e){ls_lat = "";}
            String ls_lon = "";
            try{ls_lon = lblGPSRun.getText().toString().equals("")? "" : lblGPSRun.getText().toString().split(";")[1].trim(); }catch (Exception e){ls_lon = "";}

            if(lc_idAtencion.equals("")){
                Atenciones oInsert = new Atenciones(idtipoplaga, tipoplaga, idsector, detallecontrol, observaciones, acciones, fechaatencion, ls_lat, ls_lon, lc_fotos,"1", "0");
                atencionesController.nuevaAtencion(oInsert);
            }else if(lc_estado.equals("0")){
                Atenciones oUpdate = new Atenciones(idtipoplaga, tipoplaga, idsector, detallecontrol, observaciones, acciones, fechaatencion, ls_lat, ls_lon, lc_fotos, "1", "1", Long.valueOf(lc_idAtencion));
                atencionesController.actualizarAtencion(oUpdate);
            }else{
                Toast.makeText(activity_capturadata.this, "No es posible modificar atenciones enviadas, contacte con el coordinador", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(activity_capturadata.this, "Información almacenada", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(activity_capturadata.this, activity_menu.class));
            return;
        }catch (Exception ex){
            Toast.makeText(activity_capturadata.this, "Revisar la informaciónnformación", Toast.LENGTH_SHORT).show();
        }
    }

    private void p_cargarListas(){
        listaTipoPlaga = tipoPlagaController.obtenerTipoPlaga();
        List<String> sourceTipoTarea =  new ArrayList<>();
        for (TipoPlaga item: listaTipoPlaga) {
            sourceTipoTarea.add(item.getDetalleplaga());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sourceTipoTarea);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listTipoAtencion.setAdapter(adapter);

        listaSectores = sectoresController.obtenerSector();
        List<String> sourceSector =  new ArrayList<>();
        for (Sectores item: listaSectores) {
            sourceSector.add(item.getDetallesector());
        }
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sourceSector);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listSectores.setAdapter(adapter1);
    }

    private void p_cargarAtencion(String id){
        listaAtenciones = atencionesController.obtenerAtenciones("idtipoplaga", id, false);

        String idtipoatencion = listaAtenciones.get(0).getIdtipoplaga().toString();
        this.listTipoAtencion.setSelection(Integer.parseInt(idtipoatencion)-1);

        String idsector = listaAtenciones.get(0).getIdsector().toString();
        this.listSectores.setSelection(Integer.parseInt(idsector)-1);

        this.txtDetalleControl.setText(listaAtenciones.get(0).getDetallecontrol().toString());
        this.txtObservacionesControl.setText(listaAtenciones.get(0).getObservaciones().toString());
        this.txtAccionesControl.setText(listaAtenciones.get(0).getAcciones().toString());
        lc_fotos = listaAtenciones.get(0).getFotos().toString();
        lc_estado = listaAtenciones.get(0).getEstado().toString();
    }

    /*Metodos para tomar fotos*/

    public void p_fotos() {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                lc_fotos += photoFile.toString().split("/")[9] + ";";
                Uri photoURI = getUriForFile(getBaseContext(),
                        "com.example.android.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /*Matodos de Localizacion*/

    @Override
    public void onLocationChanged(Location location) {
        //SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        lc_lat = location.getLatitude() + "";
        lc_lon = location.getLongitude() + "";
        lblGPSRun.setText("GPS: " + lc_lat + "  ;  " + lc_lon);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}