package com.emerginggames.floors.levels;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import com.emerginggames.floors.R;
import com.emerginggames.floors.items.ItemNotepadLevel3;
import com.emerginggames.floors.model.Item;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 01.09.12
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class Level04 extends Level {
    int clickCounter;

    public Level04(LevelListener levelListener, Context context) {
        super(levelListener, context);
        items = new SparseArray<Item>(1);
        items.append(R.id.note, new ItemNotepadLevel3(1, R.drawable.fl3_tool_note, true));
    }

    @Override
    protected void onElementClicked(View elementView) {
        clickCounter++;
        if (clickCounter == 4)
            elevator.openDoors();
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_04;
    }

    @Override
    protected void initView() {
        super.initView();
        setControl(R.id.button);
    }

    @Override
    public void start() {
        super.start();
        clickCounter = 0;
    }
}
