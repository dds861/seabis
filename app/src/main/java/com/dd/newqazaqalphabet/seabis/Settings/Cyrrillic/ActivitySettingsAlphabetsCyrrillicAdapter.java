package com.dd.newqazaqalphabet.seabis.Settings.Cyrrillic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.newqazaqalphabet.seabis.R;

import java.util.List;

/**
 * Created by dds86 on 08-Nov-17.
 */

public class ActivitySettingsAlphabetsCyrrillicAdapter extends ArrayAdapter<ActivitySettingsAlphabetsCyrrillicProduct> {
    private List<ActivitySettingsAlphabetsCyrrillicProduct> objects;

    public ActivitySettingsAlphabetsCyrrillicAdapter(@NonNull Context context, @NonNull List<ActivitySettingsAlphabetsCyrrillicProduct> objects) {
        super(context, 0, objects);
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_settings_alphabets_cyrrillic_items, parent, false);

        ActivitySettingsAlphabetsCyrrillicProduct product = getItem(position);

        TextView textView1 = (TextView) view.findViewById(R.id.text1);
        TextView textView2 = (TextView) view.findViewById(R.id.text2);
        TextView textView3 = (TextView) view.findViewById(R.id.text3);
        final TextView textView4 = (TextView) view.findViewById(R.id.text4);

        textView1.setText(product.getTextView1());
        textView2.setText(product.getTextView2());
        textView3.setText(product.getTextView3());
        textView4.setText(product.getTextView4());

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), textView4.getText(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}