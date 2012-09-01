package com.emerginggames.floors.levels;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 01.09.12
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
public class ControllerLevels {
    public static Level getLevel(int n, Level.LevelListener levelListener, Context context) {
        switch (n) {
            case 1:
                return new Level01(levelListener, context);
            case 2:
                return new Level02(levelListener, context);
            case 3:
                return new Level03(levelListener, context);
            case 4:
                return new Level04(levelListener, context);
            case 5:
                return new Level05(levelListener, context);
            default:
                return null;
        }
    }
}
