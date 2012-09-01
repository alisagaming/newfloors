package com.emerginggames.floors.elevators;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 25.08.12
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class Elevator extends RelativeLayout {

    public Elevator(Context context) {
        super(context);
        inflateView();
    }

    public Elevator(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateView();
    }

    public Elevator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflateView();
    }

    public View getElevatorInnerView(){
        return findViewById(R.id.elevator_inner);
    }

    public View getDoorsView(){
        return findViewById(R.id.elev_doors);
    }

    protected ImageView scaleImage(int id){
        ImageView image = (ImageView)findViewById(id);
        ViewGroup.LayoutParams lp = image.getLayoutParams();
        int width = image.getDrawable().getIntrinsicWidth();
        lp.width = (int)(width * Metrics.scale);
        return image;
    }

    protected void setImageAndScale(int id, int drawableId){
        ImageView image = (ImageView)findViewById(id);
        image.setImageResource(drawableId);
        ViewGroup.LayoutParams lp = image.getLayoutParams();
        int width = image.getDrawable().getIntrinsicWidth();
        lp.width = (int)(width * Metrics.scale);

    }

    protected void scalePadding(int id){
        View v = findViewById(id);
        float scale = Metrics.scale;
        v.setPadding((int)(v.getPaddingLeft() * scale), (int)(v.getPaddingTop() * scale), (int)(v.getPaddingRight() * scale), (int)(v.getPaddingBottom() * scale));
    }

    void scaleMargins(int id) {
        MarginLayoutParams lp = (MarginLayoutParams) findViewById(id).getLayoutParams();
        lp.leftMargin = (int) (lp.leftMargin * Metrics.scale);
        lp.topMargin = (int) (lp.topMargin * Metrics.scale);
        lp.rightMargin = (int) (lp.rightMargin * Metrics.scale);
        lp.bottomMargin = (int) (lp.bottomMargin * Metrics.scale);
    }

    void setScaledMargin(int id, int left, int top, int right, int bottom){
        MarginLayoutParams lp = (MarginLayoutParams)findViewById(id).getLayoutParams();
        lp.leftMargin = (int)(left * Metrics.scale);
        lp.topMargin = (int)(top * Metrics.scale);
        lp.rightMargin = (int)(right * Metrics.scale);
        lp.bottomMargin = (int)(bottom * Metrics.scale);
    }

    protected void inflateView(){
        addView(inflate(getContext(), getLayoutId(), null));
    }

    public abstract boolean isOpen();
    public abstract boolean isOpening();
    public abstract void reset();

    public abstract void openDoors();
    public abstract void closeDoors();
    public abstract int getLayoutId();
}
