package com.dd.newqazaqalphabet.seabis;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Main2Activity extends AppCompatActivity {

    private EditText mEtDownEditText;

    //Admob code parameters
    private static final String TAG = "MainActivity";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        String s = getIntent().getStringExtra("text");
        mEtDownEditText.setText(s);

        //Make enable image with text on action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_carrot2);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Admob code
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void initView() {
        mEtDownEditText = (EditText) findViewById(R.id.etDownEditText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_copyAll:// TODO 17/11/02
                // TODO 17/11/02

                YoYo.with(Techniques.FadeOut)
                        .duration(150)
                        .repeat(0)
                        .playOn(findViewById(R.id.action_copyAll));
                YoYo.with(Techniques.FadeIn)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.action_copyAll));

                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                // Creates a new text clip to put on the clipboard
                ClipData clip = ClipData.newPlainText("simple text", mEtDownEditText.getText().toString());

                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, R.string.TextCopied, Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_share:// TODO 17/11/02


                YoYo.with(Techniques.FadeOut)
                        .duration(150)
                        .repeat(0)
                        .playOn(findViewById(R.id.action_share));
                YoYo.with(Techniques.FadeIn)
                        .duration(350)
                        .repeat(0)
                        .playOn(findViewById(R.id.action_share));

                String shareBody = mEtDownEditText.getText().toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_using)));
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }

}
