package com.example.xiaohu.devtest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 缩放图片界面
 * @author ZHF
 *
 */
public class MainActivity extends Activity {
    public static final String TAG = "ImgDisplayActivity";
    //控件声明
    private Button btnZoomin, btnZoomout;
    private ImageView imgDisPlay;
    private EditText edtTxtDisplay;
    private LinearLayout lLayoutDisplay;
    private FrameLayout fLayoutDisplay;

    private Bitmap bitmap;

    private int imgId = 0;
    private double scale_in = 0.8;//缩小比例
    private double scale_out = 1.25;//放大比例

    private float scaleWidth = 1;
    private float scaleHeight = 1;
    //模式：0：什么都不干；1：拖拽； 2：缩放
    public static final int NONE = 0;
    public static final int DRAG = 1;
    public static final int ZOOM = 2;
    //声明触发的事件模式
    private int mode = NONE;

    private Matrix matrix;   //矩阵
    private Matrix currMatrix; //当前矩阵

    private PointF starPoint;
    private PointF midPoint;

    private float startDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_layout);
        //初始化
        fLayoutDisplay = (FrameLayout) findViewById(R.id.flayout_img_display);
        lLayoutDisplay = (LinearLayout) findViewById(R.id.linearLayout_img_display);
        imgDisPlay = (ImageView) findViewById(R.id.img_display);
        edtTxtDisplay = (EditText) findViewById(R.id.text);
        btnZoomin = (Button) findViewById(R.id.btn_min);
        btnZoomout = (Button) findViewById(R.id.btn_out);


        matrix = new Matrix(); //保存拖拽变化
        currMatrix = new Matrix();// 当前的
        starPoint = new PointF();//开始点的位置


        btnZoomin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomIn();
            }
        });
        btnZoomout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomOut();//放大
            }
        });

        imgDisPlay.setImageResource(R.drawable.icon);
        //给图片绑定监听器哦
        imgDisPlay.setOnTouchListener(new ImageViewOnTouchListener());

    }

    /**放大操作**/
    private void zoomOut() {
        reSizeBmp(scale_out);
        btnZoomin.setEnabled(true);
    }

    /**缩小操作**/
    private void zoomIn() {
        reSizeBmp(scale_in);
    }

    /**接收传入的缩放比例实现缩放**/
    private void reSizeBmp(double scale) {
        //缩放比例
        scaleWidth = (float) (scaleWidth * scale);
        scaleHeight = (float) (scaleHeight * scale);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight); //设计缩放比例

        TextView textView = new TextView(this);
        imgDisPlay.setImageMatrix(matrix);
    }

    /**计算触摸实现缩放**/
    final class ImageViewOnTouchListener implements OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:  //一只手指按下
                    Log.i(TAG,"一只手指按下");
                    currMatrix.set(matrix);
                    starPoint.set(event.getX(), event.getY());

                    mode = DRAG;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN: //如果有一只手指按下屏幕，后续又有一个手指按下     // 两只手指按下
                    Log.i(TAG,"又有一只手指按下");

                    startDistance = distance(event);//记下两点的距离
                    Log.i(TAG, startDistance+"");

                    if(startDistance > 5f) {  //两个手指之间的最小距离像素大于5，认为是多点触摸
                        mode = ZOOM;
                        currMatrix.set(matrix);

                        midPoint = getMidPoint(event); //记下两个点之间的中心点

                    }

                    break;

                case MotionEvent.ACTION_MOVE:

                    if(mode == DRAG) {  //拖拽模式
                        Log.i(TAG,"一只手指在拖拽");

                        //开始--》结束点的距离
                        float dx = event.getX() - starPoint.x;
                        float dy = event.getY() - starPoint.y;

                        matrix.set(currMatrix);
                        matrix.postTranslate(dx, dy);//移动到指定点：矩阵移动比例；eg:缩放有缩放比例
                    } else if(mode == ZOOM) {  //缩放模式
                        Log.i(TAG,"正在缩放");

                        float distance = distance(event);  //两点之间的距离
                        if(distance > 5f) {
                            matrix.set(currMatrix);
                            float cale = distance / startDistance;
                            matrix.preScale(cale, cale, midPoint.x, midPoint.y);  //进行比例缩放
                        }
                    }
                    break;

                case MotionEvent.ACTION_UP: //最后一只手指离开屏幕后触发此事件
                case MotionEvent.ACTION_POINTER_UP: //一只手指离开屏幕，但还有一只手指在上面会触此事件
                    //什么都没做
                    mode = NONE;
                    break;
                default:
                    break;
            }

            imgDisPlay.setImageMatrix(matrix);

            //两只手指的缩放
            return true;
        }
    }

    /**计算两点之间的距离像素**/
    private float distance(MotionEvent e) {

        float eX = e.getX(1) - e.getX(0);  //后面的点坐标 - 前面点的坐标
        float eY = e.getY(1) - e.getY(0);
        return FloatMath.sqrt(eX * eX + eY * eY);
    }

    /**计算两点之间的中心点**/
    private PointF getMidPoint(MotionEvent event) {

        float x = (event.getX(1) - event.getX(0)) / 2;
        float y = (event.getY(1) - event.getY(0)) / 2;
        return new PointF(x,y);
    }
}