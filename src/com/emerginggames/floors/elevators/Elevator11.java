package com.emerginggames.floors.elevators;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 08.09.12
 * Time: 9:00
 * To change this template use File | Settings | File Templates.
 */
public class Elevator11 extends Elevator {
    boolean isOpening;
    public Elevator11(Context context) {
        super(context);
    }

    public Elevator11(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isOpening() {
        return isOpening;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partial_elevator_11;
    }

    @Override
    public void openDoors() {
        if (isOpening)
            return;

        isOpening = true;
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        findViewById(R.id.elev_doors).startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.elev_doors).setVisibility(GONE);
                setDoorsOpen(true);
                isOpening = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }


}
