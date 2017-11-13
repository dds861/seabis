package com.dd.newqazaqalphabet.seabiz.Settings.Table;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dd.newqazaqalphabet.seabiz.R;

import java.util.List;

/**
 * Created by dds86 on 08-Nov-17.
 */

public class TableAdapter extends ArrayAdapter<TableProduct>  {
    private List<TableProduct> objects;
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;


    public TableAdapter(@NonNull Context context, @NonNull List<TableProduct> objects) {
        super(context, 0, objects);
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_table_item, parent, false);
        initView(view);
        TableProduct product = getItem(position);

        mText1.setText(product.getTextView1());
        mText2.setText(product.getTextView2());
        mText3.setText(product.getTextView3());
        mText4.setText(product.getTextView4());

        return view;
    }

    private void initView(@NonNull final View itemView) {
        mText1 = (TextView) itemView.findViewById(R.id.text1);
        mText2 = (TextView) itemView.findViewById(R.id.text2);
        mText3 = (TextView) itemView.findViewById(R.id.text3);
        mText4 = (TextView) itemView.findViewById(R.id.text4);
    }


}