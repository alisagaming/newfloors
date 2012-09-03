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
 * Date: 01.09.12
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class Elevator1 extends Elevator {
    View leftDoor;
    View rightDoor;
    boolean leftDoorOpen;
    boolean rightDoorOpen;
    boolean openingLeft;
    boolean openingRight;

    public Elevator1(Context context) {
        super(context);
    }

    public Elevator1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Elevator1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isOpen(){
        return leftDoorOpen && rightDoorOpen;
    }

    @Override
    public boolean isOpening(){
        return openingLeft || openingRight;
    }

    @Override
    public void reset(){
        leftDoor.clearAnimation();
        leftDoor.setVisibility(VISIBLE);
        rightDoor.clearAnimation();
        rightDoor.setVisibility(VISIBLE);
        leftDoorOpen = rightDoorOpen = false;
        getDoorsView().setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.partial_elevator_01;
    }

    @Override
    protected void inflateView(){
        super.inflateView();
        leftDoor = findViewById(R.id.elevator_door_left);
        rightDoor = findViewById(R.id.elevator_door_right);
    }

    @Override
    protected void scaleViews() {
        scaleImage(R.id.elevator_frame);
        scaleImage(R.id.elevator_inner_img);
        scaleImage(R.id.elevator_inner_arrow_up);
        scalePadding(R.id.elevator_inner_img);
        scalePadding(R.id.elev_doors);
    }

    @Override
    public void openDoors(){
        openDoorLeft();
        openDoorRight();
    }

    @Override
    public void closeDoors(){
        closeLeftDoor();
        closeRightDor();
    }

    public void openDoorLeft(){
        leftDoor.clearAnimation();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_left_full);
        anim.setAnimationListener(leftDoorOpenListener);
        leftDoor.startAnimation(anim);
        openingLeft = true;
    }

    public void openDoorRight(){
        rightDoor.clearAnimation();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_right_full);
        anim.setAnimationListener(rightDoorOpenListener);
        rightDoor.startAnimation(anim);
        openingRight = true;
    }

    public void closeLeftDoor(){
        leftDoorOpen = false;
        openingLeft =false;
        leftDoor.clearAnimation();
        leftDoor.setVisibility(VISIBLE);
    }

    public void closeRightDor(){
        rightDoorOpen = false;
        openingRight =false;
        rightDoor.clearAnimation();
        rightDoor.setVisibility(VISIBLE);
    }

    Animation.AnimationListener leftDoorOpenListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            leftDoor.setVisibility(View.INVISIBLE);
            leftDoorOpen = true;
            openingLeft =false;
            if (isOpen())
                findViewById(R.id.elev_doors).setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

    Animation.AnimationListener rightDoorOpenListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            rightDoor.setVisibility(View.INVISIBLE);
            rightDoorOpen = true;
            openingRight =false;
            if (isOpen())
                findViewById(R.id.elev_doors).setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

}
