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

        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(idatencion integer primary key autoincrement, idtipoplaga int, tipoplaga text, idsector int, detallecontrol text, observacion text, acciones text, fechaatencion text, latitud text, longitud text, fotos text, idusuario integer)",
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

        ROW1  = "INSERT INTO " + NOMBRE_TABLA_SECTORES + " (idsector, detallesector) Values (1,'Sector 1');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_SECTORES + " (idsector, detallesector) Values (2,'Sector 2');";
        db.execSQL(ROW1);
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_SECTORES + " (idsector, detallesector) Values (3,'Sector 3');";
        db.execSQL(ROW1);

        /*
        ROW1  = "INSERT INTO " + NOMBRE_TABLA_ATENCIONES + " (idtipoplaga, tipoplaga, idsector, detallecontrol, observacion, acciones, fechaatencion, latitud, longitud, fotos, idusuario) " +
                        "Values (1,'Chilo Supressalis',1,'Primer control', 'Se detecta daño en los márgenes o lindes de los arrozales y zonas con mayor densidad de siembra', 'Recomendamos el uso Confirm® 240 LV. Es un insecticida formulado a base de Tebufenocida 247 g/l y preparado de forma autosuspensible, que le confiere resistencia al lavado', '08/05/2021', '-2.05709746', '-79.9930006192', '', 3);";
        db.execSQL(ROW1);

        ROW1  = "INSERT INTO " + NOMBRE_TABLA_ATENCIONES + " (idtipoplaga, tipoplaga, idsector, detallecontrol, observacion, acciones, fechaatencion, latitud, longitud, fotos, idusuario) " +
                "Values (2,'Rosquillas',2,'Segundo control', 'Se detecta daño en los márgenes o lindes de los arrozales y zonas con mayor densidad de siembra', 'Recomendamos el uso Confirm® 240 LV. Es un insecticida formulado a base de Tebufenocida 247 g/l y preparado de forma autosuspensible, que le confiere resistencia al lavado', '09/05/2021', '-2.057394089', '-79.9936275376', '', 3);";
        db.execSQL(ROW1);

        ROW1  = "INSERT INTO " + NOMBRE_TABLA_ATENCIONES + " (idtipoplaga, tipoplaga, idsector, detallecontrol, observacion, acciones, fechaatencion, latitud, longitud, fotos, idusuario) " +
                "Values (3,'Pudenta',3,'Primer control', 'Se ha observado ya su presencia en rabo de gato (Polypogon spp.) de las lindes, en focos y también sobre el cultivo de arroz', 'Es importante eliminar las poblaciones de rabo de gato de las orillas e interior de las parcelas, ya que de esta manera evitamos que la primera generación de Pudenta se complete y pase al arroz próximo', '10/05/2021', '-2.0568320615', '-79.9932232446', '', 3);";
        db.execSQL(ROW1);
        */




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
