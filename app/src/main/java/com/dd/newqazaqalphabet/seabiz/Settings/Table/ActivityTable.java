package com.dd.newqazaqalphabet.seabiz.Settings.Table;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.dd.newqazaqalphabet.seabiz.Database.DBHelper;
import com.dd.newqazaqalphabet.seabiz.R;
import com.dd.newqazaqalphabet.seabiz.Settings.Table.EditDiacteric.ActivityEditDiacritic;

import java.util.ArrayList;
import java.util.List;

public class ActivityTable extends AppCompatActivity {


    private ImageView mImageViewEdit;
    ListView listView;
    Parcelable state = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Восстанавливаем тему после лого
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        setTitle(getString(R.string.activity_table_title_actionbar));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataToTable();
        if (state != null) {

            listView.onRestoreInstanceState(state);

        }

    }

    void loadDataToTable() {
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


        listView = (ListView) findViewById(R.id.listViewAlphabet);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), ActivityEditDiacritic.class);
                intent.putExtra("position", String.valueOf(position + 1));

                //Сохраняем текущую позицию, when we return, listview will be returned to previous state
                Parcelable state = listView.onSaveInstanceState();
                intent.putExtra("currentListviewPosition", state);

                //Запускаем новый активити
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            state = data.getExtras().getParcelable("currentListviewPosition");

        }
    }
}
