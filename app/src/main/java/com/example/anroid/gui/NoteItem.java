package com.example.anroid.gui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anroid.R;

public class NoteItem extends LinearLayout {
    private int guid;
    private boolean isOffset = false;

    private static NoteItem selected = null;

    public int getGuid() {
        return guid;
    }

    public NoteItem(Context context, String str, int guid, byte[] image) {
        super(context);
        this.setOrientation(HORIZONTAL);
        this.guid = guid;

        TextView tv = new TextView(context);
        tv.setSingleLine(false);
        ImageView iv = new ImageView(context);
        tv.setText(str);

        if(image != null){
            iv.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        }

        this.addView(iv);
        this.addView(tv);

        GradientDrawable border = new GradientDrawable();
        border.setColor(0xFFFFFFFF);
        border.setStroke(1, 0xFF000000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(border);
        }

        LayoutParams params = (LayoutParams) tv.getLayoutParams();
        params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
        tv.setLayoutParams(params);

        this.setOnLongClickListener((e) -> {
            toMove();
            return true;
        });

    }

    private void toMove(){
        if (selected == this){
            LayoutParams params = (LayoutParams) selected.getLayoutParams();
            params.rightMargin = 0;
            params.leftMargin = 0;
            selected.setLayoutParams(params);
            selected = null;
        }
        else {
            if (selected != null) {
                LayoutParams params = (LayoutParams) selected.getLayoutParams();
                params.rightMargin = 0;
                params.leftMargin = 0;
                selected.setLayoutParams(params);
            }
            selected = this;
            LayoutParams params = (LayoutParams) selected.getLayoutParams();
            params.rightMargin = 50;
            params.leftMargin = -50;
            selected.setLayoutParams(params);
        }
    }
}