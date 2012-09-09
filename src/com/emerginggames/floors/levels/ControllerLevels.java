package com.emerginggames.floors.levels;

import android.content.Context;
import com.emerginggames.floors.Settings;

import java.lang.reflect.Constructor;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 01.09.12
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
public class ControllerLevels {
    public static final int maxLevels = 12;

    public static Level getLevel(int n, Level.LevelListener levelListener, Context context) {
        if (n > maxLevels)
            return null;
        try{
            String className = String.format("com.emerginggames.floors.levels.Level%02d", n);
            Class clazz = Class.forName(className);
            Constructor constr = clazz.getConstructor(Level.LevelListener.class, Context.class);
            return (Level)constr.newInstance(levelListener, context);
        }
        catch (Exception e){
            if (Settings.DEBUG)
                throw new RuntimeException(e);
            else
                return null;

        }
    }
}
