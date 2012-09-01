package com.emerginggames.floors.elevators;

import android.content.Context;
import android.util.AttributeSet;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 01.09.12
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class Elevator5 extends Elevator_open2doors {
    public Elevator5(Context context) {
        super(context);
    }

    public Elevator5(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Elevator5(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int[] getImageIdArray() {
        return new int[] {
                R.drawable.elevator5_frame,
                R.drawable.elev5_door_l,
                R.drawable.elev5_door_r,
                R.drawable.elev5_door_l_open,
                R.drawable.elev5_door_r_open,};
    }

    @Override
    protected void inflateView() {
        super.inflateView();
        setScaledMargin(R.id.elevator_door_left_open, 0, 0, -2, 0);
        setScaledMargin(R.id.elevator_door_right_open, -2, 0, 0, 0);
        findViewById(R.id.elevator_frame).setPadding(0, (int)(15 * Metrics.scale), 0, 0);
    }
}
