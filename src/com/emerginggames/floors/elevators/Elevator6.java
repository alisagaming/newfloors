package com.emerginggames.floors.elevators;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
    boolean doorsUnlocked;
    boolean mTracking;
    float slideStartPosition;
    int minPosition;
    float rightDoorPosition;
    float leftDoorPosition;

    public Elevator6(Context context) {
        super(context);
    }

    public Elevator6(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isOpening() {
        return doorsUnlocked;
    }

    public void unlockDoors(){
        doorsUnlocked = true;
        findViewById(R.id.item1).setVisibility(GONE);
        findViewById(R.id.item2).setVisibility(GONE);
        findViewById(R.id.item3).setVisibility(GONE);
        findViewById(R.id.item4).setVisibility(GONE);
        findViewById(R.id.elevator_door_right).setOnTouchListener(doorTouchListener);
    }

    @Override
    public void reset() {
        View leftDoor = findViewById(R.id.elevator_door_left);
        View rightDoor = findViewById(R.id.elevator_door_right);
        leftDoor.clearAnimation();
        rightDoor.clearAnimation();
        findViewById(R.id.elev_doors).setVisibility(VISIBLE);
        openingDoors = doorsOpen = false;
        doorsUnlocked = false;

        findViewById(R.id.item1).setVisibility(VISIBLE);
        findViewById(R.id.item2).setVisibility(VISIBLE);
        findViewById(R.id.item3).setVisibility(VISIBLE);
        findViewById(R.id.item4).setVisibility(VISIBLE);
        findViewById(R.id.elevator_door_right).setOnTouchListener(null);
    }

    @Override
    public void openDoors() {
        /*View leftDoor = findViewById(R.id.elevator_door_left);
        View rightDoor = findViewById(R.id.elevator_door_right);
        leftDoor.clearAnimation();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_left_full);

        leftDoor.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_left_double);
        rightDoor.startAnimation(anim);
        anim.setAnimationListener(doorOpenListener);
        openingDoors = true;*/
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

/*    Animation.AnimationListener doorOpenListener = new Animation.AnimationListener() {
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
    };*/

    void moveDoors(float offset){
        float newRightPos = rightDoorPosition + offset;
        if (newRightPos > 0)
            newRightPos = 0;
        if (newRightPos < minPosition)
            newRightPos = minPosition;


        float newLeftPos = newRightPos/2;

        int rightDoorDiff = (int)(newRightPos - rightDoorPosition);
        int leftDoorDiff = (int)(newLeftPos - leftDoorPosition);

        findViewById(R.id.elevator_door_right).offsetLeftAndRight(rightDoorDiff);
        findViewById(R.id.elevator_door_left).offsetLeftAndRight(leftDoorDiff);
        rightDoorPosition = rightDoorPosition + rightDoorDiff;
        leftDoorPosition = leftDoorPosition + leftDoorDiff;
        findViewById(R.id.elev_doors).invalidate();
    }

    OnTouchListener doorTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (!doorsUnlocked)
                return true;
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(mTracking)
                        return true;
                    mTracking = true;
                    slideStartPosition = event.getX();
                    minPosition = findViewById(R.id.elevator_door_left).getWidth() * -2;
                    leftDoorPosition = 0;
                    rightDoorPosition = 0;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (mTracking)
                        moveDoors(event.getX() - slideStartPosition );
                    return true;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (!mTracking)
                        return true;
                    mTracking = false;

                    if (rightDoorPosition - minPosition <10)
                        setDoorsOpen(true);

                    return true;
            }
            return false;
        }
    };
}
