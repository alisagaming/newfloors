package com.emerginggames.floors.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 02.09.12
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */
public abstract class ScaledRelativeLayout extends RelativeLayout {
    public ScaledRelativeLayout(Context context) {
        super(context);
    }

    public ScaledRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaledRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected abstract void scaleViews();

    protected void scaleView(int id){
        View v = findViewById(id);
        if (v instanceof ImageView){
            scaleImage(id);
            return;
        }

        ViewGroup.LayoutParams lp = v.getLayoutParams();
        if (lp.width >0)
            lp.width = (int)(lp.width * Metrics.scale);
        if (lp.height >0)
            lp.height = (int)(lp.height * Metrics.scale);
        v.setLayoutParams(lp);
    }

    protected void scaleImage(int id) {
        ImageView image = (ImageView) findViewById(id);
        MarginLayoutParams lp = (MarginLayoutParams) image.getLayoutParams();
        lp.width = (int) (image.getDrawable().getIntrinsicWidth() * Metrics.scale);
        lp.height = (int) (image.getDrawable().getIntrinsicHeight() * Metrics.scale);
        image.setLayoutParams(lp);
    }

    protected void scaleMargins(int id) {
        View v = findViewById(id);
        MarginLayoutParams lp = (MarginLayoutParams) v.getLayoutParams();
        lp.leftMargin = (int) (lp.leftMargin * Metrics.scale);
        lp.topMargin = (int) (lp.topMargin * Metrics.scale);
        lp.rightMargin = (int) (lp.rightMargin * Metrics.scale);
        lp.bottomMargin = (int) (lp.bottomMargin * Metrics.scale);
        v.setLayoutParams(lp);
    }

    protected void scaleMargins(int id, boolean adjustLeftMargin, boolean adjustTopMargin, boolean adjustRightMargin, boolean adjustBottomMargin) {
        View v = findViewById(id);
        MarginLayoutParams lp = (MarginLayoutParams) v.getLayoutParams();
        if (adjustLeftMargin)
            lp.leftMargin = (int) (lp.leftMargin * Metrics.scale);
        if (adjustTopMargin)
            lp.topMargin = (int) (lp.topMargin * Metrics.scale);
        if (adjustRightMargin)
            lp.rightMargin = (int) (lp.rightMargin * Metrics.scale);
        if (adjustBottomMargin)
            lp.bottomMargin = (int) (lp.bottomMargin * Metrics.scale);
        v.setLayoutParams(lp);
    }

    protected void scalePadding(int id) {
        View v = findViewById(id);
        float scale = Metrics.scale;
        v.setPadding((int) (v.getPaddingLeft() * scale), (int) (v.getPaddingTop() * scale), (int) (v.getPaddingRight() * scale), (int) (v.getPaddingBottom() * scale));
    }

    protected void setScaledMargin(int id, int left, int top, int right, int bottom){
        MarginLayoutParams lp = (MarginLayoutParams)findViewById(id).getLayoutParams();
        lp.leftMargin = (int)(left * Metrics.scale);
        lp.topMargin = (int)(top * Metrics.scale);
        lp.rightMargin = (int)(right * Metrics.scale);
        lp.bottomMargin = (int)(bottom * Metrics.scale);
    }
}
