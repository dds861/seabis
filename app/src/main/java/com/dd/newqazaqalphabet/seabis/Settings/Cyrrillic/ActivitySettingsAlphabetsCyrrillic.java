package com.dd.newqazaqalphabet.seabis.Settings.Cyrrillic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.dd.newqazaqalphabet.seabis.R;

import java.util.ArrayList;
import java.util.List;

public class ActivitySettingsAlphabetsCyrrillic extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Восстанавливаем тему после лого
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_alphabets_cyrrillic);


                        List<ActivitySettingsAlphabetsCyrrillicProduct> products = new ArrayList<>();

                        String[] myResArray = getResources().getStringArray(R.array.cyrillic);
                        String[] myResArray2 = getResources().getStringArray(R.array.latin);
                        String[] myResArray3 = getResources().getStringArray(R.array.seabiz);
                        String[] myResArray4 = getResources().getStringArray(R.array.diacretic);
                        for (int i = 0; i < myResArray.length; i++) {
                            products.add(new ActivitySettingsAlphabetsCyrrillicProduct(myResArray[i],myResArray2[i],myResArray3[i],myResArray4[i]));

                        }

                        ActivitySettingsAlphabetsCyrrillicAdapter myAdapter = new ActivitySettingsAlphabetsCyrrillicAdapter(getApplicationContext(),products);

                        ListView listView = (ListView) findViewById(R.id.listViewAlphabet);

                        listView.setAdapter(myAdapter);

    }
}
