package com.tuapp.plantasmedicinales.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PlantasDB";
    private static final int DATABASE_VERSION = 1;

    // Tabla de historial
    private static final String TABLE_HISTORY = "history";
    private static final String COL_ID = "id";
    private static final String COL_PLANT_NAME = "plant_name";
    private static final String COL_CONFIDENCE = "confidence";
    private static final String COL_IMAGE_PATH = "image_path";
    private static final String COL_DATE = "date";
    private static final String COL_USERNAME = "username";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createHistoryTable = "CREATE TABLE " + TABLE_HISTORY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PLANT_NAME + " TEXT NOT NULL, " +
                COL_CONFIDENCE + " REAL, " +
                COL_IMAGE_PATH + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_USERNAME + " TEXT)";
        db.execSQL(createHistoryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    // Agregar identificación al historial
    public long addIdentification(String plantName, float confidence, String imagePath, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", new Locale("es", "ES"));
        String currentDate = sdf.format(new Date());

        values.put(COL_PLANT_NAME, plantName);
        values.put(COL_CONFIDENCE, confidence);
        values.put(COL_IMAGE_PATH, imagePath);
        values.put(COL_DATE, currentDate);
        values.put(COL_USERNAME, username);

        long id = db.insert(TABLE_HISTORY, null, values);
        db.close();
        return id;
    }

    // Obtener todo el historial de un usuario
    public List<HistoryItem> getHistory(String username) {
        List<HistoryItem> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_HISTORY +
                      " WHERE " + COL_USERNAME + " = ?" +
                      " ORDER BY " + COL_ID + " DESC";

        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            do {
                HistoryItem item = new HistoryItem(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PLANT_NAME)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(COL_CONFIDENCE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE))
                );
                historyList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return historyList;
    }

    // Obtener estadísticas del usuario
    public UserStats getUserStats(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserStats stats = new UserStats();

        // Total de identificaciones
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_HISTORY +
                           " WHERE " + COL_USERNAME + " = ?";
        Cursor countCursor = db.rawQuery(countQuery, new String[]{username});
        if (countCursor.moveToFirst()) {
            stats.totalIdentifications = countCursor.getInt(0);
        }
        countCursor.close();

        // Última identificación
        String lastQuery = "SELECT " + COL_DATE + " FROM " + TABLE_HISTORY +
                          " WHERE " + COL_USERNAME + " = ?" +
                          " ORDER BY " + COL_ID + " DESC LIMIT 1";
        Cursor lastCursor = db.rawQuery(lastQuery, new String[]{username});
        if (lastCursor.moveToFirst()) {
            stats.lastIdentification = lastCursor.getString(0);
        }
        lastCursor.close();

        // Planta más identificada
        String favQuery = "SELECT " + COL_PLANT_NAME + ", COUNT(*) as count FROM " + TABLE_HISTORY +
                         " WHERE " + COL_USERNAME + " = ?" +
                         " GROUP BY " + COL_PLANT_NAME +
                         " ORDER BY count DESC LIMIT 1";
        Cursor favCursor = db.rawQuery(favQuery, new String[]{username});
        if (favCursor.moveToFirst()) {
            stats.favoritePlant = favCursor.getString(0);
        }
        favCursor.close();

        db.close();
        return stats;
    }

    // Eliminar una identificación del historial
    public boolean deleteIdentification(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_HISTORY, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Borrar todo el historial de un usuario
    public boolean clearHistory(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_HISTORY, COL_USERNAME + " = ?", new String[]{username});
        db.close();
        return result > 0;
    }

    // Clase para items del historial
    public static class HistoryItem {
        public int id;
        public String plantName;
        public float confidence;
        public String imagePath;
        public String date;

        public HistoryItem(int id, String plantName, float confidence, String imagePath, String date) {
            this.id = id;
            this.plantName = plantName;
            this.confidence = confidence;
            this.imagePath = imagePath;
            this.date = date;
        }
    }

    // Clase para estadísticas del usuario
    public static class UserStats {
        public int totalIdentifications = 0;
        public String lastIdentification = "Ninguna";
        public String favoritePlant = "-";
    }
}
