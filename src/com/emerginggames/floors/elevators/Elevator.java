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
    boolean doorsOpen;
    public DoorOpenListener doorOpenListener;

    public Elevator(Context context) {
        super(context);
        if (Metrics.scale == 0)
            Metrics.setSizeFromView(this);
        inflateView();
        scaleViews();
    }

    public Elevator(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Metrics.scale == 0)
            Metrics.setSizeFromView(this);
        parseAttributes(attrs);
        inflateView();
        scaleViews();
    }

    void parseAttributes(AttributeSet attrs){}

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
    }

    public boolean isOpen(){
        return doorsOpen;
    }

    protected void setDoorsOpen(boolean isOpen){
        doorsOpen = isOpen;
        if (isOpen){
            //findViewById(R.id.elev_doors).setVisibility(GONE);
            findViewById(R.id.elevator_inner_arrow_up).setVisibility(VISIBLE);
            if (doorOpenListener != null)
                doorOpenListener.onDoorOpen();
        }else {
            //findViewById(R.id.elev_doors).setVisibility(VISIBLE);
            findViewById(R.id.elevator_inner_arrow_up).setVisibility(GONE);
        }
    }

    public void reset(){
        closeDoors();
    }

    public void debug(){
        findViewById(R.id.elev_doors).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen())
                    closeDoors();
                else
                    openDoors();
            }
        });
    }

    public void setDoorOpenListener(DoorOpenListener doorOpenListener) {
        this.doorOpenListener = doorOpenListener;
    }

    public abstract boolean isOpening();


    public void openDoors(){
        findViewById(R.id.elev_doors).setVisibility(GONE);
        findViewById(R.id.elevator_inner_arrow_up).setVisibility(VISIBLE);
        setDoorsOpen(true);
    }

    public void closeDoors(){
        findViewById(R.id.elev_doors).setVisibility(VISIBLE);
        findViewById(R.id.elevator_inner_arrow_up).setVisibility(GONE);
        setDoorsOpen(false);
    }

    public abstract int getLayoutId();

    public interface DoorOpenListener{
        public void onDoorOpen();
    }
}
