package com.emerginggames.floors.elevators;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;
import com.emerginggames.floors.view.ScaledRelativeLayout;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 25.08.12
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class Elevator extends ScaledRelativeLayout {

    public Elevator(Context context) {
        super(context);
        if (Metrics.scale == 0)
            Metrics.setSizeFromView(this);
        inflateView();
    }

    public Elevator(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Metrics.scale == 0)
            Metrics.setSizeFromView(this);
        inflateView();
    }

    public Elevator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (Metrics.scale == 0)
            Metrics.setSizeFromView(this);
        inflateView();
    }

    public View getElevatorInnerView(){
        return findViewById(R.id.elevator_inner);
    }

    public View getDoorsView(){
        return findViewById(R.id.elev_doors);
    }

    protected void setImageAndScale(int id, int drawableId){
        ImageView image = (ImageView)findViewById(id);
        image.setImageResource(drawableId);
        ViewGroup.LayoutParams lp = image.getLayoutParams();
        int width = image.getDrawable().getIntrinsicWidth();
        lp.width = (int)(width * Metrics.scale);
    }


    protected void inflateView(){
        addView(inflate(getContext(), getLayoutId(), null));
        scaleViews();
    }

    public abstract boolean isOpen();
    public abstract boolean isOpening();
    public abstract void reset();

    public abstract void openDoors();
    public abstract void closeDoors();
    public abstract int getLayoutId();
}
