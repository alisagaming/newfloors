package com.emerginggames.floors.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;
import com.emerginggames.floors.elevators.Elevator;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 02.09.12
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */
public class ScaledRelativeLayout extends RelativeLayout {
    Paint bmResizePaint;
    public ScaledRelativeLayout(Context context) {
        super(context);
    }

    public ScaledRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaledRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void scaleViews(){
        scaleViewsRecursive(this);
    }

    void scaleViewsRecursive(ViewGroup viewGroup){
        for (int i=0; i< viewGroup.getChildCount(); i++){
            View child = viewGroup.getChildAt(i);
            scaleView(child);

            if (child instanceof ImageView)
                scaleImage((ImageView)child);
            if (child instanceof TextView)
                ((TextView)child).setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)( ((TextView) child) .getTextSize() * Metrics.scale ));
            if (child.getPaddingBottom() != 0 || child.getPaddingBottom() != 0 ||
                    child.getPaddingBottom() != 0 ||child.getPaddingBottom() != 0)
                scalePadding(child);

            scaleMargins(child);

            if (child instanceof ViewGroup && ! (child instanceof ScaledRelativeLayout))
                scaleViewsRecursive((ViewGroup)child);
        }
        bmResizePaint = null;
    }

    protected void scaleView(int id){
        View v = findViewById(id);
        if (v instanceof ImageView){
            scaleImage(id);
            return;
        }
        if (v instanceof TextView)
            ((TextView)v).setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)( ((TextView) v) .getTextSize() * Metrics.scale ));


        ViewGroup.LayoutParams lp = v.getLayoutParams();
        if (lp.width >0)
            lp.width = (int)(lp.width * Metrics.scale);
        if (lp.height >0)
            lp.height = (int)(lp.height * Metrics.scale);
        v.setLayoutParams(lp);
    }

    protected void scaleView(View v){
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        if (lp.width >0)
            lp.width = (int)(lp.width * Metrics.scale);
        if (lp.height >0)
            lp.height = (int)(lp.height * Metrics.scale);
        //v.setLayoutParams(lp);
    }

    protected void scaleImage(int id) {
        scaleImage((ImageView) findViewById(id));
    }

    protected void scaleImage(ImageView image) {
        if (image.getDrawable() == null)
            return;
        ViewGroup.LayoutParams lp = image.getLayoutParams();
        lp.width = (int) (image.getDrawable().getIntrinsicWidth() * Metrics.scale);
        lp.height = (int) (image.getDrawable().getIntrinsicHeight() * Metrics.scale);
        scaleImageBitmap(image);
    }

    protected void scaleMargins(int id) {
        scaleMargins(findViewById(id));
    }

    protected void scaleMargins(View v) {
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
        scalePadding(findViewById(id));
    }

    protected void scalePadding(View v) {
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

    void scaleImageBitmap(ImageView image){
        Drawable dr = image.getDrawable();
        if (dr == null || ! (dr instanceof BitmapDrawable))
            return;

        Bitmap source = ((BitmapDrawable)dr).getBitmap();
        int dstWidth = (int)(source.getWidth() * Metrics.scale);
        int dstHeight = (int)(source.getHeight() * Metrics.scale);
        Bitmap dest = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(dest);
        if (bmResizePaint == null){
            bmResizePaint = new Paint();
            bmResizePaint.setFilterBitmap(true);
            bmResizePaint.setAntiAlias(true);
        }

        Rect sourceRect = new Rect();
        sourceRect.right = source.getWidth();
        sourceRect.bottom = source.getHeight();

        Rect destRect = new Rect();
        destRect.right = dstWidth;
        destRect.bottom = dstHeight;
        c.drawBitmap(source, sourceRect, destRect, bmResizePaint);
        image.setImageBitmap(dest);
    }
}
