package com.emerginggames.floors.levels;

import android.content.Context;
import android.view.View;
import com.emerginggames.floors.R;

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

        scaleImageSize(R.id.button);
        scaleMargins(R.id.button);
        scaleImageSize(R.id.label2);
        scaleImageSize(R.id.label1);
        scaleMargins(R.id.label1);
    }

    @Override
    public void start() {
        super.start();
        clickCounter = 0;
    }
}
