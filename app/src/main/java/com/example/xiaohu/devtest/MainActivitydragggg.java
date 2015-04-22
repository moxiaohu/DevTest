package com.example.xiaohu.devtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;


public class MainActivitydragggg extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_draggg);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        final int screenWidth = dm.widthPixels;
        final int screenHeight = dm.heightPixels - 50;//获取屏幕高度
        ImageView home = (ImageView) findViewById(R.id.imageView1);
        final OnTouchListener child;
        child = new OnTouchListener() {//对新创建的image进行监听
            int lastX, lastY;
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();//获取事件发生时的坐标
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
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
                        v.layout(left, top, right, bottom);//将移动后的图相在新位置显示出来
                        Log.i("", "position:" + left + ", " + top + ", " + right
                                + ", " + bottom);

                        ((RelativeLayout.LayoutParams) (v.getLayoutParams())).leftMargin = left;
                        ((RelativeLayout.LayoutParams) (v.getLayoutParams())).topMargin = top;
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        };
        home.setOnTouchListener(new OnTouchListener() {
            int lastX, lastY;
            ImageView image;
            LayoutParams params;
            int left;
            int top;
            int right;
            int bottom;
            public boolean onTouch(View v, MotionEvent event) {
                int ea = event.getAction();
                Log.i("TAG", "Touch:" + ea);
                switch (ea) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout parentcontener = (RelativeLayout) findViewById(R.id.parentcontent);
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        ImageView parent = (ImageView) findViewById(R.id.imageView1);
                        params = new LayoutParams(parent.getWidth(), parent
                                .getHeight());
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        image = new ImageView(MainActivitydragggg.this);
                        image.setLayoutParams(params);
                        parentcontener.addView(image);
                        image.setImageResource(R.drawable.icon);
                        image.setOnTouchListener(child);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        left = image.getLeft() + dx;
                        top = image.getTop() + dy;
                        right = image.getRight() + dx;
                        bottom = image.getBottom() + dy;
                        if (left < 0) {
                            left = 0;
                            right = left + image.getWidth();
                        }
                        if (right > screenWidth) {
                            right = screenWidth;
                            left = right - image.getWidth();
                        }
                        if (top < 0) {
                            top = 0;
                            bottom = top + image.getHeight();
                        }
                        if (bottom > screenHeight) {
                            bottom = screenHeight;
                            top = bottom - image.getHeight();
                        }
                        image.layout(left, top, right, bottom);
                        Log.i("", "position:" + left + ", " + top + ", " + right
                                + ", " + bottom);
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        params.leftMargin=left;
                        params.topMargin=top;
                        break;
                    case MotionEvent.ACTION_UP:
                        // params.setMargins(left, top, right, bottom);加上这句话，运行的时候可能出现小bug，但不是
                        //每次都出现，可测试一下
                        break;
                }
                return true;
            }
        });
    }
}