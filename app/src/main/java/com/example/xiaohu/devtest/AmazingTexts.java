package com.example.xiaohu.devtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xiaohu on 30/04/15.
 */
public class AmazingTexts extends Activity {

    @InjectView(R.id.text_size_Bar)
    SeekBar txtSizeBar;

    @InjectView(R.id.new_text_button)
    Button newTxtBtn;

    @InjectView(R.id.size_text_button)
    Button sizeTxtBtn;

    @InjectView(R.id.text_size_Bar_layout)
    LinearLayout txtSizeBarLayout;

    @InjectView(R.id.text_toolbar_layout)
    LinearLayout txtTBarLayout;


    private ArrayList<EditText> editTexts = new ArrayList<EditText>();
    private  int screenWidth;
    private  int screenHeight;
    private EditText currentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = getResources().getDisplayMetrics();
         screenWidth = dm.widthPixels;
         screenHeight = dm.heightPixels;

        setContentView(R.layout.amazing_text_layout);
        ButterKnife.inject(this);

        setTxtSizeBarListener();
        setImageBackgroundWithTxt();

    }

    private void setTxtSizeBarListener() {
        txtSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (editTexts.size() != 0){
                    int txtSize = progress;
                    currentTxt.setTextSize(txtSize);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setImageBackgroundWithTxt() {
        Bitmap bitmap = drawTextToBitmap(getApplicationContext(), R.drawable.earth, "earth");
        RelativeLayout contentLayout = (RelativeLayout) findViewById(R.id.content_layout);
        contentLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
    }

    private View.OnTouchListener createOnTouchListener() {



        View.OnTouchListener textListener = new View.OnTouchListener() {
            int lastX
                    ,
                    lastY;
            boolean lastActionIsMove;

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int action = event.getAction();
                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        lastActionIsMove = false;
                        currentTxt = (EditText)v;
                        currentTxt.setBackgroundResource(R.drawable.blur);
                        for (int i = 0; i < editTexts.size(); i ++){
                            if (!currentTxt.equals(editTexts.get(i))){
                                editTexts.get(i).setBackgroundColor(Color.TRANSPARENT);
                            }
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        lastActionIsMove = true;
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        int left = v.getLeft() + dx;
                        int top = v.getTop() + dy;
                        int right = v.getRight() + dx;
                        int bottom = v.getBottom() + dy;
                        if (left < 0) {
                            left = 0;
                            right = left + v.getWidth();
                        }
                        if (right > screenWidth) {
                            right = screenWidth;
                            left = right - v.getWidth();
                        }
                        if (top < 0) {
                            top = 0;
                            bottom = top + v.getHeight();
                        }
                        if (bottom > screenHeight) {
                            bottom = screenHeight;
                            top = bottom - v.getHeight();
                        }
                        v.layout(left, top, right, bottom);
                        Log.i("", "position:" + left + ", " + top + ", " + right
                                + ", " + bottom);
                        ((RelativeLayout.LayoutParams) (v.getLayoutParams())).leftMargin = left;
                        ((RelativeLayout.LayoutParams) (v.getLayoutParams())).topMargin = top;
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_UP:
                        if ((int) event.getRawX() == lastX && (int) event.getRawY() == lastY && !lastActionIsMove) {
                            Log.i("", "can edit now:");
                            setTxtInPutDialog((EditText)v);
                        }
                        break;
                }
                return true;
            }
        };
        return textListener;
    }

    private void setTxtInPutDialog(final EditText editText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("please input your text");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editText.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @OnClick(R.id.new_text_button)
    public void clickCreateTxtRBtn() {

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.content_layout);
        EditText editText = new EditText(this);
        editText.setHint("tape to edit");
        editText.setBackgroundResource(R.drawable.blur);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screenWidth/3, screenWidth/3);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        layoutParams.addRule(RelativeLayout.ALIGN_TOP);
        editText.setLayoutParams(layoutParams);
        relativeLayout.addView(editText);

        editText.setOnTouchListener(createOnTouchListener());
        editTexts.add(editText);
        currentTxt = editTexts.get(editTexts.size() - 1);
        for(int i = 0; i < editTexts.size() - 1; i ++){
            editTexts.get(i).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @OnClick(R.id.text_widget_button)
    public void clickTxtWidgetBtn(){
        if (txtTBarLayout.getVisibility() == View.INVISIBLE){
            txtTBarLayout.setVisibility(View.VISIBLE);
            txtSizeBarLayout.setVisibility(View.INVISIBLE);
            onlyShowCurrentEdtTxt();
        }else {
            txtTBarLayout.setVisibility(View.INVISIBLE);
            txtSizeBarLayout.setVisibility(View.INVISIBLE);
            closeAllEdtTxtBackground();
        }
    }

    private void onlyShowCurrentEdtTxt() {
        if (editTexts.size() != 0){
            currentTxt.setBackgroundResource(R.drawable.blur);
            for (int i = 0; i < editTexts.size(); i ++){
                if (!currentTxt.equals(editTexts.get(i))){
                    editTexts.get(i).setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }
    }

    private void closeAllEdtTxtBackground() {
        if (editTexts.size() != 0){
            for (int i = 0; i < editTexts.size(); i ++){
                editTexts.get(i).setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    @OnClick(R.id.button_ok_text_widget)
    public void clickTxtWidgetOKBtn(){
        txtTBarLayout.setVisibility(View.INVISIBLE);
        closeAllEdtTxtBackground();
    }


    @OnClick(R.id.size_text_button)
    public void clickOnTxtSizeBtn(){
        txtSizeBarLayout.setVisibility(View.VISIBLE);
        txtTBarLayout.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.button_ok_text_size)
    public void clickOnTxtSizeOKBtn(){
        txtSizeBarLayout.setVisibility(View.INVISIBLE);
        txtTBarLayout.setVisibility(View.VISIBLE);
    }

    public Bitmap drawTextToBitmap(Context gContext,
                                   int gResId,
                                   String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap =
                BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.WHITE);
        // text size in pixels
        paint.setTextSize((int) (30 * scale));

        paint.setTypeface(Typeface.MONOSPACE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width())/2;
        int y = (bitmap.getHeight() + bounds.height())/2;

        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }
}
