package com.example.xiaohu.devtest;

import com.devsmart.android.HorizontalListView;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HorizontalListViewDemo extends Activity {

    private View mDownView;
    private int screenWidth;
    private int itemWidth;
    private float mDownX;
    private int mDownPosition;
    private VelocityTracker mVelocityTracker;
    private HorizontalListView listview;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listviewdemo);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;

        listview = (HorizontalListView) findViewById(R.id.listview);

        Log.d("listview width", listview.getLayoutParams().width + "");


        Button button = (Button) findViewById(R.id.b);

        Log.d("button width", button.getLayoutParams().width + "");

        itemWidth = (screenWidth-button.getLayoutParams().width)/10;

       Log.d("item width", itemWidth + "");


        final RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.testbackground);


        listview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_MOVE :
                    case MotionEvent.ACTION_DOWN :{

                        Rect rect = new Rect();
                        int childCount = listview.getChildCount();
                        int[] listViewCoords = new int[2];
                        listview.getLocationOnScreen(listViewCoords);
                        int x = (int) event.getRawX() - listViewCoords[0];
                        int y = (int) event.getRawY() - listViewCoords[1];
                        View child;
                        for (int i = 0; i < childCount; i++) {
                            child = listview.getChildAt(i);
                            child.getHitRect(rect);
                            if (rect.contains(x, y)) {
                                mDownView = child;
                                break;
                            }
                        }
                        if (mDownView != null) {
                            mDownX = event.getRawX();
                            mDownPosition = listview.getPositionForView(mDownView);
                            Log.e("position", "" + mDownPosition);
                            switch (mDownPosition){
                                case 0:
                                    listview.setBackgroundColor(Color.RED);
                                    break;
                                case 1:
                                    listview.setBackgroundColor(Color.GREEN);
                                    break;
                                case 2:
                                    listview.setBackgroundColor(Color.YELLOW);
                                    break;
                            }

                            mVelocityTracker = VelocityTracker.obtain();
                            mVelocityTracker.addMovement(event);
                        }
                        v.onTouchEvent(event);
                        return true;

                    }
                }
                return false;
            }
        });

		listview.setAdapter(mAdapter);
        Log.d("list width", listview.getLayoutParams().width + "");
        listview.getLayoutParams().width = 400;
	}
	



	private BaseAdapter mAdapter = new BaseAdapter() {


		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitem, null);
            ImageView imageView = (ImageView) retval.findViewById(R.id.image);
            imageView.setImageResource(R.drawable.icon);
            imageView.getLayoutParams().width = itemWidth;

			return retval;
		}
		
	};

}
