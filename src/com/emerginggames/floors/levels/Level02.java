package com.emerginggames.floors.levels;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import com.emerginggames.floors.R;
import com.emerginggames.floors.model.Item;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 26.08.12
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */
public class Level02 extends Level {
    public Level02(LevelListener levelListener, Context context) {
        super(levelListener, context);
        items = new SparseArray<Item>(1);
        items.append(R.id.key, new Item(1, R.drawable.fl2_tool_key));
    }

    @Override
    protected void onElementClicked(View elementView) {
        switch (elementView.getId()){
            case R.id.elev_doors:
                Item item = levelListener.getCurrentItem();
                if (item != null && item.itemId == 1){
                    elevator.openDoors();
                    levelListener.removeCurrentItem();
                }
                break;
        }
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_02;
    }

    @Override
    protected void initView() {
        super.initView();
        elevator.getDoorsView().setOnClickListener(controlClickListener);
    }

    @Override
    public void start() {
        super.start();
    }

/*    @Override
    protected void scaleViews() {
        scaleImageSize(R.id.up_btn);
        scaleImageSize(R.id.down_btn);

        scaleImageSize(R.id.key);
        scaleMargins(R.id.key);
    }*/
}
