package com.emerginggames.floors.levels;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import com.emerginggames.floors.R;
import com.emerginggames.floors.util.Util;

import java.io.PushbackInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 10.09.12
 * Time: 0:15
 * To change this template use File | Settings | File Templates.
 */
public class Level13 extends Level {

    public Level13(LevelListener levelListener, Context context) {
        super(levelListener, context);
        elevator.debug();
        elevator.getDoorsView().setOnTouchListener(doorTouchListener);
        setControl(R.id.button);
    }

    @Override
    protected void onElementClicked(View elementView) {
        switch (elementView.getId()){
            case R.id.button:
                if (checkSolved())
                    elevator.openDoors();
                break;
        }
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_13;
    }

    boolean checkSolved(){
        boolean isOk = true;
        for (int i=0; i<14; i++){
            View v = getItemByNUmber(i);
            if (i%2 == 0 && v.getVisibility() != VISIBLE)
                isOk = false;
            else if (i%2 == 1 && v.getVisibility() == VISIBLE)
                isOk = false;
        }
        return isOk;
    }

    OnTouchListener doorTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_UP:
                    float angle = (float)(-Util.getAngleInView(v, event.getX(), event.getY()) + Math.PI/2);
                    if (angle < 0) angle += Math.PI * 2;
                    int itemN = (int)(angle / (2 * Math.PI /14f) );
                    if (itemN > 13)
                        itemN = 13;
                    View itemView = getItemByNUmber(itemN);
                    if (itemView == null)
                        return true;
                    if (itemView.getVisibility() != VISIBLE)
                        itemView.setVisibility(VISIBLE);
                    else
                        itemView.setVisibility(GONE);
                return true;
            }
            return false;
        }
    };

    View getItemByNUmber(int n){
        int id = getResources().getIdentifier("item" + Integer.toString(n+1), "id", getContext().getPackageName());
        return findViewById(id);
    }
}
