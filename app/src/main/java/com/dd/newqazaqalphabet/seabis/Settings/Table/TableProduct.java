package com.dd.newqazaqalphabet.seabis.Settings.Table;

/**
 * Created by dds86 on 08-Nov-17.
 */

public class TableProduct {
    String textView1, textView2, textView3, textView4;


    public String getTextView1() {
        return textView1;
    }

    public String getTextView2() {
        return textView2;
    }

    public String getTextView3() {
        return textView3;
    }

    public String getTextView4() {
        return textView4;
    }



    public void setTextView1(String textView1) {
        this.textView1 = textView1;
    }

    public void setTextView2(String textView2) {
        this.textView2 = textView2;
    }

    public void setTextView3(String textView3) {
        this.textView3 = textView3;
    }

    public void setTextView4(String textView4) {
        this.textView4 = textView4;
    }

    public TableProduct(String textView1, String textView2, String textView3, String textView4) {
        this.textView1 = textView1;
        this.textView2 = textView2;
        this.textView3 = textView3;
        this.textView4 = textView4;
    }
}
