package com.ec.proyectodemoandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelpDataBase extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DE_DATOS = "demo_db",
            NOMBRE_TABLA_ATENCIONES = "atenciones",
            NOMBRE_TABLA_TIPOPLAGA = "tipoplaga",
            NOMBRE_TABLA_USUARIOS = "usuario",
            NOMBRE_TABLA_SECTORES = "sector";

    private static final int VERSION_BASE_DE_DATOS = 1;

    public HelpDataBase(Context context) {
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(idatencion integer primary key autoincrement, idtipoplaga int, tipoplaga text, idsector int, detallecontrol text, observacion text, acciones text, fechaatencion text, latitud text, longitud text, fotos text, idusuario integer, estado text)",
                NOMBRE_TABLA_ATENCIONES));
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(idplaga integer, detalleplaga text)",
                NOMBRE_TABLA_TIPOPLAGA));
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(idusuario integer, nombres text, usuario text, clave text)",
                NOMBRE_TABLA_USUARIOS));
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(idsector integer, detallesector text)",
                NOMBRE_TABLA_SECTORES));

        String ROW1 = "INSERT INTO " + NOMBRE_TABLA_USUARIOS + " (idusuario, nombres, usuario, clave) Values (1, 'JOSUE MERINO', 'jmerino', '1234');";
        db.execSQL(ROW1);

        ROW1  = "INSERT INTO " + NOMBRE_TABLA_TIPOPLAGA + " (idplaga, detalleplaga) Values (1,'Chilo Supressalis');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_TIPOPLAGA + " (idplaga, detalleplaga) Values (2,'Rosquillas');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_TIPOPLAGA + " (idplaga, detalleplaga) Values (3,'Pudenta');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_TIPOPLAGA + " (idplaga, detalleplaga) Values (4,'Piricularia');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_TIPOPLAGA + " (idplaga, detalleplaga) Values (5,'Malas hierbas');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_TIPOPLAGA + " (idplaga, detalleplaga) Values (6,'Agallador');";
        db.execSQL(ROW1);

        ROW1  = "INSERT INTO " + NOMBRE_TABLA_SECTORES + " (idsector, detallesector) Values (1,'Sector 1');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_SECTORES + " (idsector, detallesector) Values (2,'Sector 2');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_SECTORES + " (idsector, detallesector) Values (3,'Sector 3');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_SECTORES + " (idsector, detallesector) Values (3,'Sector 4');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_SECTORES + " (idsector, detallesector) Values (3,'Sector 5');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_SECTORES + " (idsector, detallesector) Values (3,'Sector 6');";
        db.execSQL(ROW1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+NOMBRE_TABLA_ATENCIONES);
        db.execSQL("DROP TABLE IF EXISTS "+NOMBRE_TABLA_TIPOPLAGA);
        db.execSQL("DROP TABLE IF EXISTS "+NOMBRE_TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS "+NOMBRE_TABLA_SECTORES);
        onCreate(db);
    }
}
