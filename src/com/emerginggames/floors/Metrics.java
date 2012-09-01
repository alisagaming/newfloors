package com.emerginggames.floors;

import android.graphics.Rect;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 27.08.12
 * Time: 9:04
 * To change this template use File | Settings | File Templates.
 */
public class Metrics {
    public static int width;
    public static int height;
    public static float scale;

    public static void setSizeFromView(View v){
        Rect r = new Rect();
        v.getWindowVisibleDisplayFrame(r);
        width = r.width();
        height = r.height();
        scale = width / 480f;
    }

}
