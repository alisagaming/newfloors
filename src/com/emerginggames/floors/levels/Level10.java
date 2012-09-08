package com.emerginggames.floors.levels;

import android.content.Context;
import android.os.Vibrator;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import com.emerginggames.floors.R;

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
    protected void scaleViews(){
        scaleImageSize(R.id.handle);
        scaleMargins(R.id.handle);

        scaleImageSize(R.id.arrow_left);
        scaleMargins(R.id.arrow_left);

        scaleImageSize(R.id.arrow_right);
        scaleMargins(R.id.arrow_right);
        scalePaddings(R.id.elevator);
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
                        float angle =  getAngle(v, lastRotateX, lastRotateY, event.getX(), event.getY());
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

    float getAngle(View v, double x1, double y1, double x2, double y2){
        int x0 = v.getWidth()/2;
        int y0 = v.getHeight()/2;

        x2 = x2 - x0;
        y2 = y2 - x0;
        x1 = x1 - x0;
        y1 = y1 - y0;

        double len = Math.sqrt( (x2 * x2 + y2 * y2) *(x1 * x1 + y1 * y1) );
        double sin = (x1 * y2 - y1 * x2 ) / len;
        double cos = (x1 * x2 + y1 * y2) / len;

        if (sin < 0)
            return (float)Math.acos(cos);
        else
            return - (float)Math.acos(cos);
    }

    void rotateHandle(float angle){
        Animation an = new RotateAnimation(-angle, -angle, handle.getWidth()/2, handle.getHeight() / 2);
        an.setDuration(1);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        handle.startAnimation(an);
    }
}
