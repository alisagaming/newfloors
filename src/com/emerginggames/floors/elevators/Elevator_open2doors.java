package com.emerginggames.floors.elevators;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 01.09.12
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
public class Elevator_open2doors extends Elevator {
    int doorMargin;
    int frameMargin;
    int innerPaddingBottom;
    Drawable frameDrawable;
    Drawable leftDoorDrawable;
    Drawable rightDoorDrawable;
    Drawable leftDoorOpenDrawable;
    Drawable rightDoorOpenDrawable;

    public Elevator_open2doors(Context context) {
        super(context);
    }

    public Elevator_open2doors(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    void parseAttributes(AttributeSet attrs) {
        super.parseAttributes(attrs);

        TypedArray styledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.Elevator_open2doors);
        frameDrawable = styledAttributes.getDrawable(R.styleable.Elevator_open2doors_frame);

        leftDoorDrawable = styledAttributes.getDrawable(R.styleable.Elevator_open2doors_leftDoor);
        rightDoorDrawable = styledAttributes.getDrawable(R.styleable.Elevator_open2doors_rightDoor);
        leftDoorOpenDrawable = styledAttributes.getDrawable(R.styleable.Elevator_open2doors_leftDoorOpen);
        rightDoorOpenDrawable = styledAttributes.getDrawable(R.styleable.Elevator_open2doors_rightDoorOpen);

        String valueStr = styledAttributes.getString(R.styleable.Elevator_open2doors_openDoorMargin);
        float value;
        if (valueStr != null) {
            value = styledAttributes.getDimension(R.styleable.Elevator_open2doors_openDoorMargin, 0);
            doorMargin = (int) (valueStr.contains("px") ? value * Metrics.scale : value);
        }

        valueStr = styledAttributes.getString(R.styleable.Elevator_open2doors_frameMarginTop);
        if (valueStr != null) {
            value = styledAttributes.getDimension(R.styleable.Elevator_open2doors_frameMarginTop, 0);
            frameMargin = (int) (valueStr.contains("px") ? value * Metrics.scale : value);
        }

        valueStr = styledAttributes.getString(R.styleable.Elevator_open2doors_innerPaddingBottom);
        if (valueStr != null) {
            value = styledAttributes.getDimension(R.styleable.Elevator_open2doors_innerPaddingBottom, 0);
            innerPaddingBottom = (int) (valueStr.contains("px") ? value * Metrics.scale : value);
        }

        styledAttributes.recycle();
    }

    @Override
    public boolean isOpening() {
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        closeDoors();
    }

    @Override
    public int getLayoutId() {
        return R.layout.partial_elevator_2dors_open;
    }

    @Override
    protected void inflateView() {
        super.inflateView();
        if (frameDrawable != null)
            ((ImageView) findViewById(R.id.elevator_frame)).setImageDrawable(frameDrawable);
        if (leftDoorDrawable != null)
            ((ImageView) findViewById(R.id.elevator_door_left)).setImageDrawable(leftDoorDrawable);
        if (rightDoorDrawable != null)
            ((ImageView) findViewById(R.id.elevator_door_right)).setImageDrawable(rightDoorDrawable);
        if (leftDoorOpenDrawable != null)
            ((ImageView) findViewById(R.id.elevator_door_left_open)).setImageDrawable(leftDoorOpenDrawable);
        if (rightDoorOpenDrawable != null)
            ((ImageView) findViewById(R.id.elevator_door_right_open)).setImageDrawable(rightDoorOpenDrawable);
        frameDrawable = leftDoorDrawable = rightDoorDrawable = leftDoorOpenDrawable = rightDoorOpenDrawable = null;
    }

    @Override
    protected void scaleViews() {
        scaleImage(R.id.elevator_frame);

        scaleImage(R.id.elevator_door_left);
        scaleImage(R.id.elevator_door_right);

        scaleImage(R.id.elevator_door_left_open);
        scaleImage(R.id.elevator_door_right_open);

        ((MarginLayoutParams) findViewById(R.id.elevator_door_left_open).getLayoutParams()).rightMargin = doorMargin;
        ((MarginLayoutParams) findViewById(R.id.elevator_door_right_open).getLayoutParams()).leftMargin = doorMargin;
        ((MarginLayoutParams) findViewById(R.id.elevator_frame).getLayoutParams()).topMargin = frameMargin;
        findViewById(R.id.elevator_inner).setPadding(0, 0, 0, innerPaddingBottom);

        scaleImage(R.id.elevator_inner_img);
        scaleImage(R.id.elevator_inner_arrow_up);
    }

    @Override
    public void openDoors() {
        findViewById(R.id.elevator_door_left).setVisibility(INVISIBLE);
        findViewById(R.id.elevator_door_right).setVisibility(INVISIBLE);
        findViewById(R.id.elevator_door_left_open).setVisibility(VISIBLE);
        findViewById(R.id.elevator_door_right_open).setVisibility(VISIBLE);
        setDoorsOpen(true);
    }

    @Override
    public void closeDoors() {
        findViewById(R.id.elevator_door_left).setVisibility(VISIBLE);
        findViewById(R.id.elevator_door_right).setVisibility(VISIBLE);
        findViewById(R.id.elevator_door_left_open).setVisibility(INVISIBLE);
        findViewById(R.id.elevator_door_right_open).setVisibility(INVISIBLE);
        setDoorsOpen(false);
    }

/*    @Override
    protected void setDoorsOpen(boolean isOpen) {
        doorsOpen = isOpen;
        findViewById(R.id.elevator_inner_arrow_up).setVisibility(isOpen ? VISIBLE : GONE);
        if (doorOpenListener != null)
            doorOpenListener.onDoorOpen();
    }*/

    public View getLeftDoor(){
        return findViewById(R.id.elevator_door_left);
    }

    public View getRightDoor(){
        return findViewById(R.id.elevator_door_right);
    }

    public void unlock(){
        View.OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                openDoors();
            }
        };

        findViewById(R.id.elevator_door_left).setOnClickListener(listener);
        findViewById(R.id.elevator_door_right).setOnClickListener(listener);
    }
}
