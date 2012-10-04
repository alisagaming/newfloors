package com.emerginggames.floors.elevators;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.emerginggames.floors.R;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 04.10.12
 * Time: 10:59
 * To change this template use File | Settings | File Templates.
 */
public class ElevatorNoDoors extends Elevator {
    int innerMarginTop;
    int frameImageId;


    public ElevatorNoDoors(Context context) {
        super(context);
    }

    public ElevatorNoDoors(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    void parseAttributes(AttributeSet attrs) {
        super.parseAttributes(attrs);
        TypedArray styledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.ElevatorNoDoors);

        frameImageId = styledAttributes.getResourceId(R.styleable.Elevator_2DoorsSlide_frame, 0);

        innerMarginTop = parseScaledAttribute(styledAttributes, R.styleable.ElevatorNoDoors_innerMarginTop);

        styledAttributes.recycle();
    }

    @Override
    public boolean isOpening() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partial_elevator_no_doors;
    }

    @Override
    public ViewGroup getDoorsView() {
        return null;
    }

    @Override
    public void openDoors() {
        setDoorsOpen(true);
    }

    @Override
    public void closeDoorsNow() {
        setDoorsOpen(false);
    }

    @Override
    protected void inflateView() {
        super.inflateView();

        if (frameImageId != 0)
            ((ImageView)findViewById(R.id.elevator_frame)).setImageResource(frameImageId);
    }

    @Override
    protected void scaleViews() {
        super.scaleViews();

        if (innerMarginTop != 0)
            ((MarginLayoutParams)findViewById(R.id.elevator_inner).getLayoutParams()).topMargin = innerMarginTop;
    }
}
