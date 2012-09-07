package com.emerginggames.floors.levels;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.emerginggames.floors.R;
import com.emerginggames.floors.elevators.Elevator;
import com.emerginggames.floors.elevators.Elevator_open2doors;
import com.emerginggames.floors.items.ItemNotepadLevel3;
import com.emerginggames.floors.model.Item;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 07.09.12
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public class Level08 extends Level {
    ImageView rightDoorPlacement;
    ImageView rightDoorHandle;

    public Level08(LevelListener levelListener, Context context) {
        super(levelListener, context);
        items = new SparseArray<Item>();
        items.append(R.id.handle, new Item(1, R.drawable.level08_handle_icon));
        items.append(R.id.note, new ItemNotepadLevel3(1, R.drawable.fl3_tool_note, true));
    }

    @Override
    protected void onElementClicked(View elementView) {
        switch (elementView.getId()){
            case R.id.plate:
                findViewById(R.id.plate).setVisibility(GONE);
                break;
            case R.id.elevator_door_right:
                if (levelListener.getCurrentItem() != null && levelListener.getCurrentItem().itemId == 1){
                    levelListener.removeCurrentItem();
                    rightDoorPlacement.setVisibility(GONE);
                    rightDoorHandle.setVisibility(VISIBLE);
                    ((Elevator_open2doors)elevator).unlock();
                    elevator.setDoorOpenListener(new Elevator.DoorOpenListener() {
                        @Override
                        public void onDoorOpen() {
                            rightDoorHandle.setVisibility(GONE);
                        }
                    });
                }
                break;

        }
    }



    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_08;
    }

    @Override
    protected void scaleViews() {
        scaleImageSize(R.id.plate);
        scaleMargins(R.id.plate);

        scaleImageSize(R.id.handle);
        scaleMargins(R.id.handle);

        scaleImageSize(R.id.handle_placement);
        scaleMargins(R.id.handle_placement);

        scaleImageSize(R.id.right_door_handle);
        scaleMargins(R.id.right_door_handle);
    }

    @Override
    protected void initView() {
        super.initView();

        int rightDoorId = ((Elevator_open2doors)elevator).getRightDoor().getId();
        RelativeLayout doorCont = (RelativeLayout)elevator.getDoorsView();

        rightDoorPlacement = new ImageView(getContext());
        rightDoorPlacement.setImageResource(R.drawable.elev08_door_handle_placement);
        rightDoorPlacement.setId(R.id.handle_placement);

        RelativeLayout.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_LEFT, rightDoorId);
        lp.leftMargin = 3;
        lp.topMargin = 175;

        doorCont.addView(rightDoorPlacement, lp);

        setControl(R.id.plate);
        setControl(rightDoorId);


        rightDoorHandle = new ImageView(getContext());
        rightDoorHandle.setImageResource(R.drawable.elev08_door_handle2);
        rightDoorHandle.setVisibility(GONE);
        rightDoorHandle.setId(R.id.right_door_handle);
        lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_LEFT, rightDoorId);
        lp.leftMargin = 2;
        lp.topMargin = 174;
        doorCont.addView(rightDoorHandle, lp);


    }
}
