package com.hexagonalgames.annuaire;

import android.content.Context;
import android.database.Cursor;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Gestion de la base de données
 *
 * Cette classe hérite de SQLiteAssetHelper, qui n'est pas une classe de la bibliothèque standart.
 * Pour l'ajouter, il a fallu indiquer dans build.gradle du module où la trouver grace aux lignes:
 * suivantes :
 *
 * dependencies {
 *     compile 'com.readystatesoftware.sqliteasset:sqliteassethelper:2.0.1'
 * }
 *
 * Pour éviter de charger plusieurs fois la base dans chaque activité de l'application, on utilise
 * un singleton (db), auquel on accède par getDBHelper() au lieu du constructeur
 *
 *
 * Created by dubois on 22/01/17.
 */
public class DBHelper extends SQLiteAssetHelper {

    // nom de la base (dans assets/databases )
    private static final String DATABASE_NAME = "annuaire.sqlite";
    private static final int DATABASE_VERSION = 1;

    // Requètes SQL. Les paramètres sont à remplacer par ?
    private static final String GET_ALL_QUERY = "SELECT _id, nom, prenom, mail FROM personne;";
    private static final String GET_PERSONNE_BY_ID = "SELECT _id, nom, prenom, mail FROM personne WHERE _id=?;";


    // Utilisation d'un singleton pour éviter d'avoir plusieurs instances de cette classe.
    private static DBHelper db = null;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Obtenir le singleton
    public static synchronized DBHelper getDBHelper(Context context){
        if (db == null) { // Si c'est le premier appel,
            db = new DBHelper(context); // alors on crée la base
        }
        return db;
    }

    public Cursor getAll(){
        return getReadableDatabase().rawQuery(GET_ALL_QUERY,null);
    }

    public Cursor getPersonneByID(long id){
        return getReadableDatabase().rawQuery(GET_PERSONNE_BY_ID,
                new String[]{Long.toString(id)} // Les paramètres de la requète sont passés comme un
                // tableau de String. Il faut donc convertir l'entier long, d'où le Long.toString()
        );
    }
}
