package com.dd.newqazaqalphabet.seabis.Settings.Table;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.newqazaqalphabet.seabis.R;
import com.dd.newqazaqalphabet.seabis.Settings.Table.EditDiacteric.ActivityEditDiacritic;

import java.util.List;

/**
 * Created by dds86 on 08-Nov-17.
 */

public class TableAdapter extends ArrayAdapter<TableProduct> implements View.OnClickListener {
    private List<TableProduct> objects;
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;
    private ImageView mImageViewEdit;

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
        mText4.setOnClickListener(this);
        mImageViewEdit = (ImageView) itemView.findViewById(R.id.imageViewEdit);
        mImageViewEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text4:
                // TODO 17/11/09
                break;
            case R.id.imageViewEdit:
                YoYo.with(Techniques.FadeOut)
                        .duration(150)
                        .repeat(0)
                        .playOn(view.findViewById(R.id.imageViewEdit));
                YoYo.with(Techniques.FadeIn)
                        .duration(350)
                        .repeat(0)
                        .playOn(view.findViewById(R.id.imageViewEdit));

                Intent intent   = new Intent(getContext(), ActivityEditDiacritic.class);
               getContext().startActivity(intent);
                break;
            default:
                break;
        }
    }
}