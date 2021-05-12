package com.ec.proyectodemoandroid.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ec.proyectodemoandroid.HelpDataBase;
import com.ec.proyectodemoandroid.modelos.Atenciones;
import com.ec.proyectodemoandroid.modelos.AtencionesPipe;

import java.util.ArrayList;

public class AtencionesController {

    private HelpDataBase ayudanteBaseDatos;
    private String NOMBRE_TABLA = "atenciones";

    public AtencionesController(Context contexto) {
        ayudanteBaseDatos = new HelpDataBase(contexto);
    }

    public long nuevaAtencion(Atenciones o) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        //detallecontrol, tipoplaga, observacion, acciones, fechaatencion, latitud, longitud, fotos, idusuario
        valoresParaInsertar.put("detallecontrol", o.getDetallecontrol());
        valoresParaInsertar.put("tipoplaga", o.getTipoplaga());
        valoresParaInsertar.put("observacion", o.getObservaciones());
        valoresParaInsertar.put("acciones", o.getAcciones());
        valoresParaInsertar.put("fechaatencion", o.getFechaatencion());
        valoresParaInsertar.put("latitud", o.getLatitud());
        valoresParaInsertar.put("longitud", o.getLongitud());
        valoresParaInsertar.put("fotos", o.getLongitud());
        valoresParaInsertar.put("idusuario", o.getIdusuario());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public ArrayList<Atenciones> obtenerAtenciones(String p_columna, String p_valor, boolean p_like) {
        ArrayList<Atenciones> items = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDatos.getReadableDatabase();
        String[] columnasAConsultar = {"detallecontrol", "tipoplaga", "observacion", "acciones", "fechaatencion", "latitud", "longitud", "fotos", "idusuario", "idatencion"};

        String selection = p_columna + (p_like?" LIKE ? ":" = ? ");
        String[] whereClause={  p_like?"%"+ p_valor.toString() + "%":p_valor.toString() };

        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from
                columnasAConsultar,
                selection,
                whereClause,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return items;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return items;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista
        do {
            // El 0 es el número de la columna, como seleccionamos
            //"detallecontrol", "tipoplaga", "observacion", "acciones", "fechaatencion", "latitud", "longitud", "fotos", "idusuario", "idatencion"
            String detallecontrolDB = cursor.getString(0);
            String tipoplagaDB = cursor.getString(1);
            String observacionesDB = cursor.getString(2);
            String accionesDB = cursor.getString(3);
            String fechaatencionDB = cursor.getString(4);
            String latitudDB = cursor.getString(5);
            String longitudDB = cursor.getString(6);
            String fotosDB = cursor.getString(7);
            String idusuarioDB = cursor.getString(8);
            long idatencionDB = cursor.getLong(9);

            Atenciones objItems = new Atenciones(detallecontrolDB, tipoplagaDB, observacionesDB, accionesDB, fechaatencionDB, latitudDB, longitudDB, fotosDB, idusuarioDB, idatencionDB);
            items.add(objItems);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista :)
        cursor.close();
        return items;
    }

    public ArrayList<Atenciones> obtenerAtenciones() {
        ArrayList<Atenciones> items = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDatos.getReadableDatabase();
        String[] columnasAConsultar = {"detallecontrol", "tipoplaga", "observacion", "acciones", "fechaatencion", "latitud", "longitud", "fotos", "idusuario", "idatencion"};

        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return items;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return items;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista
        do {
            // El 0 es el número de la columna, como seleccionamos
            //"detallecontrol", "tipoplaga", "observacion", "acciones", "fechaatencion", "latitud", "longitud", "fotos", "idusuario", "idatencion"
            String detallecontrolDB = cursor.getString(0);
            String tipoplagaDB = cursor.getString(1);
            String observacionesDB = cursor.getString(2);
            String accionesDB = cursor.getString(3);
            String fechaatencionDB = cursor.getString(4);
            String latitudDB = cursor.getString(5);
            String longitudDB = cursor.getString(6);
            String fotosDB = cursor.getString(7);
            String idusuarioDB = cursor.getString(8);
            long idatencionDB = cursor.getLong(9);

            Atenciones objItems = new Atenciones(detallecontrolDB, tipoplagaDB, observacionesDB, accionesDB, fechaatencionDB, latitudDB, longitudDB, fotosDB, idusuarioDB, idatencionDB);
            items.add(objItems);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista :)
        cursor.close();
        return items;
    }

    public ArrayList<AtencionesPipe> obtenerAtencionesEstadistica() {
        ArrayList<AtencionesPipe> items = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDatos.getReadableDatabase();
        String[] columns = new String[] { "tipoplaga", "count(*)" };
        String groupBy = "tipoplaga";
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from
                columns,
                null,
                null,
                groupBy,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return items;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return items;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista
        do {
            // El 0 es el número de la columna, como seleccionamos
            //"detallecontrol", "tipoplaga", "observacion", "acciones", "fechaatencion", "latitud", "longitud", "fotos", "idusuario", "idatencion"
            String tipoplagaDB = cursor.getString(0);
            long idatencionDB = cursor.getLong(1);
            AtencionesPipe objItems = new AtencionesPipe(tipoplagaDB,idatencionDB);
            items.add(objItems);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista :)
        cursor.close();
        return items;
    }


}
