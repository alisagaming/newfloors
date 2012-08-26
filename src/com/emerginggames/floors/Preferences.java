package com.emerginggames.floors;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 26.08.12
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
public class Preferences {
    static Preferences instance;
    SharedPreferences prefs;
    Context context;

    private static final String CURRENT_LEVEL = "level";

    public Preferences(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static Preferences getInstance(Context context){
        if (instance == null)
            instance = new Preferences(context);
        return instance;
    }

    public int getCurrentLevel(){
        return prefs.getInt(CURRENT_LEVEL, 1);
    }

    public void advanceLevel(){
        prefs.edit().putInt(CURRENT_LEVEL, getCurrentLevel() + 1).commit();
    }

    public void setCurrentLevel(int n){
        prefs.edit().putInt(CURRENT_LEVEL, n).commit();
    }
}
