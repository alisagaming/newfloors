package com.emerginggames.floors.levels;

import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;
import com.emerginggames.floors.items.ItemNotepadLevel3;
import com.emerginggames.floors.model.Item;
import com.emerginggames.floors.view.DustView;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 04.09.12
 * Time: 1:23
 * To change this template use File | Settings | File Templates.
 */
public class Level07 extends Level {
    ImageView mBrush;
    DustView mDust;
    public Level07(LevelListener levelListener, Context context) {
        super(levelListener, context);
        items = new SparseArray<Item>(1);
        items.append(R.id.brush, new Item(1, R.drawable.level07_brush_icon));
        items.append(R.id.note, new ItemNotepadLevel3(1, R.drawable.fl3_tool_note, true));
    }

    @Override
    protected void onElementClicked(View elementView) {
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_07;
    }

    @Override
    protected void scaleViews() {
        scaleImageSize(R.id.brush);
        scaleMargins(R.id.brush);
    }

    @Override
    public void start() {
        super.start();
        mBrush = (ImageView)findViewById(R.id.brush);
        mDust = (DustView)findViewById(R.id.dustView);
    }

    @Override
    public void itemSelected(Item item) {
        if (item != null && item.itemId == 1){
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mBrush.getLayoutParams();
            lp.leftMargin = Metrics.width/2 - mBrush.getWidth()/2;
            lp.bottomMargin = -60;
            mBrush.setLayoutParams(lp);

            mBrush.setVisibility(View.VISIBLE);

            Animation an = new RotateAnimation(25f, 30f, mBrush.getWidth()/2, mBrush.getHeight() * 0.9f);

            // Set the animation's parameters
            an.setDuration(2000);               // duration in ms
            an.setRepeatCount(-1);                // -1 = infinite repeated
            an.setRepeatMode(Animation.REVERSE); // reverses each repeat
            an.setFillAfter(true);               // keep rotation after animation

            // Aply animation to image view
            mBrush.setAnimation(an);

            mDust.setOnTouchListener(dustTouchListener);

        } else{
            mBrush.setVisibility(GONE);
            mBrush.clearAnimation();
            mDust.setOnTouchListener(null);
        }
    }

    OnTouchListener dustTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    moveBrushTo((int)event.getX(), (int)event.getY());
                    mDust.clear(event.getX(), event.getY(), mBrush.getWidth()/2);
                    mDust.invalidate();
                    return true;
                case MotionEvent.ACTION_UP:
                    Rect rect = new Rect();
                    rect.top = 0;
                    rect.left = 0;
                    rect.right = mDust.getWidth();
                    rect.bottom = mDust.getHeight() - levelListener.getCoveredBottomHeight();
                    if (mDust.isAreaClear(rect)){
                        elevator.openDoors();
                        levelListener.resetCurrentItem();
                    }

                    return true;
            }
            return false;
        }
    };

    void moveBrushTo(int x, int y){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)mBrush.getLayoutParams();
        lp.leftMargin = x - mBrush.getWidth()/2;
        lp.bottomMargin = -(y + mBrush.getHeight() /15);
        mBrush.setLayoutParams(lp);
    }




}
