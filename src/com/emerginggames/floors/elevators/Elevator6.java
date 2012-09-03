package com.emerginggames.floors.elevators;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 02.09.12
 * Time: 19:40
 * To change this template use File | Settings | File Templates.
 */
public class Elevator6 extends Elevator {
    boolean openingDoors;
    boolean doorsOpen;

    public Elevator6(Context context) {
        super(context);
    }

    public Elevator6(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Elevator6(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isOpen() {
        return doorsOpen;
    }

    @Override
    public boolean isOpening() {
        return openingDoors;
    }

    @Override
    public void reset() {
        View leftDoor = findViewById(R.id.elevator_door_left);
        View rightDoor = findViewById(R.id.elevator_door_right);
        leftDoor.clearAnimation();
        rightDoor.clearAnimation();
        findViewById(R.id.elev_doors).setVisibility(VISIBLE);
        openingDoors = doorsOpen = false;
    }

    @Override
    public void openDoors() {
        View leftDoor = findViewById(R.id.elevator_door_left);
        View rightDoor = findViewById(R.id.elevator_door_right);
        leftDoor.clearAnimation();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_left_full);

        leftDoor.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_left_double);
        rightDoor.startAnimation(anim);
        anim.setAnimationListener(doorOpenListener);
        openingDoors = true;
    }

    @Override
    public void closeDoors() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.partial_elevator_06;
    }

    @Override
    protected void scaleViews() {
        scaleImage(R.id.elevator_frame);
        scaleImage(R.id.elevator_inner_img);
        scaleImage(R.id.elevator_inner_arrow_up);
        scaleMargins(R.id.elevator_inner);
        scaleImage(R.id.elevator_inner_arrow_up);
        scaleImage(R.id.elevator_door_left);
        scaleImage(R.id.elevator_door_right);
    }

    public View getItem(int n){
        switch (n){
            case 1:
                return findViewById(R.id.item1);
            case 2:
                return findViewById(R.id.item2);
            case 3:
                return findViewById(R.id.item3);
            case 4:
                return findViewById(R.id.item4);
        }
        return null;
    }

    Animation.AnimationListener doorOpenListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            doorsOpen = true;
            openingDoors =false;
            findViewById(R.id.elev_doors).setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };
}
