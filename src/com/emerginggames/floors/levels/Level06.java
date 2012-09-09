package com.emerginggames.floors.levels;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import com.emerginggames.floors.R;
import com.emerginggames.floors.Settings;
import com.emerginggames.floors.elevators.Elevator6;
import com.emerginggames.floors.items.ItemNotepadLevel3;
import com.emerginggames.floors.model.Item;

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
        items = new SparseArray<Item>(1);
        items.append(R.id.note, new ItemNotepadLevel3(1, R.drawable.fl3_tool_note, true));
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
        if (n == 4){
            ((Elevator6)elevator).unlockDoors();
            if (Settings.DEBUG)
                findViewById(R.id.arrow).setVisibility(VISIBLE);
        }
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
        if (Settings.DEBUG)
            findViewById(R.id.arrow).setVisibility(GONE);
    }
}
