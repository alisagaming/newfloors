package com.emerginggames.floors.levels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.FloatMath;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;
import com.emerginggames.floors.model.Item;
import com.emerginggames.floors.util.Util;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 04.10.12
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class Level17 extends Level {
    enum ResultColor {Red, Back, Zero}
    Roulette roulette;
    boolean gotBlackChip;
    boolean gotRedChip;
    View redChip;
    View blackChip;

    public Level17(LevelListener levelListener, Context context) {
        super(levelListener, context);
        roulette = new Roulette();
        items = new SparseArray<Item>(2);
        items.append(R.id.chip_b, new Item(171, R.drawable.level17_item_chip_b));
        items.append(R.id.chip_r, new Item(172, R.drawable.level17_item_chip_r));
        setControl(R.id.hole_b);
        setControl(R.id.hole_r);
        blackChip = findViewById(R.id.chip_in_hole_b);
        redChip = findViewById(R.id.chip_in_hole_r);
    }

    @Override
    protected void onElementClicked(View elementView) {
        Item item = levelListener.getCurrentItem();
        if (item.itemId ==171 && elementView.getId() == R.id.hole_b){
            blackChip.setVisibility(VISIBLE);
            levelListener.removeCurrentItem();
        }
        if (item.itemId ==172 && elementView.getId() == R.id.hole_r){
            redChip.setVisibility(VISIBLE);
            levelListener.removeCurrentItem();
        }

    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_17;
    }

    void rouletteRotatingDone(){
        switch (roulette.getResultColor()){
            case Back:
                if (!gotBlackChip){
                    gotBlackChip = true;
                    findViewById(R.id.chip_b).setVisibility(VISIBLE);
                }
                break;
            case Red:
                if (!gotRedChip){
                    gotRedChip = true;
                    findViewById(R.id.chip_r).setVisibility(VISIBLE);
                }
                break;
        }
    }

    void drumRotateReleased(){
        if (redChip.getVisibility() == VISIBLE && blackChip.getVisibility() == VISIBLE && roulette.getCellAtAngle(180) == 0){
            elevator.openDoors();
            roulette.rollOut();
        }

    }

    private class Roulette{

        private static final float DRUM_DECELERATION = 35;
        private static final float BALL_DECELERATION = 40;
        private static final int UPDATE_DELAY = 10;
        private static final int MIN_BALL_SPEED = 180;
        private static final int MAX_BALL_SPEED = 270;
        private static final int MIN_DRUM_SPEED = 180;
        private static final int MAX_DRUM_SPEED = 270 ;
        View ball;
        View drum;
        View drumGrid;
        View handle;
        int centerX;
        int centerY;
        int maxBallDr;
        int maxBallR;
        float ballStartSpeed;
        float drumStartSpeed;
        long lastUpdateTime;
        long startTime;
        float currentDrumAngle = 0;
        float currentBallAngle = 0;
        int resultCell;
        final float cellAngle = 360f/ 37f;
        boolean rotating;


        private Roulette() {
            ball = findViewById(R.id.ball);
            handle = findViewById(R.id.roulette_handle);
            drum = findViewById(R.id.roulette_drum);
            drumGrid = findViewById(R.id.roulette_drum_grid);

            View back = findViewById(R.id.roulette_back);
            LayoutParams lp = (LayoutParams)back.getLayoutParams();
            centerX = lp.width/2;
            centerY = lp.height/2;

            lp = (LayoutParams)drum.getLayoutParams();
            maxBallR = lp.height/2;

            lp = (LayoutParams)ball.getLayoutParams();
            maxBallDr = lp.topMargin - lp.height/2;
            maxBallR -= lp.height/2;
            int[] rules = lp.getRules();
            rules[CENTER_HORIZONTAL] = 0;
            rules[ALIGN_TOP] = 0;
            setBallPosition(0, 1);
            setDrumAngle(0);

            handle.setOnClickListener(handleClickListener);
            drum.setOnTouchListener(drumRotateListener);
        }

        void setDrumAngle(float angle){
            Animation anim = new RotateAnimation(angle, angle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(1);
            anim.setFillAfter(true);
            drum.startAnimation(anim);

            float gridAngle = angle - (float)Math.floor(angle/ cellAngle) * cellAngle;
            anim = new RotateAnimation(gridAngle, gridAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(1);
            anim.setFillAfter(true);

            drumGrid.startAnimation(anim);
        }

        void setBallPosition(float angle, float dr){
            dr = Math.min(1, Math.max(0, dr));
            float r = maxBallR - ball.getHeight()/2 - dr * maxBallDr;
            LayoutParams lp = (LayoutParams)ball.getLayoutParams();
            angle = angle / 180 * 3.1415f;

            lp.leftMargin = centerX + (int)(r * FloatMath.sin(angle)) - lp.width/2;
            lp.topMargin = centerY - (int)(r * FloatMath.cos(angle)) - lp.height/2;

            ball.requestLayout();
        }

        OnClickListener handleClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = lastUpdateTime = System.currentTimeMillis();
                ballStartSpeed = - (float)(Math.random() * (MAX_BALL_SPEED - MIN_BALL_SPEED)) - MIN_BALL_SPEED;
                drumStartSpeed = (float)Math.random() * (MAX_DRUM_SPEED - MIN_DRUM_SPEED) + MIN_DRUM_SPEED;
                postDelayed(update, UPDATE_DELAY);
                currentBallAngle = 0;
                resultCell = -1;
                rotating = true;
            }
        };

        Runnable update = new Runnable() {
            @Override
            public void run() {

                long now = System.currentTimeMillis();
                float time = (now - startTime) / 1000f;
                float dt = (now - lastUpdateTime) / 1000f;

                float drumSpeed = Math.max(0, drumStartSpeed - time * DRUM_DECELERATION);
                currentDrumAngle += drumSpeed * dt;
                setDrumAngle(currentDrumAngle);

                float ballSpeed = ballStartSpeed + time * BALL_DECELERATION;
                if (resultCell == -1){
                    currentBallAngle += ballSpeed * dt;
                    float dr = 1 - ballSpeed / ballStartSpeed;
                    setBallPosition(currentBallAngle, dr);

                    if (dr > 0.5f)
                        resultCell = getCellAtAngle(currentBallAngle);
                } else {
                    currentBallAngle = currentDrumAngle + (resultCell) * (cellAngle);
                    setBallPosition(currentBallAngle, 1 - ballSpeed/ballStartSpeed);
                }

                lastUpdateTime = now;

                if (drumSpeed > 0)
                    postDelayed(update, UPDATE_DELAY);
                else{
                    rotating = false;
                    normaliseAngles();
                    rouletteRotatingDone();
                }
            }
        };

        ResultColor getResultColor(){
            if (resultCell == 0)
                    return ResultColor.Zero;
            if (resultCell%2 == 1)
                return ResultColor.Red;
            return ResultColor.Back;
        }

        void normaliseAngles(){
            currentBallAngle -= Math.floor(currentBallAngle / 360) * 360 ;
            currentDrumAngle -= Math.floor(currentDrumAngle / 360) * 360 ;
        }

        OnTouchListener drumRotateListener = new OnTouchListener() {
            float lastAngle;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (rotating)
                            return false;

                        lastAngle = (float)(Util.getAngleInView(v, event.getX(), event.getY()) / Math.PI * 180);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float angle = (float)(Util.getAngleInView(v, event.getX(), event.getY()) / Math.PI * 180);
                        float da = (angle - lastAngle);
                        lastAngle = angle;
                        currentDrumAngle -= da;
                        currentBallAngle -= da;
                        setDrumAngle(currentDrumAngle);
                        setBallPosition(currentBallAngle, 1);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        drumRotateReleased();
                        return true;
                }
                return false;
            }
        };

        int getCellAtAngle(float angle){
            float pos = angle - currentDrumAngle + cellAngle/2;
            pos -= Math.floor(pos / 360) * 360 ;
            return (int)(pos / cellAngle);
        }

        void rollOut(){
            final View roulette = findViewById(R.id.roulette);

            float toDegrees = 180;
            AnimationSet animSet = new AnimationSet(true);
            animSet.setDuration(1500);
            Animation anim1 = new RotateAnimation(0, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animSet.addAnimation(anim1);

            float l =  roulette.getHeight() / 2 * toDegrees / 180 * (float)Math.PI;
            Animation anim2 = new TranslateAnimation(0, l, 0, 0);
            anim2.setDuration(1500);
            animSet.addAnimation(anim2);
            animSet.setFillAfter(true);
            animSet.setDuration(1500);

            roulette.startAnimation(animSet);
            animSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    roulette.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

        }
    }
}
