package com.emerginggames.floors.levels;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 01.09.12
 * Time: 23:52
 * To change this template use File | Settings | File Templates.
 */
public class Level05 extends Level {
    private static final int UPDATE_INTERVAL = 50;
    private static final float UPDATE_DECREASE_STEP = 0.01f;
    private static final float BAR_INCREASE_STEP = 0.05f;
    float currentBarPosition;
    boolean shouldRun;

    public Level05(LevelListener levelListener, Context context) {
        super(levelListener, context);
    }

    @Override
    protected void onElementClicked(View elementView) {
        currentBarPosition += BAR_INCREASE_STEP;
        setBarPosition(currentBarPosition);
        if (currentBarPosition >=1){
            elevator.openDoors();
            findViewById(R.id.button).setVisibility(GONE);
        }
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_05;
    }

    @Override
    protected void initView() {
        super.initView();

        scaleView(R.id.bar);
        scaleMargins(R.id.bar);
        scalePaddings(R.id.bar);

        scaleView(R.id.bar_fill);

        scaleImageSize(R.id.button);
        scaleMargins(R.id.button);

        setClicableElement(R.id.button);
        setBarPosition(currentBarPosition = 0);
    }

    void setBarPosition(float position){
        int maxWidth = rootView.findViewById(R.id.bar_fill).getWidth();
        View bar = rootView.findViewById(R.id.bar_inner);
        ViewGroup.LayoutParams lp = bar.getLayoutParams();
        lp.width = (int)(maxWidth * position);
        bar.setLayoutParams(lp);
    }

    @Override
    public void onPause() {
        super.onPause();
        shouldRun = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        shouldRun = true;
        if (!elevator.isOpen())
            postDelayed(decreaseBar, UPDATE_INTERVAL);
    }

    Runnable decreaseBar = new Runnable() {
        @Override
        public void run() {
            currentBarPosition -= UPDATE_DECREASE_STEP;
            if (currentBarPosition < 0)
                currentBarPosition = 0;
            setBarPosition(currentBarPosition);
            if (shouldRun && !elevator.isOpen())
                postDelayed(decreaseBar, UPDATE_INTERVAL);
        }
    };


}
