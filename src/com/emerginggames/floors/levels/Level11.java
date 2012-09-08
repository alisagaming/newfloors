package com.emerginggames.floors.levels;

import android.content.Context;
import android.view.View;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 08.09.12
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
public class Level11 extends Level {
    private static final String ANSWER = "TTSFFFWW";
    int currentStep = 0;
    boolean failed = false;

    public Level11(LevelListener levelListener, Context context) {
        super(levelListener, context);
    }

    @Override
    protected void onElementClicked(View elementView) {
        Character currentClick = 0;
        switch (elementView.getId()){
            case R.id.itemFlower:
                currentClick = 'F';
                break;
            case R.id.itemSun:
                currentClick = 'S';
                break;
            case R.id.itemTree:
                currentClick = 'T';
                break;
            case R.id.itemWater:
                currentClick = 'W';
                break;
        }

        if (ANSWER.charAt(currentStep) == currentClick)
            currentStep++;
        else
            failed = true;

        if (currentStep == 8 && ! failed)
            elevator.openDoors();

    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_11;
    }

    @Override
    protected void initView() {
        super.initView();
        setControl(R.id.itemFlower);
        setControl(R.id.itemSun);
        setControl(R.id.itemTree);
        setControl(R.id.itemWater);
    }

    @Override
    protected void scaleViews() {
        scaleMargins(elevator);
        scaleImage(R.id.hint);
        scaleMargins(R.id.hint);
    }
}
