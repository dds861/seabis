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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Main0Activity extends AppCompatActivity implements View.OnClickListener {


    private EditText mEtUpEditText;
    private ScrollView mScrollViewEditText;
    final String SAVED_TEXT = "saved_text";
    final String SAVED_TEXT_DOWN = "saved_text_down";
    final String STATE_TEXT = "C2L";
    SharedPreferences sPref;
    String temp;
    private boolean state_cyr_to_lat = true;

    //Admob code parameters
    private static final String TAG = "MainActivity";
    private AdView mAdView;
    private TextView mTvFirstState;
    private ImageView mIvChangeButton;
    private TextView mTvSecondState;
    private TextView mTvDown;
    private ImageView mIvDelete;
    private ImageView mIvInsert;
    private ImageView mIvCopyAll;
    private ImageView mIvShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Восстанавливаем тему после лого
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main0);


        initView();


        //Admob code
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Make enable image with text on action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_carrot2);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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
            loadText();

            //Восстановиться состояние "Кириллица" и "латиница"
            loadState();

            //Восстановиться состояние текста в textview
            loadTextSecondTextview();
        }

        //Проверяем был ли текст ранее, если был, что восстанавливаем
        if (temp != null) {
            mEtUpEditText.setText(temp);
        }

        //mEtUpEditText text change listener. Что будет происходить после вставки текста
        mEtUpEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                convertTextFromUpToDown();
            }
        });
    }

    private void initView() {
        mEtUpEditText = (EditText) findViewById(R.id.etUpEditText);
        mScrollViewEditText = (ScrollView) findViewById(R.id.scrollViewEditText);

        mTvFirstState = (TextView) findViewById(R.id.tvFirstState);
        mIvChangeButton = (ImageView) findViewById(R.id.ivChangeButton);
        mIvChangeButton.setOnClickListener(this);
        mTvSecondState = (TextView) findViewById(R.id.tvSecondState);

        mTvDown = (TextView) findViewById(R.id.tvDown);
        mIvDelete = (ImageView) findViewById(R.id.ivDelete);
        mIvDelete.setOnClickListener(this);
        mIvInsert = (ImageView) findViewById(R.id.ivInsert);
        mIvInsert.setOnClickListener(this);
        mIvCopyAll = (ImageView) findViewById(R.id.ivCopyAll);
        mIvCopyAll.setOnClickListener(this);
        mIvShare = (ImageView) findViewById(R.id.ivShare);
        mIvShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivChangeButton:// TODO 17/11/04

                //эффект нажатия на кнопку ivChangeButton
                YoYo.with(Techniques.RotateOut)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.ivChangeButton));
                YoYo.with(Techniques.RotateIn)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.ivChangeButton));

                //эффект нажатия на textview mTvFirstState
                YoYo.with(Techniques.SlideInLeft)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.tvFirstState));
                YoYo.with(Techniques.SlideInRight)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.tvFirstState));

                //эффект нажатия на textview mTvSecondState
                YoYo.with(Techniques.SlideInRight)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.tvSecondState));
                YoYo.with(Techniques.SlideInLeft)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.tvSecondState));

                //Меняем состояние кнопки ivChangeButton
                if (state_cyr_to_lat) {
                    state_cyr_to_lat = false;
                } else {
                    state_cyr_to_lat = true;
                }
                //Меняем местами название First and Second textviews
                loadTextViewStates();

                //проверяем пустой ли текст в mEtUpEditText
                if (mEtUpEditText == null || mEtUpEditText.equals(null) || mEtUpEditText.equals("")) {
                    break;
                }
                convertTextFromUpToDown();


                break;
            case R.id.ivDelete:// TODO 17/11/04
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
            case R.id.ivInsert:// TODO 17/11/04
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
            case R.id.ivCopyAll:// TODO 17/11/04
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
            case R.id.ivShare:// TODO 17/11/04
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
            default:
                break;
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
        saveText();

        //Сохраниться состояние "Кириллица" и "латиница"
        saveState();

        //Сохраниться состояние текста в textview
        saveTextSecondTextview();
    }

    //save text from edittext, when we return from previous activity text will be restored
    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, mEtUpEditText.getText().toString());
        ed.apply();
    }

    //save text from edittext, when we return from previous activity text will be restored
    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        mEtUpEditText.setText(savedText);

    }

    //save text from edittext, when we return from previous activity text will be restored
    void saveTextSecondTextview() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT_DOWN, mTvDown.getText().toString());
        ed.apply();
    }

    //save text from edittext, when we return from previous activity text will be restored
    void loadTextSecondTextview() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT_DOWN, "");
        mTvDown.setText(savedText);

    }

    //save text from edittext, when we return from previous activity text will be restored
    void saveState() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(STATE_TEXT, state_cyr_to_lat);
        ed.apply();
    }

    //save text from edittext, when we return from previous activity text will be restored
    void loadState() {
        sPref = getPreferences(MODE_PRIVATE);
        Boolean savedState = sPref.getBoolean(STATE_TEXT, false);
        state_cyr_to_lat = savedState;
        loadTextViewStates();

    }

    //Восстанавливает состояние "Кириллицы" и "латиница"
    void loadTextViewStates() {
        if (state_cyr_to_lat) {
            String stateFirst = getResources().getString(R.string.cyrillic);
            String stateSecond = getResources().getString(R.string.latin);
            mTvFirstState.setText(stateFirst);
            mTvSecondState.setText(stateSecond);

        } else {
            String stateFirst = getResources().getString(R.string.latin);
            String stateSecond = getResources().getString(R.string.cyrillic);
            mTvFirstState.setText(stateFirst);
            mTvSecondState.setText(stateSecond);
        }
    }

    //Здесь происходит преобразование вставленного текста
    void convertTextFromUpToDown() {
        String s2;
        //Проверяем какой стоит выбор латиница на киррилицу или наоборот
        //в зависимости от выбора будет происходить вызов соответствующего метода
        //для приобразования текста
        if (state_cyr_to_lat) {
            s2 = new Converter().cyrillicToLatin(mEtUpEditText.getText().toString());
//                    state_cyr_to_lat=false;
        } else {
            s2 = new Converter().latinToCyrillic(mEtUpEditText.getText().toString());
//                    state_cyr_to_lat=true;
        }
        //Присваем mTvSecondState уже преобразованный текст
        mTvDown.setText(s2);
    }

}
