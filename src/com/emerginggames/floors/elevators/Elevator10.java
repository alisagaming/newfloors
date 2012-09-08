package com.emerginggames.floors.elevators;

import android.content.Context;
import android.util.AttributeSet;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 08.09.12
 * Time: 3:29
 * To change this template use File | Settings | File Templates.
 */
public class Elevator10 extends Elevator {
    public Elevator10(Context context) {
        super(context);
    }

    public Elevator10(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isOpening() {
        return false;
    }

    @Override
    public void openDoors() {
        findViewById(R.id.elevator_door).setVisibility(INVISIBLE);
        findViewById(R.id.elevator_door_open).setVisibility(VISIBLE);
        setDoorsOpen(true);
    }

    @Override
    public void closeDoors() {
        findViewById(R.id.elevator_door).setVisibility(VISIBLE);
        findViewById(R.id.elevator_door_open).setVisibility(INVISIBLE);
        setDoorsOpen(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.partial_elevator_10;
    }
}
