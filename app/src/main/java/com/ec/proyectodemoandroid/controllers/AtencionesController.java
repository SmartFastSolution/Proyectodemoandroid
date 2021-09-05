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
        //idtipoplaga, idsector, detallecontrol, observacion, acciones, fechaatencion, latitud, longitud, fotos, idusuario
        valoresParaInsertar.put("idtipoplaga", o.getIdtipoplaga());
        valoresParaInsertar.put("tipoplaga", o.getTipoplaga());
        valoresParaInsertar.put("idsector", o.getIdsector());
        valoresParaInsertar.put("detallecontrol", o.getDetallecontrol());
        valoresParaInsertar.put("observacion", o.getObservaciones());
        valoresParaInsertar.put("acciones", o.getAcciones());
        valoresParaInsertar.put("fechaatencion", o.getFechaatencion());
        valoresParaInsertar.put("latitud", o.getLatitud());
        valoresParaInsertar.put("longitud", o.getLongitud());
        valoresParaInsertar.put("fotos", o.getFotos());
        valoresParaInsertar.put("idusuario", o.getIdusuario());
        valoresParaInsertar.put("estado", o.getEstado());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public long actualizarAtencion(Atenciones o) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        //idtipoplaga, idsector, detallecontrol, observacion, acciones, fechaatencion, latitud, longitud, fotos, idusuario
        valoresParaInsertar.put("idtipoplaga", o.getIdtipoplaga());
        valoresParaInsertar.put("tipoplaga", o.getTipoplaga());
        valoresParaInsertar.put("idsector", o.getIdsector());
        valoresParaInsertar.put("detallecontrol", o.getDetallecontrol());
        valoresParaInsertar.put("observacion", o.getObservaciones());
        valoresParaInsertar.put("acciones", o.getAcciones());
        valoresParaInsertar.put("fechaatencion", o.getFechaatencion());
        valoresParaInsertar.put("latitud", o.getLatitud());
        valoresParaInsertar.put("longitud", o.getLongitud());
        valoresParaInsertar.put("fotos", o.getFotos());
        valoresParaInsertar.put("idusuario", o.getIdusuario());
        valoresParaInsertar.put("estado", o.getEstado());
        String whereClause = "idatencion=?";
        String[] whereArgs = new String [] { String.valueOf(o.getId()) };

        return baseDeDatos.update(NOMBRE_TABLA, valoresParaInsertar,whereClause, whereArgs);
    }

    public ArrayList<Atenciones> obtenerAtenciones(String p_columna, String p_valor, boolean p_like) {
        ArrayList<Atenciones> items = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDatos.getReadableDatabase();
        //idtipoplaga, idsector, detallecontrol, observacion, acciones, fechaatencion, latitud, longitud, fotos, idusuario
        String[] columnasAConsultar = {"idtipoplaga", "tipoplaga", "idsector", "detallecontrol", "observacion", "acciones", "fechaatencion", "latitud", "longitud", "fotos", "idusuario", "estado", "idatencion"};

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
            //idtipoplaga, idsector, detallecontrol, observacion, acciones, fechaatencion, latitud, longitud, fotos, idusuario
            String idtipoplagaDB = String.valueOf(cursor.getLong(0));
            //String tipoplagaDB = String.valueOf(cursor.getLong(1));
            String tipoplagaDB = cursor.getString(1);
            String idsectorDB = String.valueOf(cursor.getLong(2));
            String detallecontrolDB = cursor.getString(3);
            String observacionesDB = cursor.getString(4);
            String accionesDB = cursor.getString(5);
            String fechaatencionDB = cursor.getString(6);
            String latitudDB = cursor.getString(7);
            String longitudDB = cursor.getString(8);
            String fotosDB = cursor.getString(9);
            String idusuarioDB = cursor.getString(10);
            String estadoDB = cursor.getString(11);
            long idatencionDB = cursor.getLong(12);
            Atenciones objItems = new Atenciones(idtipoplagaDB, tipoplagaDB, idsectorDB, detallecontrolDB, observacionesDB, accionesDB, fechaatencionDB, latitudDB, longitudDB, fotosDB, idusuarioDB, estadoDB, idatencionDB);
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
        //idtipoplaga, tipoplaga, idsector, detallecontrol, observacion, acciones, fechaatencion, latitud, longitud, fotos, idusuario
        String[] columnasAConsultar = {"idtipoplaga", "tipoplaga", "idsector", "detallecontrol", "observacion", "acciones", "fechaatencion", "latitud", "longitud", "fotos", "idusuario", "estado", "idatencion"};

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
            //idtipoplaga, idsector, detallecontrol, observacion, acciones, fechaatencion, latitud, longitud, fotos, idusuario
            String idtipoplagaDB = String.valueOf(cursor.getLong(0));
            String tipoplagaDB = cursor.getString(1);
            String idsectorDB = String.valueOf(cursor.getLong(2));
            String detallecontrolDB = cursor.getString(3);
            String observacionesDB = cursor.getString(4);
            String accionesDB = cursor.getString(5);
            String fechaatencionDB = cursor.getString(6);
            String latitudDB = cursor.getString(7);
            String longitudDB = cursor.getString(8);
            String fotosDB = cursor.getString(9);
            String idusuarioDB = cursor.getString(10);
            String estadoDB = cursor.getString(11);
            long idatencionDB = cursor.getLong(12);
            Atenciones objItems = new Atenciones(idtipoplagaDB, tipoplagaDB, idsectorDB, detallecontrolDB, observacionesDB, accionesDB, fechaatencionDB, latitudDB, longitudDB, fotosDB, idusuarioDB, estadoDB, idatencionDB);
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
