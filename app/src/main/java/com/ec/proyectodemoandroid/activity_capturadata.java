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

import com.ec.proyectodemoandroid.controllers.AtencionesController;
import com.ec.proyectodemoandroid.controllers.TipoPlagaController;
import com.ec.proyectodemoandroid.modelos.Atenciones;
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
    private Spinner listTipoAtencion;
    private TextView txtDetalleControl, txtObservacionesControl, txtAccionesControl;

    protected LocationManager locationManager;
    private String lc_lat, lc_lon;
    private String mCurrentPhotoPath = "", lc_nom = "", outputData = "", lc_idAtencion = "", lc_fotos = "";

    private List<TipoPlaga> listaTipoPlaga;
    private TipoPlagaController tipoPlagaController;

    private AtencionesController atencionesController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturadata);

        atencionesController = new AtencionesController(activity_capturadata.this);

        lblGPSRun = (TextView) findViewById(R.id.lblGps);

        listTipoAtencion = (Spinner) findViewById(R.id.listTipoAtencion);
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

        String idAtencion = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idAtencion = extras.getString("idAtencion");
        }
        lc_idAtencion = idAtencion;

        tipoPlagaController = new TipoPlagaController(activity_capturadata.this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        lblGPSRun.setFocusable(true);

        p_cargarListas();

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String tipoplaga =  listTipoAtencion.getSelectedItem().toString();
        String detallecontrol = txtDetalleControl.getText().toString();
        String observaciones = txtObservacionesControl.getText().toString();
        String acciones = txtAccionesControl.getText().toString();
        String fechaatencion = sdf.format(new Date());
        Atenciones oInsert = new Atenciones(detallecontrol, tipoplaga, observaciones, acciones, fechaatencion, lc_lat, lc_lon, "", "1");
        atencionesController.nuevaAtencion(oInsert);
        finish();
        return;
    }

    private void p_cargarListas(){
        listaTipoPlaga = tipoPlagaController.obtenerTipoPlaga();
        List<String> sourceTipoTarea =  new ArrayList<>();
        for (TipoPlaga item: listaTipoPlaga) {
            sourceTipoTarea.add(item.getDetalleplaga());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sourceTipoTarea);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listTipoAtencion.setAdapter(adapter);
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