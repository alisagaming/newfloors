package com.emerginggames.floors.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 04.09.12
 * Time: 1:53
 * To change this template use File | Settings | File Templates.
 */
public class DustView extends View {
    private static final int HORIZ_CHECKPOINTS = 30;
    private static final int VERT_CHECKPOINTS = 10;

    Bitmap content;
    Paint paint;
    Paint pTouch;
    public DustView(Context context) {
        super(context);
        paint = new Paint();
    }

    public DustView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

        pTouch = new Paint(Paint.ANTI_ALIAS_FLAG);
        pTouch.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        pTouch.setColor(Color.TRANSPARENT);
        pTouch.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        if (content == null || content.getWidth() != getWidth() || content.getHeight() != getHeight()){
            if (content != null)
                content.recycle();
            content = createContentBitmap();
        }
        canvas.drawBitmap(content, 0, 0, paint);
        canvas.restore();
    }

    Bitmap createContentBitmap(){
        Bitmap bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
        canvas.drawARGB(128, 0, 0, 0);
        Rect src = new Rect();
        src.left = src.top = 0;
        Rect dst = new Rect();
        Bitmap dustBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.level07_dirt);

        src.right = dustBitmap.getWidth();
        src.bottom = dustBitmap.getHeight();
        dst.left = dst.top = 0;
        dst.right = bmp.getWidth();
        dst.bottom = (int)(src.bottom * Metrics.scale);

        canvas.drawBitmap(dustBitmap, 0, 0, paint);
        dustBitmap.recycle();

        Bitmap doorReflectBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.level07_door_reflection);

        src.right = doorReflectBitmap.getWidth();
        src.bottom = doorReflectBitmap.getHeight();
        int dstWidth = (int)(src.right * Metrics.scale);
        dst.left = (bmp.getWidth() - dstWidth) / 2;
        dst.right = (bmp.getWidth() + dstWidth) / 2;
        dst.top = 0;
        dst.bottom = (int)(doorReflectBitmap.getHeight() * Metrics.scale);

        canvas.drawBitmap(doorReflectBitmap, src, dst, paint);
        doorReflectBitmap.recycle();

        return bmp;
    }

    public void clear(float x, float y, int radius){
        Canvas canvas = new Canvas(content);
        RectF oval = new RectF();
        oval.left = x - radius;
        oval.right = x+ radius;
        oval.top = y - radius * 0.6f;
        oval.bottom = y + radius * 0.6f;
        canvas.drawOval(oval, pTouch);

    }

   public boolean isAreaClear(Rect rect){
        int stepX = rect.width() / (HORIZ_CHECKPOINTS + 1);
        int stepY = rect.height() / (VERT_CHECKPOINTS + 1);

        for (int x = rect.left + stepX/2; x<rect.right; x+=stepX)
            for (int y= rect.top + stepY/2; y<rect.bottom; y+=stepY)
                if (Color.alpha(content.getPixel(x, y)) != 0)
                    return false;

        return true;
    }
}
