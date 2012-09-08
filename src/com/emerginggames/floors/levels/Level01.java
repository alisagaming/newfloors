package com.emerginggames.floors.levels;

import android.content.Context;
import android.view.View;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;
import com.emerginggames.floors.elevators.Elevator;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 21.08.12
 * Time: 9:41
 * To change this template use File | Settings | File Templates.
 */
public class Level01 extends Level {

    public Level01(LevelListener levelListener, Context context) {
        super(levelListener, context);
    }

    @Override
    protected void onElementClicked(View elementView) {
        switch (elementView.getId()){
            case R.id.up_btn:
                ((Elevator)findViewById(R.id.elevator)).openDoors();
                break;
        }
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_01;
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.up_btn).setOnClickListener(controlClickListener);
        findViewById(R.id.down_btn).setVisibility(View.INVISIBLE);
    }

/*    @Override
    protected void scaleViews() {
        scaleImageSize(R.id.up_btn);
        scaleImageSize(R.id.down_btn);
    }*/
}
