package com.emerginggames.floors.elevators;

import android.content.Context;
import android.util.AttributeSet;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 01.09.12
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
public class Elevator4 extends Elevator_open2doors {

    public Elevator4(Context context) {
        super(context);
    }

    public Elevator4(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Elevator4(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.partial_elevator_2dors_open;
    }

    @Override
    protected int[] getImageIdArray() {
        return new int[] {
                R.drawable.elev4_frame,
                R.drawable.elev4_door_l,
                R.drawable.elev4_door_r,
                R.drawable.elev4_door_l_open,
                R.drawable.elev4_door_r_open,};
    }

    @Override
    protected void inflateView() {
        super.inflateView();
        setScaledMargin(R.id.elevator_door_left_open, 0, 0, -6, 0);
        setScaledMargin(R.id.elevator_door_right_open, -6, 0, 0, 0);
        findViewById(R.id.elevator_frame).setPadding(0, (int)(17 * Metrics.scale), 0, 0);
    }
}
