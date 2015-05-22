package com.example.xiaohu.devtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaohu on 11/05/15.
 */
public class GridViewTest extends Activity implements AbsListView.OnScrollListener{
    private static final String TAG = "MainActivity";

    private TextView textview_show_prompt = null;
    private GridView gridview_test = null;

    private List<String> mList = null;
    //图片缓存用来保存GridView中每个Item的图片，以便释放
    public static Map<String,Bitmap> gridviewBitmapCaches = new HashMap<String,Bitmap>();

    private MyGridViewAdapter adapter = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);
        findViews();
        initData();
        setAdapter();
    }

    private void findViews(){
        textview_show_prompt = (TextView)findViewById(R.id.textview_show_prompt);
        gridview_test = (GridView)findViewById(R.id.gridview_test);
    }

    private void initData(){
        mList = new ArrayList<>();
        String url = "/mnt/sdcard/testGridView/jay.png";//为sd卡下面创建testGridView文件夹，将图片放入其中
        //为了方便测试，我们这里只存入一张图片，将其路径后面添加数字进行区分，到后面要获取图片时候再处理该路径。
        for(int i=0;i<1000;i++){
            mList.add(url+"/"+i);//区分路径
        }
    }

    private void setAdapter(){
        adapter = new MyGridViewAdapter(this, mList);
        gridview_test.setAdapter(adapter);
        gridview_test.setOnScrollListener(this);
    }

    //释放图片的函数
    private void recycleBitmapCaches(int fromPosition,int toPosition){
        Bitmap delBitmap = null;
        for(int del=fromPosition;del<toPosition;del++){
            delBitmap = gridviewBitmapCaches.get(mList.get(del));
            if(delBitmap != null){
                //如果非空则表示有缓存的bitmap，需要清理
                Log.d(TAG, "release position:" + del);
                //从缓存中移除该del->bitmap的映射
                gridviewBitmapCaches.remove(mList.get(del));
                delBitmap.recycle();
                delBitmap = null;
            }
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        //注释：firstVisibleItem为第一个可见的Item的position，从0开始，随着拖动会改变
        //visibleItemCount为当前页面总共可见的Item的项数
        //totalItemCount为当前总共已经出现的Item的项数
        recycleBitmapCaches(0,firstVisibleItem);
        recycleBitmapCaches(firstVisibleItem+visibleItemCount, totalItemCount);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
    }


}