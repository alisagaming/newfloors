package com.emerginggames.floors.elevators;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.emerginggames.floors.R;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 09.09.12
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public class Elevator_oneDoorSlide extends Elevator {

    private enum Direction {UP, RIGHT, DOWN, LEFT}

    Drawable frameDrawable;
    Drawable doorDrawable;
    int innerMarginTop;
    Direction doorOpenDirection;
    int doorOverlayId;
    View doorOverlay;

    boolean isOpening;
    boolean isClosing;
    float animState;
    long animStartTime;



    public Elevator_oneDoorSlide(Context context) {
        super(context);
    }

    public Elevator_oneDoorSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    void parseAttributes(AttributeSet attrs) {
        super.parseAttributes(attrs);
        TypedArray styledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.Elevator_oneDoorSlide);

        frameDrawable = styledAttributes.getDrawable(R.styleable.Elevator_oneDoorSlide_frame);
        doorDrawable = styledAttributes.getDrawable(R.styleable.Elevator_oneDoorSlide_door);

        innerMarginTop = parseScaledAttribute(styledAttributes, R.styleable.Elevator_oneDoorSlide_innerMarginTop);

        doorOverlayId = styledAttributes.getResourceId(R.styleable.Elevator_oneDoorSlide_doorOverlay, 0);

        doorOpenDirection = Direction.values()[styledAttributes.getInt(R.styleable.Elevator_oneDoorSlide_doorOpenDirection, 0)];
        styledAttributes.recycle();
    }

    @Override
    public boolean isOpening() {
        return isOpening || isClosing;
    }

    @Override
    public int getLayoutId() {
        return R.layout.partial_elevator_one_door;
    }

    @Override
    protected void inflateView() {
        super.inflateView();

        if (frameDrawable != null)
            ((ImageView)findViewById(R.id.elevator_frame)).setImageDrawable(frameDrawable);

        if (doorDrawable != null)
            ((ImageView)findViewById(R.id.elevator_door)).setImageDrawable(doorDrawable);

        if (doorOverlayId != 0){
            doorOverlay = inflate(getContext(), doorOverlayId, null);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_LEFT, R.id.elevator_door);
            lp.addRule(RelativeLayout.ALIGN_RIGHT, R.id.elevator_door);
            lp.addRule(RelativeLayout.ALIGN_TOP, R.id.elevator_door);
            lp.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.elevator_door);
            ((RelativeLayout)findViewById(R.id.elev_doors)).addView(doorOverlay, lp);
        }
    }

    @Override
    protected void scaleViews() {
        super.scaleViews();

        if (innerMarginTop != 0)
            ((MarginLayoutParams)findViewById(R.id.elevator_inner).getLayoutParams()).topMargin = innerMarginTop;
    }

    @Override
    public void openDoors() {
        if (isOpening)
            return;

        isClosing = false;
        isOpening = true;
        Animation anim = getDoorAnimation(animState, 1);

        anim.setDuration((int)(DOOR_OPEN_DURATION - animState * DOOR_OPEN_DURATION));
        animStartTime = System.currentTimeMillis();

        startDoorAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.elev_doors).setVisibility(GONE);
                setDoorsOpen(true);
                isOpening = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    @Override
    public void closeDoors() {
        if (isClosing || animState == 0)
            return;

        isOpening = false;
        isClosing = true;
        Animation anim = getDoorAnimation(animState, 0);

        anim.setDuration((int)(animState * DOOR_OPEN_DURATION));
        animStartTime = System.currentTimeMillis();

        startDoorAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                isClosing = false;
                animState = 0;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

    }

    void startDoorAnimation(Animation anim){
        View door = findViewById(R.id.elevator_door);
        Animation oldAnim = door.getAnimation();
        if (oldAnim != null)
            oldAnim.setAnimationListener(null);

        door.clearAnimation();
        door.setAnimation(anim);

        if (doorOverlay != null){
            doorOverlay.clearAnimation();
            doorOverlay.setAnimation(anim);
        }
        anim.reset();
        door.invalidate();
    }

    public void pauseDoors(){
        if (isOpening || isClosing){
            if (isOpening)
                animState = animState + (System.currentTimeMillis()- animStartTime) / (float)DOOR_OPEN_DURATION;
            else
                animState = animState - (System.currentTimeMillis()- animStartTime) / (float)DOOR_OPEN_DURATION;
            Animation newAnim = getDoorAnimation(animState, animState);
            newAnim.setDuration(1);
            newAnim.setFillAfter(true);
            newAnim.setRepeatCount(0);
            startDoorAnimation(newAnim);
            isClosing = isOpening = false;
        }
    }

    Animation getDoorAnimation(float start, float end){
        switch (doorOpenDirection){
            case UP:
                return new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, -start, Animation.RELATIVE_TO_SELF, -end);
            case RIGHT:
                return new TranslateAnimation(Animation.RELATIVE_TO_SELF, start, Animation.RELATIVE_TO_SELF, end, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
            case DOWN:
                return new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, start, Animation.RELATIVE_TO_SELF, end);
            case LEFT:
                return new TranslateAnimation(Animation.RELATIVE_TO_SELF, -start, Animation.RELATIVE_TO_SELF, -end, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
        }
        return null;
    }
}
