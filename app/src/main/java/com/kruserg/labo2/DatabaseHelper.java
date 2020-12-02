package com.kruserg.labo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String ASTRE_TABLE = "ASTRE_TABLE";
    public static final String COLUMN_ASTRE_ID = "ID";
    public static final String COLUMN_ASTRE_TAILLE = "TAILLE";
    public static final String COLUMN_ASTRE_COULEUR = "COULEUR";
    public static final String COLUMN_ASTRE_STATUS = "STATUS";
    public static final String COLUMN_ASTRE_IMAGE = "IMAGE";
    public static final String COLUMN_ASTRE_NOM = "ASTRE_NOM";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "astre.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ASTRE_TABLE + " (" + COLUMN_ASTRE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ASTRE_NOM + " TEXT, " + COLUMN_ASTRE_TAILLE + " INTEGER, " + COLUMN_ASTRE_COULEUR + " INTEGER, " + COLUMN_ASTRE_STATUS + " BOOLEAN, " + COLUMN_ASTRE_IMAGE + " TEXT) ";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(AstreModel astreModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ASTRE_NOM, astreModel.getNom());
        cv.put(COLUMN_ASTRE_TAILLE,astreModel.getTaille());
        cv.put(COLUMN_ASTRE_COULEUR,astreModel.getCouleur());
        cv.put(COLUMN_ASTRE_STATUS,astreModel.isStatus());
        cv.put(COLUMN_ASTRE_IMAGE,astreModel.getImage());

        long insert = db.insert(ASTRE_TABLE, null, cv);
        //  db.close();
        if(insert == -1){

            return false; } else {
            return true; }
    }

    List<AstreModel> listAstre(){
        List<AstreModel> astreList = new ArrayList<>();

        String selectStatement = "SELECT * FROM " + ASTRE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectStatement,null);

        if(cursor.moveToFirst()){
            do{
                int astreID = cursor.getInt(0);
                String astreNom = cursor.getString(1);
                int astreTaille = cursor.getInt(2);
                String astreCouleur = cursor.getString(3);
                boolean astreStatus = cursor.getInt(4) == 1;
                String astreImage = cursor.getString(5);

                AstreModel astre = new AstreModel(astreID,astreNom,astreTaille,astreCouleur,astreStatus,astreImage);
                astreList.add(astre);

            } while (cursor.moveToNext());
        }else{
            System.out.println("Select failed");
        }
        cursor.close();
        //db.close();
        return astreList;
    }

}
