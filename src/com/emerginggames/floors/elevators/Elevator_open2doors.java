package com.emerginggames.floors.elevators;

import android.content.Context;
import android.util.AttributeSet;
import com.emerginggames.floors.R;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 01.09.12
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class Elevator_open2doors extends Elevator {
    boolean isOpen;

    public Elevator_open2doors(Context context) {
        super(context);
    }

    public Elevator_open2doors(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Elevator_open2doors(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public boolean isOpening() {
        return false;
    }

    @Override
    public void reset() {
        closeDoors();
    }

    @Override
    public int getLayoutId() {
        return R.layout.partial_elevator_2dors_open;
    }

    // frame, left door, right door, leftDoorOpen, RightDoorOpen
    protected abstract int[] getImageIdArray();

    @Override
    protected void scaleViews() {
        int[] imageIds = getImageIdArray();

        setImageAndScale(R.id.elevator_frame, imageIds[0]);
        scalePadding(R.id.elevator_frame);

        setImageAndScale(R.id.elevator_door_left, imageIds[1]);
        setImageAndScale(R.id.elevator_door_right, imageIds[2]);

        setImageAndScale(R.id.elevator_door_left_open, imageIds[3]);
        setImageAndScale(R.id.elevator_door_right_open, imageIds[4]);
        scaleMargins(R.id.elevator_door_left_open);
        scaleMargins(R.id.elevator_door_right_open);

        scaleImage(R.id.elevator_inner_img);
        scaleImage(R.id.elevator_inner_arrow_up);
    }

    @Override
    public void openDoors() {
        findViewById(R.id.elevator_door_left).setVisibility(INVISIBLE);
        findViewById(R.id.elevator_door_right).setVisibility(INVISIBLE);
        findViewById(R.id.elevator_door_left_open).setVisibility(VISIBLE);
        findViewById(R.id.elevator_door_right_open).setVisibility(VISIBLE);
        isOpen = true;
    }

    @Override
    public void closeDoors() {
        findViewById(R.id.elevator_door_left).setVisibility(VISIBLE);
        findViewById(R.id.elevator_door_right).setVisibility(VISIBLE);
        findViewById(R.id.elevator_door_left_open).setVisibility(INVISIBLE);
        findViewById(R.id.elevator_door_right_open).setVisibility(INVISIBLE);
        isOpen = false;
    }


}
