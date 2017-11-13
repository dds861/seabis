package com.dd.newqazaqalphabet.seabiz;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.newqazaqalphabet.seabiz.Database.CreateDatabase;
import com.dd.newqazaqalphabet.seabiz.Database.DBHelper;
import com.dd.newqazaqalphabet.seabiz.Settings.Table.ActivityTable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class Main0Activity extends AppCompatActivity implements View.OnClickListener {


    private EditText mEtUpEditText;
    final String SAVED_TEXT_FIRST = "saved_text";
    final String SAVED_TEXT_SECOND = "saved_text_down";
    final String SELECTED_STATE_FIRST = "selected_state_first";
    final String SELECTED_STATE_SECOND = "selected_state_second";
    SharedPreferences sPref;

    //Admob code parameters
    private AdView mAdView;

    private TextView mTvDown;
    private ImageView mIvDelete;
    private ImageView mIvInsert;
    private ImageView mIvCopyAll;
    private ImageView mIvShare;
    private Spinner mSpinnerFirstState;
    private ImageView mIvChangeButtonspinner;
    private Spinner mSpinnerSecondState;
    ArrayAdapter<String> adapterFirstSpinner;
    ArrayAdapter<String> adapterSecondSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Восстанавливаем тему после лого
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main0);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        if (c.getCount() == 0) {
            new CreateDatabase().createDatabaseFirstTimeAppLaunch(getApplicationContext());
        }
        c.close();
        dbHelper.close();

        //Находим все компоненты в activity
        initView();

        //Admob code
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Logo and app name on action bar
        setLogoAndAppName();


        //mEtUpEditText text change listener. Что будет происходить после вставки текста
        mEtUpEditTextListener();

        //Обрабатываем первый спиннер R.id.spinnerFirstState
        spinnerFirstState();

        //Обрабатываем второй спиннер R.id.spinnerFirstState
        spinnerSecondState();


    }


    @Override
    protected void onResume() {
        super.onResume();

        //Обрабатываем с текстом который прислали с другой программы
        //или восстанавливаем предыдущий текст
        restoreTextsOnViews();
    }

    //Находим все компоненты в activity
    private void initView() {
        mEtUpEditText = (EditText) findViewById(R.id.etUpEditText);

        mTvDown = (TextView) findViewById(R.id.tvDown);
        mIvDelete = (ImageView) findViewById(R.id.ivDelete);
        mIvDelete.setOnClickListener(this);
        mIvInsert = (ImageView) findViewById(R.id.ivInsert);
        mIvInsert.setOnClickListener(this);
        mIvCopyAll = (ImageView) findViewById(R.id.ivCopyAll);
        mIvCopyAll.setOnClickListener(this);
        mIvShare = (ImageView) findViewById(R.id.ivShare);
        mIvShare.setOnClickListener(this);

        mSpinnerFirstState = (Spinner) findViewById(R.id.spinnerFirstState);
        mIvChangeButtonspinner = (ImageView) findViewById(R.id.ivChangeButtonspinner);
        mIvChangeButtonspinner.setOnClickListener(this);
        mSpinnerSecondState = (Spinner) findViewById(R.id.spinnerSecondState);
    }

    //Создается кнопка Settings справо вверхнем углу на action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    //Создается меню на кнопке settings на Action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case R.id.action_my_alphabet:
                Intent intent2 = new Intent(getApplicationContext(), ActivityTable.class);
                startActivity(intent2);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //Обработка спиннера spinnerFirstState
    private void spinnerFirstState() {

        String s1 = getString(R.string.cyrillic);
        String s2 = getString(R.string.latin);

        final ArrayList<String> arrayListFirstSpinner = new ArrayList<>();
        arrayListFirstSpinner.add(s1);
        arrayListFirstSpinner.add(s2);

        // адаптер
        adapterFirstSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListFirstSpinner);
        adapterFirstSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerFirstState.setAdapter(adapterFirstSpinner);
        // заголовок
        mSpinnerFirstState.setPrompt("Title");
        // по умолчанию ставим кириллицу
        mSpinnerFirstState.setSelection(0);


        //Обрабатываем нажатие каждого item  в спиннере
        mSpinnerFirstState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                convertTextFromUpToDown(mSpinnerFirstState.getSelectedItem().toString(), mSpinnerSecondState.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //Обработка спиннера spinnerSecondState
    private void spinnerSecondState() {

        String s1 = getString(R.string.cyrillic);
        String s2 = getString(R.string.latin);
        String s3 = getString(R.string.myAlphabet);
        String s4 = getString(R.string.app_name);

        ArrayList<String> arrayListSecondSpinner = new ArrayList<>();
        arrayListSecondSpinner.add(s1);
        arrayListSecondSpinner.add(s2);
        arrayListSecondSpinner.add(s3);
        arrayListSecondSpinner.add(s4);


        // адаптер
        adapterSecondSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListSecondSpinner);
        adapterSecondSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mSpinnerSecondState.setAdapter(adapterSecondSpinner);
        // заголовок
        mSpinnerSecondState.setPrompt("Title");
        // по умолчанию ставим Латиницу
        mSpinnerSecondState.setSelection(1);

        //Обрабатываем нажатие каждого item  в спиннере
        mSpinnerSecondState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                convertTextFromUpToDown(mSpinnerFirstState.getSelectedItem().toString(), mSpinnerSecondState.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //mEtUpEditText text change listener. Что будет происходить после вставки текста
    private void mEtUpEditTextListener() {
        mEtUpEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //преобразования текста при написании что либо на mEtUpEditText
                convertTextFromUpToDown(mSpinnerFirstState.getSelectedItem().toString(), mSpinnerSecondState.getSelectedItem().toString());

            }
        });
    }


    void makeAnimationOnView(int resourceId, Techniques techniques, int duration, int repeat) {
        YoYo.with(techniques)
                .duration(duration)
                .repeat(repeat)
                .playOn(findViewById(resourceId));

    }

    //Обрабатываем нажатие всех компонентов
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivDelete:
                //эффект нажатия на кнопку
                makeAnimationOnView(R.id.ivDelete, Techniques.FadeOut, 150, 0);
                makeAnimationOnView(R.id.ivDelete, Techniques.FadeIn, 350, 0);

                //edittext равно Пустота
                mEtUpEditText.setText("");
                mTvDown.setText("");
                break;
            case R.id.ivInsert:
                //эффект нажатия на кнопку
                makeAnimationOnView(R.id.ivInsert, Techniques.FadeOut, 150, 0);
                makeAnimationOnView(R.id.ivInsert, Techniques.FadeIn, 350, 0);

                //Вставляем в edittext - текст с буфера обмена
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String oldText = mEtUpEditText.getText().toString();
                String newText = clipboard.getPrimaryClip().getItemAt(0).getText().toString();

                mEtUpEditText.setText(oldText + newText);
                break;
            case R.id.ivCopyAll:
                //эффект нажатия на кнопку
                makeAnimationOnView(R.id.ivCopyAll, Techniques.FadeOut, 150, 0);
                makeAnimationOnView(R.id.ivCopyAll, Techniques.FadeIn, 350, 0);

                // Gets a handle to the clipboard service.
                ClipboardManager clipboard2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                // Creates a new text clip to put on the clipboard
                ClipData clip = ClipData.newPlainText("simple text", mTvDown.getText().toString());

                // Set the clipboard's primary clip.
                clipboard2.setPrimaryClip(clip);
                Toast.makeText(this, R.string.TextCopied, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivShare:
                //эффект нажатия на кнопку
                makeAnimationOnView(R.id.ivShare, Techniques.FadeOut, 150, 0);
                makeAnimationOnView(R.id.ivShare, Techniques.FadeIn, 350, 0);

                String shareBody = mTvDown.getText().toString();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_using)));
                break;
            case R.id.ivChangeButtonspinner://кнопка когда меняется местами спиннер

                //получаем название diacritic и app_name из strings.xml
                String diacritic = getString(R.string.myAlphabet);
                String saebiz = getString(R.string.app_name);

                //получаем текущий item в спиннере 2 (mSpinnerSecondState)
                String resultSecondState = mSpinnerSecondState.getSelectedItem().toString();

                if (!resultSecondState.equals(diacritic) && !resultSecondState.equals(saebiz)) {

                    //эффект нажатия на кнопку ivChangeButton
//                    makeAnimationOnView(R.id.ivChangeButtonspinner, Techniques.RotateOut, 750, 0);
                    makeAnimationOnView(R.id.ivChangeButtonspinner, Techniques.RotateIn, 700, 0);


                    //эффект нажатия на textview mTvFirstState
                    makeAnimationOnView(R.id.spinnerFirstState, Techniques.SlideInLeft, 350, 0);
                    makeAnimationOnView(R.id.spinnerFirstState, Techniques.SlideInRight, 350, 0);


                    //эффект нажатия на textview mTvSecondState
                    makeAnimationOnView(R.id.spinnerSecondState, Techniques.SlideInRight, 350, 0);
                    makeAnimationOnView(R.id.spinnerSecondState, Techniques.SlideInLeft, 350, 0);

                } else {
                    //эффект нажатия на если выбраны diacritic и saebiz
                    makeAnimationOnView(R.id.spinnerSecondState, Techniques.Shake, 700, 0);

                }
                //Меняем местами название First and Second textviews
                changePlacesSpinnerCyrillicLatin();

                //проверяем пустой ли текст в mEtUpEditText
                if (mEtUpEditText == null || mEtUpEditText.equals(null) || mEtUpEditText.equals("")) {
                    break;
                }

                //преобразования текста при нажатии этой кнопки
                convertTextFromUpToDown(mSpinnerFirstState.getSelectedItem().toString(), mSpinnerSecondState.getSelectedItem().toString());


                break;
            default:
                break;
        }
    }


    //Logo and app name on action bar
    private void setLogoAndAppName() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_carrot2);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    //Обрабатываем с текстом который прислали с другой программы
    //или восстанавливаем предыдущий текст
    void restoreTextsOnViews() {
        //Обрабатываем с текстом который прислали с другой программы
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        //Если ничего не присылали, а программау просто открыли тогда восстанавливаем ранее сохраненный текст
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        } else {
            // Если мы просто запускаем приложение тогда восстановиться текст который ранее был

            //Восстановиться состояние текста в edittext
            loadTextFirstEdittext();

            //Восстановиться состояние спиннера
            loadSpinnerState();

            //Восстановиться состояние текста в textview
            loadTextSecondTextview();
        }

    }

    //Обрабатываем с текстом который прислали с другой программы
    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        //Проверяем не пустой ли текст
        if (sharedText != null) {
            // mEtUpEditText присваем текст который прислали
            mEtUpEditText.setText(sharedText);
            // sentTextCatchedConverted присваем текст который приобразовали в латиницу
            convertTextFromUpToDown(mSpinnerFirstState.getSelectedItem().toString(), mSpinnerSecondState.getSelectedItem().toString());
        }
    }

    //Если вышли из приложения, состояние текста в приложении сохраниться
    @Override
    protected void onPause() {
        super.onPause();

        //Сохраниться состояние текста в edittext
        saveTextFirstEdittext();

        //Сохраниться состояние "Кириллица" и "латиница"
        saveSpinnerState();

        //Сохраниться состояние текста в textview
        saveTextSecondTextview();
    }

    //save text from edittext, when we return from previous activity text will be restored
    void saveTextFirstEdittext() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        //сохраняем текст снизу ключу SAVED_TEXT_FIRST
        ed.putString(SAVED_TEXT_FIRST, mEtUpEditText.getText().toString());
        ed.apply();
    }

    //load text from edittext, when we return from previous activity text will be restored
    void loadTextFirstEdittext() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT_FIRST, "");
        mEtUpEditText.setText(savedText);

    }

    //save text from edittext, when we return from previous activity text will be restored
    void saveTextSecondTextview() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        //сохраняем текст снизу ключу SAVED_TEXT_SECOND
        ed.putString(SAVED_TEXT_SECOND, mTvDown.getText().toString());
        ed.apply();
    }

    //load text from edittext, when we return from previous activity text will be restored
    void loadTextSecondTextview() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT_SECOND, "");
        mTvDown.setText(savedText);

    }

    //save text from edittext, when we return from previous activity text will be restored
    void saveSpinnerState() {
        //получаем текущую позицию спиннеров
        int SelectedItemPositionFirstState = mSpinnerFirstState.getSelectedItemPosition();
        int SelectedItemPositionSecondState = mSpinnerSecondState.getSelectedItemPosition();

        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        //сохраняем позиции спиннеров по ключам SELECTED_STATE_FIRST и SELECTED_STATE_SECOND
        ed.putInt(SELECTED_STATE_FIRST, SelectedItemPositionFirstState);
        ed.putInt(SELECTED_STATE_SECOND, SelectedItemPositionSecondState);

        //запускаем сохранение
        ed.apply();
    }

    //load text from edittext, when we return from previous activity text will be restored
    void loadSpinnerState() {
        sPref = getPreferences(MODE_PRIVATE);

        //получаем сохраненный текст по ключам  SELECTED_STATE_FIRST и SELECTED_STATE_SECOND
        int SelectedItemPositionFirstState = sPref.getInt(SELECTED_STATE_FIRST, 0);
        int SelectedItemPositionSecondState = sPref.getInt(SELECTED_STATE_SECOND, 1);

        //устанавливаем спиннерам сохраненные значения
        mSpinnerFirstState.setSelection(SelectedItemPositionFirstState);
        mSpinnerSecondState.setSelection(SelectedItemPositionSecondState);
    }

    //Меняем местами название First and Second textviews
    void changePlacesSpinnerCyrillicLatin() {
        //получаем название diacritic и app_name из strings.xml
        String diacritic = getString(R.string.myAlphabet);
        String saebiz = getString(R.string.app_name);

        //получаем текущий item в спиннере 2 (mSpinnerSecondState)
        String resultSecondState = mSpinnerSecondState.getSelectedItem().toString();

        //Если mSpinnerSecondState не будут равняться diacritic и saebiz тогда продолжить
        if (!resultSecondState.equals(diacritic) && !resultSecondState.equals(saebiz)) {

            //Получаем какой item выбран данный момент
            int posititionSpinnerFirstState = mSpinnerFirstState.getSelectedItemPosition();
            int posititionSpinnerSecondState = mSpinnerSecondState.getSelectedItemPosition();

            //меняем местами спиннеры, т.е. первому даем состояние второго, второму - первое
            mSpinnerFirstState.setSelection(posititionSpinnerSecondState);
            mSpinnerSecondState.setSelection(posititionSpinnerFirstState);
        }
    }

    //Здесь происходит преобразование вставленного текста
    void convertTextFromUpToDown(String textFirstSpinner, String textSecondSpinner) {

        String latin = getString(R.string.latin);
        String cyrillic = getString(R.string.cyrillic);
        String diacritic = getString(R.string.myAlphabet);
        String saebiz = getString(R.string.app_name);

        String result = null;
        SQLiteDatabase db;
        DBHelper dbHelper = new DBHelper(this);


        //получаем текст из edittext
        result = mEtUpEditText.getText().toString();

        // подключаемся к БД
        db = dbHelper.getReadableDatabase();

        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        // TODO надо будет изменить колонки которые надо вызывать т.е. не все колонки нам нужны
        Cursor c = db.query(getString(R.string.mytable), null, null, null, null, null, null);


        //Проверяем какой стоит выбор
        //в зависимости от выбора будет происходить вызов соответствующего метода
        //для приобразования текста

        //c.moveToFirst()
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false

        //  Действие если cyrillic To Latin
        if (c.moveToFirst() && textFirstSpinner.equals(cyrillic) && textSecondSpinner.equals(latin)) {
            // определяем номера столбцов по имени в выборке
            int idColIndex1 = c.getColumnIndex("cyrillic");
            int idColIndex2 = c.getColumnIndex("latin");

            do {
                // получаем значения по номерам столбцов и пишем в String s1 и s2
                String s1 = c.getString(idColIndex1);
                Log.i("autolog", "s1: " + s1);
                String s2 = c.getString(idColIndex2);
                Log.i("autolog", "s2: " + s2);

                if (!s1.isEmpty()) {
                    Log.i("autolog", "до result: " + result);
                    //заменяем все символы в тексте
                    result = result.replaceAll(s1, s2);
                    Log.i("autolog", "после result: " + result);
                }


                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());


        }
        //  Действие если cyrillic To cyrillic
        else if (c.moveToFirst() && textFirstSpinner.equals(cyrillic) && textSecondSpinner.equals(cyrillic)) {
            result = mEtUpEditText.getText().toString();
        }
        //  Действие если cyrillic To diacritic
        else if (c.moveToFirst() && textFirstSpinner.equals(cyrillic) && textSecondSpinner.equals(diacritic)) {
            // определяем номера столбцов по имени в выборке
            int idColIndex1 = c.getColumnIndex("cyrillic");
            int idColIndex2 = c.getColumnIndex("diacritic");

            do {
                // получаем значения по номерам столбцов и пишем в String s1 и s2
                String s1 = c.getString(idColIndex1);
                Log.i("autolog", "s1: " + s1);
                String s2 = c.getString(idColIndex2);
                Log.i("autolog", "s2: " + s2);
                Log.i("autolog", "до result: " + result);
                if (!s1.isEmpty()) {
                    //заменяем все символы в тексте
                    result = result.replaceAll(s1, s2);
                }

                Log.i("autolog", "после result: " + result);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());

        }
        //  Действие если cyrillic To Saebiz
        else if (c.moveToFirst() && textFirstSpinner.equals(cyrillic) && textSecondSpinner.equals(saebiz)) {
            // определяем номера столбцов по имени в выборке
            int idColIndex1 = c.getColumnIndex("cyrillic");
            int idColIndex2 = c.getColumnIndex("saebiz");

            do {
                // получаем значения по номерам столбцов и пишем в String s1 и s2
                String s1 = c.getString(idColIndex1);
                String s2 = c.getString(idColIndex2);

                if (!s1.isEmpty()) {
                    //заменяем все символы в тексте
                    result = result.replaceAll(s1, s2);
                }

                Log.i("a", "result: " + result);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());

        }
        //  Действие если latin To latin
        else if (c.moveToFirst() && textFirstSpinner.equals(latin) && textSecondSpinner.equals(latin)) {
            result = mEtUpEditText.getText().toString();
        }
        //  Действие если latin To Cyrillic
        else if (c.moveToFirst() && textFirstSpinner.equals(latin) && textSecondSpinner.equals(cyrillic)) {


            // определяем номера столбцов по имени в выборке
            int idColIndex1 = c.getColumnIndex("latin");
            Log.i("autolog", "idColIndex1: " + idColIndex1);
            int idColIndex2 = c.getColumnIndex("cyrillic");
            Log.i("autolog", "idColIndex2: " + idColIndex2);

            Integer[] positionsLatin = {69, 70, 81, 82, 17, 18, 83, 84,85, 86, 3, 4, 11, 12, 23, 24,
                    25, 26, 37, 38, 41, 42, 51, 52, 55, 56, 65, 66, 67, 68, 1, 2, 5, 6, 7, 8,
                    9, 10, 13, 14, 15, 16, 19, 20, 21, 22, 27, 28, 29, 30, 31, 32, 33, 34, 35,
                    36, 39, 40, 43, 44, 45, 46, 47, 48, 49, 50, 53, 54, 57, 58, 59, 60, 61, 62,
                    63, 64, 73, 74, 75, 76, 79, 80, 71, 72, 77, 78};

            for (int i = 0; i < positionsLatin.length; i++) {
                if (c.moveToPosition(positionsLatin[i])) {
                    //перемещаемся на позицию курсора в базе
                    ;

                    Log.i("autolog", "result до: " + result);

                    // получаем значения по номерам столбцов и пишем в String s1 и s2
                    String s1 = c.getString(idColIndex1);
                    Log.i("autolog", "s1: " + s1);
                    String s2 = c.getString(idColIndex2);
                    Log.i("autolog", "s2: " + s2);

                    if (!s1.isEmpty()) {
                        //заменяем все символы в тексте
                        result = result.replaceAll(s1, s2);
                    }

                    Log.i("autolog", "result после: " + result);
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false - выходим из цикла
                }


            }


        }
        //  Действие если latin To diacritic
        else if (c.moveToFirst() && textFirstSpinner.equals(latin) && textSecondSpinner.equals(diacritic)) {
            // определяем номера столбцов по имени в выборке
            int idColIndex1 = c.getColumnIndex("latin");
            int idColIndex2 = c.getColumnIndex("diacritic");

            Integer[] positionsLatin = {69, 70, 81, 82, 17, 18, 83, 84,85, 86, 3, 4, 11, 12, 23, 24,
                    25, 26, 37, 38, 41, 42, 51, 52, 55, 56, 65, 66, 67, 68, 1, 2, 5, 6, 7, 8,
                    9, 10, 13, 14, 15, 16, 19, 20, 21, 22, 27, 28, 29, 30, 31, 32, 33, 34, 35,
                    36, 39, 40, 43, 44, 45, 46, 47, 48, 49, 50, 53, 54, 57, 58, 59, 60, 61, 62,
                    63, 64, 73, 74, 75, 76, 79, 80, 71, 72, 77, 78};

            for (int i = 0; i < positionsLatin.length; i++) {
                if (c.moveToPosition(positionsLatin[i])) {
                    //перемещаемся на позицию курсора в базе
                    ;

                    Log.i("autolog", "result до: " + result);

                    // получаем значения по номерам столбцов и пишем в String s1 и s2
                    String s1 = c.getString(idColIndex1);
                    Log.i("autolog", "s1: " + s1);
                    String s2 = c.getString(idColIndex2);
                    Log.i("autolog", "s2: " + s2);

                    if (!s1.isEmpty()) {
                        //заменяем все символы в тексте
                        result = result.replaceAll(s1, s2);
                    }

                    Log.i("autolog", "result после: " + result);
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false - выходим из цикла
                }


            }
        }
        //  Действие если latin To Saebiz
        else if (c.moveToFirst() && textFirstSpinner.equals(latin) && textSecondSpinner.equals(saebiz)) {
            // определяем номера столбцов по имени в выборке
            int idColIndex1 = c.getColumnIndex("latin");
            int idColIndex2 = c.getColumnIndex("saebiz");

            Integer[] positionsLatin = {69, 70, 81, 82, 17, 18, 83, 84,85, 86, 3, 4, 11, 12, 23, 24,
                    25, 26, 37, 38, 41, 42, 51, 52, 55, 56, 65, 66, 67, 68, 1, 2, 5, 6, 7, 8,
                    9, 10, 13, 14, 15, 16, 19, 20, 21, 22, 27, 28, 29, 30, 31, 32, 33, 34, 35,
                    36, 39, 40, 43, 44, 45, 46, 47, 48, 49, 50, 53, 54, 57, 58, 59, 60, 61, 62,
                    63, 64, 73, 74, 75, 76, 79, 80, 71, 72, 77, 78};

            for (int i = 0; i < positionsLatin.length; i++) {
                if (c.moveToPosition(positionsLatin[i])) {
                    //перемещаемся на позицию курсора в базе
                    ;

                    Log.i("autolog", "result до: " + result);

                    // получаем значения по номерам столбцов и пишем в String s1 и s2
                    String s1 = c.getString(idColIndex1);
                    Log.i("autolog", "s1: " + s1);
                    String s2 = c.getString(idColIndex2);
                    Log.i("autolog", "s2: " + s2);

                    if (!s1.isEmpty()) {
                        //заменяем все символы в тексте
                        result = result.replaceAll(s1, s2);
                    }

                    Log.i("autolog", "result после: " + result);
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false - выходим из цикла
                }


            }
        }

        //Присваем mTvSecondState уже преобразованный текст
        mTvDown.setText(result);

        c.close();
        dbHelper.close();

    }

}
