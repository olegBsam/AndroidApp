package com.example.anroid.gui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anroid.R;

import java.util.ArrayList;

public class NoteItem extends LinearLayout {
    private static boolean isMultiSelected = false;

    public static ArrayList<NoteItem> getSelectedItems() {
        return selectedItems;
    }

    public static void setSelectedItems(ArrayList<NoteItem> ni) {
        selectedItems = ni;
    }

    public static void reloadSelectedItems(){
        selectedItems = new ArrayList<>();
    }

    private static ArrayList<NoteItem> selectedItems = new ArrayList<>();
    private static OnClickListener deleteModeFunc;

    private int guid;
    private boolean selected = false;
    private TextView tv;
    private ImageView iv;

    private ArrayList<NoteItem> allItems = new ArrayList<>();


    public static boolean isMultiSelected() {
        return isMultiSelected;
    }

    public static void setDeleteModeFunc(OnClickListener deleteModeFunc) {
        NoteItem.deleteModeFunc = deleteModeFunc;
    }

    public int getGuid() {
        return guid;
    }

    public NoteItem(Context context, String str, int guid, byte[] image, OnClickListener clickListener) {
        super(context);
        allItems.add(this);

        this.setOrientation(HORIZONTAL);
        this.guid = guid;

        tv = new TextView(context);
        tv.setSingleLine(false);
        iv = new ImageView(context);
        tv.setText(str);

        if(image != null){
            iv.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        }

        iv.setAdjustViewBounds(true);
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

        this.setOnLongClickListener((sender) -> {
            if (!isMultiSelected) {
                isMultiSelected = true;
                Select(this);
                deleteModeFunc.onClick(this);
            }
            return true;
        });

        this.setOnClickListener((sender)->{
            if (isMultiSelected){
                if (selected){
                    UnSelected(this);
                    if (selectedItems.size() == 0){
                        endDeleting();
                    }
                }
                else {
                    Select(this);
                }
            }
            else {
                clickListener.onClick(this);
            }
            return;
        });

    }

    public static void endDeleting(){
        isMultiSelected = false;
        deleteModeFunc.onClick(null);
    }

    public void Select(NoteItem sender){
        selected = true;
        selectedItems.add(sender);
        sender.setBackgroundColor(Color.parseColor("#FF3300"));
    }

    public void UnSelected(NoteItem sender){
        selected = false;

        sender.setBackgroundColor(Color.parseColor("#FFFFFF"));
        selectedItems.remove(sender);
    }
}