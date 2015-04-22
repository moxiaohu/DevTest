package com.example.xiaohu.devtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by xiaohu on 17/04/15.
 */
public class MoveAnimation extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);

        final TextView tv = new TextView(this);
        tv.setText("Animation");

        final TranslateAnimation moveLefttoRight = new TranslateAnimation(0, 200, 0, 0);
        moveLefttoRight.setDuration(1000);
        moveLefttoRight.setFillAfter(true);

        Button button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        button.setText("PressMe");
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                tv.startAnimation(moveLefttoRight);
            }

        });

        ll.addView(tv);
        ll.addView(button);
        setContentView(ll);
    }

}
