package com.emerginggames.floors.levels;

import android.content.Context;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import com.emerginggames.floors.R;
import com.emerginggames.floors.util.Util;

public class Level10 extends Level {
    private static final float CLICK_ANGLE = (float)(Math.PI * 2/3);
    float lastRotateX;
    float lastRotateY;
    int checkN;
    boolean failed = false;
    float droppedAngle;
    View handle;

    public Level10(LevelListener levelListener, Context context) {
        super(levelListener, context);
    }

    @Override
    protected void onElementClicked(View elementView){}

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_10;
    }

    @Override
    protected void initView() {
        super.initView();
        handle = findViewById(R.id.handle);

        handle.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        lastRotateX = event.getX();
                        lastRotateY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (elevator.isOpen())
                            return false;

                        float angle = Util.getAngleInView(v, lastRotateX, lastRotateY, event.getX(), event.getY());
                        rotateHandle((float)((angle + droppedAngle) / Math.PI * 180));

                        if (Math.abs(angle) > CLICK_ANGLE){
                            droppedAngle += angle;
                            lastRotateX = event.getX();
                            lastRotateY = event.getY();
                            Vibrator vib = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vib.vibrate(50);

                            if (checkN < 5 && angle > 0)
                                checkN ++;
                            else if (checkN >=5  && angle < 0)
                                checkN ++;
                            else
                                failed = true;

                            if (!failed && checkN == 8){
                                elevator.openDoors();
                                handle.setVisibility(GONE);
                                handle.clearAnimation();
                            }
                        }
                        return true;
                }
                return false;
            }
        });
    }

    void rotateHandle(float angle){
        Animation an = new RotateAnimation(-angle, -angle, handle.getWidth()/2, handle.getHeight() / 2);
        an.setDuration(1);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        handle.startAnimation(an);
    }
}
