package com.dd.newqazaqalphabet.seabis.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dd.newqazaqalphabet.seabis.R;

/**
 * Created by dds86 on 12-Nov-17.
 */

public class CreateDatabase {
    private String[] arrayCyrillic2;
    private String[] arrayLatin2;
    private String[] arrayDiacritics2;
    private String[] arraySaebiz2;

    public CreateDatabase(String[] arrayCyrillic2, String[] arrayLatin2, String[] arrayDiacritics2, String[] arraySaebiz2) {
        this.arrayCyrillic2 = arrayCyrillic2;
        this.arrayLatin2 = arrayLatin2;
        this.arrayDiacritics2 = arrayDiacritics2;
        this.arraySaebiz2 = arraySaebiz2;
    }

    public void createDatabaseFirstTimeAppLaunch(Context context) {
        final String LOG_TAG = "autolog";
        DBHelper dbHelper;

        dbHelper = new DBHelper(context);

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // проверка существования записей

        // создаем объект для данных
        ContentValues cv = new ContentValues();


        Log.d(LOG_TAG, "--- Insert in mytable: ---");
        for (int i = 0; i < arrayCyrillic2.length; i++) {
            cv.put("cyrillic", arrayCyrillic2[i]);
            cv.put("latin", arrayLatin2[i]);
            cv.put("saebiz", arraySaebiz2[i]);
            cv.put("diacritic", arrayDiacritics2[i]);
            db.insert(context.getString(R.string.mytable), null, cv);

        }

        dbHelper.close();

    }
}
