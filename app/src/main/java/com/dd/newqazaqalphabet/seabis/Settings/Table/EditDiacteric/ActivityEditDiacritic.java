package com.dd.newqazaqalphabet.seabis.Settings.Table.EditDiacteric;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dd.newqazaqalphabet.seabis.Database.DBHelper;
import com.dd.newqazaqalphabet.seabis.R;

import java.util.ArrayList;

public class ActivityEditDiacritic extends AppCompatActivity {


    private Spinner mSpinnerFirstState;
    private GridView mGvMain;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Восстанавливаем тему после лого
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diacritic);
        initView();
        setupSpinner();

        mGvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(ActivityEditDiacritic.this, adapter.getItem(position), Toast.LENGTH_SHORT).show();

                DBHelper dbHelper = new DBHelper(getApplicationContext());

                // создаем объект для данных
                ContentValues cv = new ContentValues();

                // подключаемся к БД
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Log.d("autologs", "--- Update mytable: ---");
                // подготовим значения для обновления
                cv.put("diacritic", adapter.getItem(position));

                String position_ = getIntent().getStringExtra("position");

                // обновляем по id
                int updCount = db.update(getString(R.string.mytable), cv, "id = ?",
                        new String[]{position_});
                Log.d("autologs", "updated rows count = " + updCount);
                dbHelper.close();

                Intent intent = new Intent();
                intent.putExtra("currentListviewPosition",getIntent().getExtras().getParcelable("currentListviewPosition"));
                setResult(RESULT_OK, intent);
                finish();
            }

        });


    }


    void setupSpinner() {
        String unicode1BasicLatinASCII = getString(R.string.unicode1BasicLatinASCII);
        String unicode2Latin1Supplement = getString(R.string.unicode2Latin1Supplement);
        String unicode3LatinExtendedA = getString(R.string.unicode3LatinExtendedA);
        String unicode4LatinExtendedB = getString(R.string.unicode4LatinExtendedB);
        String unicode5LatinExtendedC = getString(R.string.unicode5LatinExtendedC);
        String unicode6LatinExtendedD = getString(R.string.unicode6LatinExtendedD);
        String unicode7LatinExtendedE = getString(R.string.unicode7LatinExtendedE);
        String unicode8LatinExtendedAdditional = getString(R.string.unicode8LatinExtendedAdditional);
        String unicode9LatinLigatures = getString(R.string.unicode9LatinLigatures);
        String unicode10FullwidthLatinLetters = getString(R.string.unicode10FullwidthLatinLetters);
        String unicode11IpaExtensions = getString(R.string.unicode11IpaExtensions);
        String unicode12PhoneticExtensions = getString(R.string.unicode12PhoneticExtensions);
        String unicode13PhoneticExtensionsSupplement = getString(R.string.unicode13PhoneticExtensionsSupplement);

        final ArrayList<String> arrayListFirstSpinner = new ArrayList<>();
        arrayListFirstSpinner.add(unicode1BasicLatinASCII);
        arrayListFirstSpinner.add(unicode2Latin1Supplement);
        arrayListFirstSpinner.add(unicode3LatinExtendedA);
        arrayListFirstSpinner.add(unicode4LatinExtendedB);
        arrayListFirstSpinner.add(unicode5LatinExtendedC);
        arrayListFirstSpinner.add(unicode6LatinExtendedD);
        arrayListFirstSpinner.add(unicode7LatinExtendedE);
        arrayListFirstSpinner.add(unicode8LatinExtendedAdditional);
        arrayListFirstSpinner.add(unicode9LatinLigatures);
        arrayListFirstSpinner.add(unicode10FullwidthLatinLetters);
        arrayListFirstSpinner.add(unicode11IpaExtensions);
        arrayListFirstSpinner.add(unicode12PhoneticExtensions);
        arrayListFirstSpinner.add(unicode13PhoneticExtensionsSupplement);

        // адаптер
        ArrayAdapter<String> adapterFirstSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListFirstSpinner);
        adapterFirstSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerFirstState.setAdapter(adapterFirstSpinner);
        // заголовок
        mSpinnerFirstState.setPrompt("Title");
        // по умолчанию ставим кириллицу
        mSpinnerFirstState.setSelection(2);

        //Обрабатываем нажатие каждого item  в спиннере
        mSpinnerFirstState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String[] data;

                switch (position) {
                    case 0:
                        data = getResources().getStringArray(R.array.unicode1BasicLatinASCII);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);

                        break;
                    case 1:
                        data = getResources().getStringArray(R.array.unicode2Latin1Supplement);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 2:
                        data = getResources().getStringArray(R.array.unicode3LatinExtendedA);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 3:
                        data = getResources().getStringArray(R.array.unicode4LatinExtendedB);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 4:
                        data = getResources().getStringArray(R.array.unicode5LatinExtendedC);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 5:
                        data = getResources().getStringArray(R.array.unicode6LatinExtendedD);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 6:
                        data = getResources().getStringArray(R.array.unicode7LatinExtendedE);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 7:
                        data = getResources().getStringArray(R.array.unicode8LatinExtendedAdditional);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 8:
                        data = getResources().getStringArray(R.array.unicode9LatinLigatures);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 9:
                        data = getResources().getStringArray(R.array.unicode10FullwidthLatinLetters);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 10:
                        data = getResources().getStringArray(R.array.unicode11IpaExtensions);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 11:
                        data = getResources().getStringArray(R.array.unicode12PhoneticExtensions);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    case 12:
                        data = getResources().getStringArray(R.array.unicode13PhoneticExtensionsSupplement);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_edit_diacritic_item, R.id.tvText, data);
                        mGvMain.setAdapter(adapter);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void initView() {
        mSpinnerFirstState = (Spinner) findViewById(R.id.spinnerFirstState);
        mGvMain = (GridView) findViewById(R.id.gvMain);
    }


}

