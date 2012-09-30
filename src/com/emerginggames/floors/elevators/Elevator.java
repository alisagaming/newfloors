package com.emerginggames.floors.elevators;

import android.content.Context;
import android.content.res.TypedArray;
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
    protected static final int DOOR_OPEN_DURATION = 1000;
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

    int parseScaledAttribute(TypedArray styledAttributes, int attributeId){
        String valueStr = styledAttributes.getString(attributeId);
        float value;
        if (valueStr == null)
            return 0;

        value = styledAttributes.getDimension(attributeId, 0);
        return (int) (valueStr.contains("px") ? value * Metrics.scale : value);
    }


    public View getElevatorInnerView(){
        return findViewById(R.id.elevator_inner);
    }

    public ViewGroup getDoorsView(){
        return (ViewGroup)findViewById(R.id.elev_doors);
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
        closeDoorsNow();
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
        closeDoorsNow();
    }

    public void closeDoorsNow(){
        findViewById(R.id.elev_doors).setVisibility(VISIBLE);
        findViewById(R.id.elevator_inner_arrow_up).setVisibility(GONE);
        setDoorsOpen(false);
    }

    public abstract int getLayoutId();

    public interface DoorOpenListener{
        public void onDoorOpen();
    }
}
