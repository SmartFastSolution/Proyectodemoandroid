package com.ec.proyectodemoandroid.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ec.proyectodemoandroid.HelpDataBase;
import com.ec.proyectodemoandroid.modelos.Atenciones;
import com.ec.proyectodemoandroid.modelos.TipoPlaga;

import java.util.ArrayList;

public class TipoPlagaController {

    private HelpDataBase ayudanteBaseDatos;
    private String NOMBRE_TABLA = "tipoplaga";

    public TipoPlagaController(Context contexto) {
        ayudanteBaseDatos = new HelpDataBase(contexto);
    }

    public ArrayList<TipoPlaga> obtenerTipoPlaga() {
        ArrayList<TipoPlaga> items = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDatos.getReadableDatabase();
        String[] columnasAConsultar = {"detalleplaga", "idplaga"};

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
            String detalleplagaDB = cursor.getString(0);
            long idplagaDB = cursor.getLong(1);

            TipoPlaga objItems = new TipoPlaga(detalleplagaDB, idplagaDB);
            items.add(objItems);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista :)
        cursor.close();
        return items;
    }

}
