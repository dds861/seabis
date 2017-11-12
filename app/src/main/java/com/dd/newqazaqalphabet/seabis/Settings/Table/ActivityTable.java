package com.dd.newqazaqalphabet.seabis.Settings.Table;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.dd.newqazaqalphabet.seabis.Database.DBHelper;
import com.dd.newqazaqalphabet.seabis.R;
import com.dd.newqazaqalphabet.seabis.Settings.Table.EditDiacteric.ActivityEditDiacritic;

import java.util.ArrayList;
import java.util.List;

public class ActivityTable extends AppCompatActivity {


    private ImageView mImageViewEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Восстанавливаем тему после лого
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);




    }
    @Override
    protected void onResume() {
        super.onResume();
        loadDataToTable();

    }

    void loadDataToTable(){
        DBHelper dbHelper = new DBHelper(this);

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(getString(R.string.mytable), null, null, null, null, null, null);

        List<TableProduct> products = new ArrayList<>();

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int ColIndex1 = c.getColumnIndex("cyrillic");
            int ColIndex2 = c.getColumnIndex("latin");
            int ColIndex3 = c.getColumnIndex("saebiz");
            int ColIndex4 = c.getColumnIndex("diacritic");

            do {
                String s1 = c.getString(ColIndex1);
                String s2 = c.getString(ColIndex2);
                String s3 = c.getString(ColIndex3);
                String s4 = c.getString(ColIndex4);

                products.add(new TableProduct(s1, s2, s3, s4));

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("autologs", "0 rows");
        c.close();
        dbHelper.close();

        TableAdapter myAdapter = new TableAdapter(getApplicationContext(), products);

        ListView listView = (ListView) findViewById(R.id.listViewAlphabet);


        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), ActivityEditDiacritic.class);
                intent.putExtra("position", String.valueOf(position+1));
                Log.i("autolog", "view.getTag(): " + position);
                startActivity(intent);
            }
        });

    }
}
