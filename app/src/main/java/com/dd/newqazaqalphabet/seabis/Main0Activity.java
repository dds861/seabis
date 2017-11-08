package com.dd.newqazaqalphabet.seabis;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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


        //Находим все компоненты в activity
        initView();

        //Admob code
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
        mAdView = (AdView) findViewById(R.id.adView);
        mSpinnerFirstState = (Spinner) findViewById(R.id.spinnerFirstState);
        mIvChangeButtonspinner = (ImageView) findViewById(R.id.ivChangeButtonspinner);
        mIvChangeButtonspinner.setOnClickListener(this);
        mSpinnerSecondState = (Spinner) findViewById(R.id.spinnerSecondState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:


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
        String s3 = getString(R.string.diacritic);
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


    //Обрабатываем нажатие всех компонентов
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivDelete:
                //эффект нажатия на кнопку action_clean
                YoYo.with(Techniques.FadeOut)
                        .duration(150)
                        .repeat(0)
                        .playOn(findViewById(R.id.ivDelete));

                YoYo.with(Techniques.FadeIn)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.ivDelete));
                //edittext равно Пустота
                mEtUpEditText.setText("");
                mTvDown.setText("");
                break;
            case R.id.ivInsert:
                //эффект нажатия на кнопку action_insert
                YoYo.with(Techniques.FadeOut)
                        .duration(150)
                        .repeat(0)
                        .playOn(findViewById(R.id.ivInsert));
                YoYo.with(Techniques.FadeIn)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.ivInsert));

                //Вставляем в edittext - текст с буфера обмена
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String oldText = mEtUpEditText.getText().toString();
                String newText = clipboard.getPrimaryClip().getItemAt(0).getText().toString();

                mEtUpEditText.setText(oldText + newText);
                break;
            case R.id.ivCopyAll:
                YoYo.with(Techniques.FadeOut)
                        .duration(150)
                        .repeat(0)
                        .playOn(findViewById(R.id.ivCopyAll));
                YoYo.with(Techniques.FadeIn)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.ivCopyAll));

                // Gets a handle to the clipboard service.
                ClipboardManager clipboard2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                // Creates a new text clip to put on the clipboard
                ClipData clip = ClipData.newPlainText("simple text", mTvDown.getText().toString());

                // Set the clipboard's primary clip.
                clipboard2.setPrimaryClip(clip);
                Toast.makeText(this, R.string.TextCopied, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivShare:
                YoYo.with(Techniques.FadeOut)
                        .duration(150)
                        .repeat(0)
                        .playOn(findViewById(R.id.ivShare));
                YoYo.with(Techniques.FadeIn)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.ivShare));

                String shareBody = mTvDown.getText().toString();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_using)));
                break;
            case R.id.ivChangeButtonspinner://кнопка когда меняется местами спиннер

                //получаем название diacritic и app_name из strings.xml
                String diacritic = getString(R.string.diacritic);
                String saebiz = getString(R.string.app_name);

                //получаем текущий item в спиннере 2 (mSpinnerSecondState)
                String resultSecondState = mSpinnerSecondState.getSelectedItem().toString();

                if (!resultSecondState.equals(diacritic) && !resultSecondState.equals(saebiz)) {

                    //эффект нажатия на кнопку ivChangeButton
                    YoYo.with(Techniques.RotateOut)
                            .duration(350)
                            .repeat(0)
                            .playOn(findViewById(R.id.ivChangeButtonspinner));
                    YoYo.with(Techniques.RotateIn)
                            .duration(350)
                            .repeat(0)
                            .playOn(findViewById(R.id.ivChangeButtonspinner));

                    //эффект нажатия на textview mTvFirstState
                    YoYo.with(Techniques.SlideInLeft)
                            .duration(350)
                            .repeat(0)
                            .playOn(findViewById(R.id.spinnerFirstState));
                    YoYo.with(Techniques.SlideInRight)
                            .duration(350)
                            .repeat(0)
                            .playOn(findViewById(R.id.spinnerFirstState));

                    //эффект нажатия на textview mTvSecondState
                    YoYo.with(Techniques.SlideInRight)
                            .duration(350)
                            .repeat(0)
                            .playOn(findViewById(R.id.spinnerSecondState));
                    YoYo.with(Techniques.SlideInLeft)
                            .duration(350)
                            .repeat(0)
                            .playOn(findViewById(R.id.spinnerSecondState));

                }
                else {
                    //эффект нажатия на если выбраны diacritic и saebiz
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(0)
                            .playOn(findViewById(R.id.spinnerSecondState));


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
            String sentTextCatchedConverted = new Converter().cyrillicToLatin(mEtUpEditText.getText().toString());
            // mTvDown присваем текст который приобразовали в латиницу
            mTvDown.setText(sentTextCatchedConverted);
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
        String diacritic = getString(R.string.diacritic);
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
        String diacritic = getString(R.string.diacritic);
        String saebiz = getString(R.string.app_name);

        String result = null;
        //Проверяем какой стоит выбор
        //в зависимости от выбора будет происходить вызов соответствующего метода
        //для приобразования текста
        //  Действие если cyrillic To Latin
        if (textFirstSpinner.equals(cyrillic) && textSecondSpinner.equals(latin)) {

            result = new Converter().cyrillicToLatin(mEtUpEditText.getText().toString());
        }
        //  Действие если cyrillic To cyrillic
        else if (textFirstSpinner.equals(cyrillic) && textSecondSpinner.equals(cyrillic)) {
            result = mEtUpEditText.getText().toString();
        }
        //  Действие если cyrillic To Acute
        else if (textFirstSpinner.equals(cyrillic) && textSecondSpinner.equals(diacritic)) {
            result = new Converter().cyrillicToAcute(mEtUpEditText.getText().toString());
        }
        //  Действие если cyrillic To Saebiz
        else if (textFirstSpinner.equals(cyrillic) && textSecondSpinner.equals(saebiz)) {
            result = new Converter().cyrillicToSaebiz(mEtUpEditText.getText().toString());
        }
        //  Действие если latin To latin
        else if (textFirstSpinner.equals(latin) && textSecondSpinner.equals(latin)) {
            result = mEtUpEditText.getText().toString();
        }
        //  Действие если latin To Cyrillic
        else if (textFirstSpinner.equals(latin) && textSecondSpinner.equals(cyrillic)) {
            result = new Converter().latinToCyrillic(mEtUpEditText.getText().toString());
        }
        //  Действие если latin To Acute
        else if (textFirstSpinner.equals(latin) && textSecondSpinner.equals(diacritic)) {
            result = new Converter().latinToAcute(mEtUpEditText.getText().toString());
        }
        //  Действие если latin To Saebiz
        else if (textFirstSpinner.equals(latin) && textSecondSpinner.equals(saebiz)) {
            result = new Converter().latinToSaebiz(mEtUpEditText.getText().toString());
        }

        //Присваем mTvSecondState уже преобразованный текст
        mTvDown.setText(result);
    }

}
