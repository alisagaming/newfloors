package com.emerginggames.floors.elevators;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import com.emerginggames.floors.R;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 09.09.12
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */
public class Elevator_2DoorsSlide extends Elevator {
    boolean isOpening;

    int frameImageId;
    int leftDoorImageId;
    int rightDoorImageId;
    int leftDoorOverlayId;
    int rightDoorOverlayId;
    int innerMarginTop;
    View leftOverlay;
    View rightOverlay;

    public Elevator_2DoorsSlide(Context context) {
        super(context);
    }

    public Elevator_2DoorsSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    void parseAttributes(AttributeSet attrs) {
        super.parseAttributes(attrs);

        TypedArray styledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.Elevator_2DoorsSlide);

        frameImageId = styledAttributes.getResourceId(R.styleable.Elevator_2DoorsSlide_frame, 0);
        leftDoorImageId = styledAttributes.getResourceId(R.styleable.Elevator_2DoorsSlide_leftDoor, 0);
        rightDoorImageId = styledAttributes.getResourceId(R.styleable.Elevator_2DoorsSlide_rightDoor, 0);

        leftDoorOverlayId = styledAttributes.getResourceId(R.styleable.Elevator_2DoorsSlide_leftDoorOverlay, 0);
        rightDoorOverlayId = styledAttributes.getResourceId(R.styleable.Elevator_2DoorsSlide_rightDoorOverlay, 0);

        innerMarginTop = parseScaledAttribute(styledAttributes, R.styleable.Elevator_2DoorsSlide_innerMarginTop);

        styledAttributes.recycle();
    }

    @Override
    public boolean isOpening() {
        return isOpening;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partial_elevator_2doors_slide;
    }

    @Override
    protected void inflateView() {
        super.inflateView();

        if (frameImageId != 0)
            ((ImageView)findViewById(R.id.elevator_frame)).setImageResource(frameImageId);
        if (leftDoorImageId != 0)
            ((ImageView)findViewById(R.id.elevator_door_left)).setImageResource(leftDoorImageId);
        if (rightDoorImageId != 0)
            ((ImageView)findViewById(R.id.elevator_door_right)).setImageResource(rightDoorImageId);

        if (leftDoorOverlayId != 0)
            leftOverlay = addOverlay(R.id.elev_doors, leftDoorOverlayId,  R.id.elevator_door_left);
        if (rightDoorOverlayId != 0)
            rightOverlay = addOverlay(R.id.elev_doors, rightDoorOverlayId,  R.id.elevator_door_right);
    }

    @Override
    protected void scaleViews() {
        super.scaleViews();

        if (innerMarginTop != 0)
            ((MarginLayoutParams)findViewById(R.id.elevator_inner).getLayoutParams()).topMargin = innerMarginTop;
    }

    View addOverlay(int rootId, int id, int alignViewId){
        RelativeLayout root = (RelativeLayout)findViewById(rootId);
        View overlay = inflate(getContext(), id, null);
        RelativeLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_LEFT, alignViewId);
        lp.addRule(RelativeLayout.ALIGN_RIGHT, alignViewId);
        lp.addRule(RelativeLayout.ALIGN_TOP, alignViewId);
        lp.addRule(RelativeLayout.ALIGN_BOTTOM, alignViewId);
        root.addView(overlay, lp);
        return overlay;
    }

    @Override
    public void openDoors() {
        if (isOpening)
            return;

        Animation leftAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
        Animation rightAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);

        leftAnim.setDuration(DOOR_OPEN_DURATION);
        rightAnim.setDuration(DOOR_OPEN_DURATION);
        findViewById(R.id.elevator_door_left).startAnimation(leftAnim);
        findViewById(R.id.elevator_door_right).startAnimation(rightAnim);

        if (leftOverlay != null)
            leftOverlay.setAnimation(leftAnim);

        if (rightOverlay != null)
            rightOverlay.setAnimation(rightAnim);

        leftAnim.start();
        rightAnim.start();
        isOpening = true;

        rightAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isOpening = false;
                setDoorsOpen(true);
                findViewById(R.id.elev_doors).setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
