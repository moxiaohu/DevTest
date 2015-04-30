package com.example.xiaohu.devtest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiaohu on 30/04/15.
 */
public class AmazingTexts extends Activity implements View.OnTouchListener{

    private static final String EDITTEXT_TAG = "edit_text";
    private GestureDetector mGestureDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amazing_text_layout);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.create_text_button)
    public void clickCreateTxtRBtn() {

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.content_layout);
        final EditText editText = new EditText(this);
        editText.setHint("tape to edit");
        editText.setBackgroundColor(Color.parseColor("#ffffff"));
        editText.setTag(EDITTEXT_TAG);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(Gravity.CENTER);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        editText.setLayoutParams(layoutParams);
        relativeLayout.addView(editText);

        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                ClipData.Item item = new ClipData.Item(v.getTag().toString());
                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(editText);

                v.startDrag(null, myShadow, null, 0);
                return false;
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }


}
