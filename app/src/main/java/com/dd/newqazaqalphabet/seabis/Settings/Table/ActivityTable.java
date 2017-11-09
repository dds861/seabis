package com.dd.newqazaqalphabet.seabis.Settings.Table;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

import com.dd.newqazaqalphabet.seabis.R;

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



        List<TableProduct> products = new ArrayList<>();

        String[] myResArray = getResources().getStringArray(R.array.cyrillic);
        String[] myResArray2 = getResources().getStringArray(R.array.latin);
        String[] myResArray3 = getResources().getStringArray(R.array.seabiz);
        String[] myResArray4 = getResources().getStringArray(R.array.diacretic);
        for (int i = 0; i < myResArray.length; i++) {
            products.add(new TableProduct(myResArray[i], myResArray2[i], myResArray3[i], myResArray4[i]));

        }


        TableAdapter myAdapter = new TableAdapter(getApplicationContext(), products);

        ListView listView = (ListView) findViewById(R.id.listViewAlphabet);

        listView.setAdapter(myAdapter);

    }

}
