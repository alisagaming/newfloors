package com.emerginggames.floors.util;

import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 10.09.12
 * Time: 1:06
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    public static float getAngleInView(View v, double x1, double y1, double x2, double y2){
        int x0 = v.getWidth()/2;
        int y0 = v.getHeight()/2;

        x2 = x2 - x0;
        y2 = y2 - y0;
        x1 = x1 - x0;
        y1 = y1 - y0;

        double len = Math.sqrt( (x2 * x2 + y2 * y2) *(x1 * x1 + y1 * y1) );
        double sin = (x1 * y2 - y1 * x2 ) / len;
        double cos = (x1 * x2 + y1 * y2) / len;

        if (sin < 0)
            return (float)Math.acos(cos);
        else
            return - (float)Math.acos(cos);
    }

    public static float getAngleInView(View v, double x2, double y2){
        int x0 = v.getWidth()/2;
        int y0 = v.getHeight()/2;

        x2 = x2 - x0;
        y2 = y2 - y0;

        double len = Math.sqrt( (x2 * x2 + y2 * y2));
        double sin = y2  / len;
        double cos = x2  / len;

        if (sin < 0)
            return (float)Math.acos(cos);
        else
            return - (float)Math.acos(cos);
    }
}
