package com.dd.newqazaqalphabet.seabis;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private TextView mEtDownEditText;
    private ImageView mIvDelete;
    private ImageView mIvInsert;
    private ImageView mIvCopyAll;
    private ImageView mIvShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent

            }
        } else {
            // Handle other intents, such as being started from the home screen
            loadText();
            loadState();
        }
        //Проверяем был ли текст ранее, если был, что восстанавливаем
        if (temp != null) {
            mEtUpEditText.setText(temp);
        }

    }

    private void initView() {
        mEtUpEditText = (EditText) findViewById(R.id.etUpEditText);
        mScrollViewEditText = (ScrollView) findViewById(R.id.scrollViewEditText);

        mTvFirstState = (TextView) findViewById(R.id.tvFirstState);
        mIvChangeButton = (ImageView) findViewById(R.id.ivChangeButton);
        mIvChangeButton.setOnClickListener(this);
        mTvSecondState = (TextView) findViewById(R.id.tvSecondState);

        mEtDownEditText = (TextView) findViewById(R.id.etDownEditText);
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
                mEtDownEditText.setText(s2);


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
                mEtDownEditText.setText("");
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
                ClipData clip = ClipData.newPlainText("simple text", mEtDownEditText.getText().toString());

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

                String shareBody = mEtDownEditText.getText().toString();
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



    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            mEtUpEditText.setText(sharedText);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveText();
        saveState();
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

}
