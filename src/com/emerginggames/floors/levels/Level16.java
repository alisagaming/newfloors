package com.emerginggames.floors.levels;

import android.content.Context;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import com.emerginggames.floors.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 04.10.12
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public class Level16 extends Level {
    RingStack leftStack = new RingStack(1);
    RingStack centerStack = new RingStack(1);
    RingStack rightStack = new RingStack(1);


    public Level16(LevelListener levelListener, Context context) {
        super(levelListener, context);

        addRing(0, R.id.ring0);
        addRing(1, R.id.ring1);
        addRing(2, R.id.ring2);
        addRing(3, R.id.ring3);
        addRing(4, R.id.ring4);
        addRing(5, R.id.ring5);
        centerStack.getTop().activate();
    }

    void addRing(int n, int id){
        Ring ring = new Ring(n, findViewById(id));
        centerStack.add(ring);
    }

    @Override
    protected void onElementClicked(View elementView) {
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_16;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    boolean checkRingOnTop(Ring ring) {
        return leftStack.checkRingOnTop(ring) || centerStack.checkRingOnTop(ring) || rightStack.checkRingOnTop(ring);
    }

    void moveRingToStick(Ring ring, int stickN){
        RingStack currentStack = findStack(ring);
        RingStack newStack = getStack(stickN);
        if (newStack != null && (newStack.isEmpty() || newStack.getTop().n < ring.n)){
            currentStack.remove(ring);
            newStack.add(ring);
            positionRingToStick(ring, newStack);
        }
        else {
            positionRingToStick(ring, currentStack);
        }
        if (centerStack.isEmpty()){
            elevator.openDoors();
            View stick = findViewById(R.id.stick2);
            Animation anim = new AlphaAnimation(1, 0);
            anim.setDuration(300);
            anim.setFillAfter(true);
            stick.startAnimation(anim);
        }
    }

    RingStack getStack(int stackN){
        switch (stackN){
            case 1:
                return leftStack;
            case 2:
                return centerStack;
            case 3:
                return rightStack;
            default:
                return null;
        }
    }

    void positionRingToStick(Ring ring, RingStack stack){
        View stickView = stack == leftStack ? findViewById(R.id.stick1):
                          stack == centerStack ? findViewById(R.id.stick2) : findViewById(R.id.stick3);

        LayoutParams lp = (LayoutParams)ring.view.getLayoutParams();
        lp.leftMargin = stickView.getLeft() + stickView.getWidth()/2 - ring.view.getWidth()/2;
        if (stack.size() == 1)
            lp.topMargin = stickView.getBottom() - ring.view.getHeight();
        else
            lp.topMargin = stack.get(stack.size()-2).view.getTop() - ring.view.getHeight();

        ring.view.requestLayout();
    }

    private class Ring {
        int n;
        View view;
        OnTouchListener listener;

        private Ring(int n, View view) {
            this.n = n;
            this.view = view;
            listener =new RingTouchListener(this);
        }

        public void activate(){
            view.setOnTouchListener(listener);
        }

        public void deactivate(){
            view.setOnTouchListener(null);
        }
    }

    private class RingStack{
        int n;
        List<Ring> list;

        private RingStack(int n) {
            this.n = n;
            list = new ArrayList<Ring>();
        }

        public boolean contains(Ring r){
            return list.contains(r);
        }

        public void add(Ring r){
            if (list.size() > 0)
                list.get(list.size()-1).deactivate();
            list.add(r);
            //r.activate();
        }

        public void remove(Ring r){
            list.remove(r);
            if (list.size()> 0)
                list.get(list.size() - 1).activate();
        }

        public boolean checkRingOnTop(Ring r){
            return list.size() != 0 && (list.get(list.size() - 1).equals(r));
        }

        public Ring getTop(){
            return list.size() > 0 ? list.get(list.size()-1) : null;
        }

        public boolean isEmpty(){
            return list.size() == 0;
        }

        public int size(){
            return list.size();
        }

        public Ring get(int n){
            return list.get(n);
        }
    }

    RingStack findStack(Ring ring){
        if (leftStack.contains(ring))
            return leftStack;
        if (centerStack.contains(ring))
            return centerStack;

        return rightStack;
    }


    class RingTouchListener implements OnTouchListener {
        float initX;
        float initY;
        Ring ring;

        RingTouchListener(Ring ring) {
            this.ring = ring;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if (checkRingOnTop(ring)){
                        initX = event.getX();
                        initY = event.getY();
                        LayoutParams lp = (LayoutParams)v.getLayoutParams();
                        lp.topMargin = v.getTop();
                        lp.leftMargin = v.getLeft();
                        int[] rules = lp.getRules();
                        rules[ABOVE] = 0;
                        rules[CENTER_HORIZONTAL] = 0;
                        rules[ALIGN_PARENT_TOP] = TRUE;
                        rules[ALIGN_PARENT_LEFT] = TRUE;
                        v.requestLayout();
                        return true;
                    }
                    return false;
                case MotionEvent.ACTION_MOVE:
                    moveBy(event.getX() - initX, event.getY() - initY);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    moveRingToStick(ring, getClosestStick());
                    return true;
            }

            return false;
        }

        void moveBy(float x, float y){
            LayoutParams lp = (LayoutParams)ring.view.getLayoutParams();
            lp.leftMargin +=x;
            lp.topMargin +=y;
            lp.leftMargin = Math.min(lp.leftMargin, ((View)ring.view.getParent()).getWidth() - ring.view.getWidth());
            lp.topMargin = Math.min(lp.topMargin, ((View)ring.view.getParent()).getHeight() - ring.view.getHeight());
            ring.view.requestLayout();
        }

        int getClosestStick(){
            int x = ring.view.getLeft() + ring.view.getWidth()/2;
            int y = ring.view.getTop() + ring.view.getHeight()/2;

            float d1 = getDistance(x, y, findViewById(R.id.stick1));
            float d2 = getDistance(x, y, findViewById(R.id.stick2));
            float d3 = getDistance(x, y, findViewById(R.id.stick3));

            if (d2 <= d1 && d2 <= d3)
                return 2;

            if (d1 < d2 && d1 < d3)
                return 1;

            return 3;
        }

        float getDistance(int x, int y, View stick){
            int dx = stick.getLeft() + stick.getWidth()/2 - x;
            int dy = stick.getTop() - y;
            return FloatMath.sqrt(dx * dx + dy * dy);
        }
    }
}
