package com.emerginggames.floors.levels;

import android.content.Context;
import android.view.View;
import com.emerginggames.floors.R;
import com.emerginggames.floors.elevators.Elevator6;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 02.09.12
 * Time: 19:41
 * To change this template use File | Settings | File Templates.
 */
public class Level06 extends Level {
    int lastClickedItem = 0;
    boolean failed = false;

    public Level06(LevelListener levelListener, Context context) {
        super(levelListener, context);
    }

    @Override
    protected void onElementClicked(View elementView) {
        if (failed || elevator.isOpening() || elevator.isOpen())
            return;
        int n = (Integer)elementView.getTag();
        if (lastClickedItem != n-1){
            failed = true;
            return;
        }

        lastClickedItem = n;
        if (n == 4)
            elevator.openDoors();
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_06;
    }

    @Override
    protected void initView() {
        super.initView();
        Elevator6 elev = (Elevator6)elevator;
        setControl(elev.getItem(1), 1);
        setControl(elev.getItem(2), 2);
        setControl(elev.getItem(3), 3);
        setControl(elev.getItem(4), 4);
    }

    @Override
    public void start() {
        super.start();
        lastClickedItem = 0;
        failed = false;
    }
}
