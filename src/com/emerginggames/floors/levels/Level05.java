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
    boolean running;
    boolean focused;

    public Level05(LevelListener levelListener, Context context) {
        super(levelListener, context);
    }

    @Override
    protected void onElementClicked(View elementView) {
        currentBarPosition += BAR_INCREASE_STEP;
        setBarPosition(currentBarPosition);
        if (currentBarPosition >= 1) {
            elevator.openDoors();
            findViewById(R.id.button).setVisibility(GONE);
            shouldRun = false;
        }
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_05;
    }

    @Override
    public void start() {
        super.start();
        synchronized (this) {
            setBarPosition(currentBarPosition = 0);
            findViewById(R.id.button).setVisibility(VISIBLE);
            shouldRun = true;
            if (!running)
                post(decreaseBar);
            running = true;
        }
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

        setControl(R.id.button);
    }

    void setBarPosition(float position) {
        int maxWidth = rootView.findViewById(R.id.bar_fill).getWidth();
        View bar = rootView.findViewById(R.id.bar_inner);
        ViewGroup.LayoutParams lp = bar.getLayoutParams();
        lp.width = (int) (maxWidth * position);
        bar.setLayoutParams(lp);
    }

    @Override
    public void onPause() {
        super.onPause();
        focused = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        synchronized (this) {
            focused = true;
            if (shouldRun && !running && !elevator.isOpen()) {
                post(decreaseBar);
                running = true;
            }
        }
    }

    Runnable decreaseBar = new Runnable() {
        @Override
        public void run() {
            synchronized (this) {
                currentBarPosition -= UPDATE_DECREASE_STEP;
                if (currentBarPosition < 0)
                    currentBarPosition = 0;
                setBarPosition(currentBarPosition);
                if (focused && !elevator.isOpen())
                    postDelayed(decreaseBar, UPDATE_INTERVAL);
                else running = false;
            }
        }
    };


}