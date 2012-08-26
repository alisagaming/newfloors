package com.emerginggames.floors.levels;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 25.08.12
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public class Elevator extends RelativeLayout {
    View leftDoor;
    View rightDoor;
    boolean leftDoorOpen;
    boolean rightDoorOpen;
    boolean openingLeft;
    boolean openingRight;

    public Elevator(Context context) {
        super(context);
        inflateView();
    }

    public Elevator(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateView();
    }

    public Elevator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflateView();
    }

    public View getElevatorInnerView(){
        return findViewById(R.id.elevator_inner);
    }

    public View getDoorsView(){
        return findViewById(R.id.elev_doors);
    }

    public boolean isOpen(){
        return leftDoorOpen && rightDoorOpen;
    }

    public boolean isOpening(){
        return openingLeft || openingRight;
    }

    public void reset(){
        leftDoor.clearAnimation();
        leftDoor.setVisibility(VISIBLE);
        rightDoor.clearAnimation();
        rightDoor.setVisibility(VISIBLE);
        leftDoorOpen = rightDoorOpen = false;
        findViewById(R.id.elev_doors).setVisibility(View.VISIBLE);
    }

    void inflateView(){
        addView(inflate(getContext(), R.layout.partial_elevator, null));
        leftDoor = findViewById(R.id.elevator_door_left);
        rightDoor = findViewById(R.id.elevator_door_right);
    }

    public void openDoors(){
        openDoorLeft();
        openDoorRight();
    }

    public void closeDoors(){
        closeLeftDoor();
        closeRightDor();
    }

    public void openDoorLeft(){
        leftDoor.clearAnimation();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.left_door_open);
        anim.setAnimationListener(leftDoorOpenListener);
        leftDoor.startAnimation(anim);
        openingLeft = true;
    }

    public void openDoorRight(){
        rightDoor.clearAnimation();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.right_door_open);
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
