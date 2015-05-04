//package com.example.xiaohu.devtest;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.graphics.PointF;
//import android.os.Bundle;
//import android.util.FloatMath;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
///**
// * 缩放图片界面
// * @author ZHF
// *
// */
//public class MainActivity extends Activity {
//    public static final String TAG = "ImgDisplayActivity";
//    //控件声明
//    private Button btnZoomin, btnZoomout;
//    private EditText edtTxtDisplay;
//    private RelativeLayout lLayoutDisplay;
//    private FrameLayout fLayoutDisplay;
//
//    private Bitmap bitmap;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.drag_layout);
//        //初始化
//        fLayoutDisplay = (FrameLayout) findViewById(R.id.flayout_img_display);
//        lLayoutDisplay = (RelativeLayout) findViewById(R.id.linearLayout_img_display);
//        edtTxtDisplay = (EditText) findViewById(R.id.text);
//        btnZoomin = (Button) findViewById(R.id.btn_min);
//        btnZoomout = (Button) findViewById(R.id.btn_out);
//
//
//
//    /**计算触摸实现缩放**/
//    final class ImageViewOnTouchListener implements OnTouchListener {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//
//            switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                case MotionEvent.ACTION_DOWN:  //一只手指按下
//                    Log.i(TAG, "一只手指按下");
//                    lastPoint.set(event.getRawX(), event.getRawY());
//                    break;
//
//                case MotionEvent.ACTION_MOVE:
//
//                    float dx = event.getX() - lastPoint.x;
//                    float dy = event.getY() - lastPoint.y;
//
//                    Log.d("touch point", "x " + event.getX() + "y " + event.getY());
//
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) edtTxtDisplay.getLayoutParams();
//                    layoutParams.leftMargin = (int) (event.getX() + dx);
//                    layoutParams.topMargin = (int) (event.getY() + dy);
//                    edtTxtDisplay.setLayoutParams(layoutParams);
//                    lastPoint.x = (int) event.getRawX();
//                    lastPoint.y = (int) event.getRawY();
//                    break;
//                default:
//                    break;
//            }
//            //两只手指的缩放
//            return true;
//        }
//
//        /**
//         * 计算两点之间的距离像素*
//         */
//        private float distance(MotionEvent e) {
//
//            float eX = e.getX(1) - e.getX(0);  //后面的点坐标 - 前面点的坐标
//            float eY = e.getY(1) - e.getY(0);
//            return FloatMath.sqrt(eX * eX + eY * eY);
//        }
//
//        /**
//         * 计算两点之间的中心点*
//         */
//        private PointF getMidPoint(MotionEvent event) {
//
//            float x = (event.getX(1) - event.getX(0)) / 2;
//            float y = (event.getY(1) - event.getY(0)) / 2;
//            return new PointF(x, y);
//        }
//    }}}