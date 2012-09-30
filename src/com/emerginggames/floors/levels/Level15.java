package com.emerginggames.floors.levels;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 30.09.12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class Level15 extends Level {
    private static long UPDATE_INTERVAL = 30;
    private float speedMult = 1.0f;
    private float maxSpeed = 4;
    boolean mPaused;
    int maxBottomMargin = 300;
    int maxRightMargin = 396;
    int minRightMargin = 5;
    int minBottomMargin = 0;
    float bottomMargin = 0;
    float rightMargin = 0;
    boolean solved;
    View cat;


    public Level15(LevelListener levelListener, Context context) {
        super(levelListener, context);
        initGravityDetector();

        maxBottomMargin *= Metrics.scale;
        maxRightMargin *= Metrics.scale;
        minRightMargin *= Metrics.scale;
        minBottomMargin *= Metrics.scale;
        maxSpeed *= Metrics.scale;
        speedMult *= Metrics.scale;
        cat = findViewById(R.id.cat);
    }

    @Override
    protected void onElementClicked(View elementView) {
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_15;
    }

    Runnable catUpdater = new Runnable() {
        @Override
        public void run() {
            MarginLayoutParams lp = (MarginLayoutParams)cat.getLayoutParams();
            if (lp.rightMargin == minRightMargin || lp.rightMargin == maxRightMargin){
                float speed = Math.max(-maxSpeed, Math.min(maxSpeed, -y * speedMult));

                bottomMargin = Math.max(minBottomMargin, Math.min(maxBottomMargin, bottomMargin + speed));
                lp.bottomMargin = (int)bottomMargin;
            }

            if (lp.bottomMargin == maxBottomMargin){
                float speed = Math.max(-maxSpeed, Math.min(maxSpeed, x * speedMult));
                rightMargin = Math.max(minRightMargin, Math.min(maxRightMargin, rightMargin + speed));
                lp.rightMargin = (int)rightMargin;
            }
            cat.requestLayout();

            if (lp.rightMargin == maxRightMargin && lp.bottomMargin == minBottomMargin){
                elevator.openDoors();
                solved = true;
            }

            if (!mPaused && !solved)
                postDelayed(catUpdater, UPDATE_INTERVAL);
        }
    };

    @Override
    public void start() {
        super.start();
        mPaused = false;
        post(catUpdater);
        startGravityListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPaused = true;
        stopGravityListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPaused = false;
        if (!solved)
            post(catUpdater);
        startGravityListener();
    }

    //Gravity direction detector

    private SensorManager mSensorManager;
    float x, y, z;

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            x = se.values[0];
            y = se.values[1];//this is up-down
            z = se.values[2];

            /*if (Math.abs(y)* 0.8 < Math.abs(x) + Math.abs(z))
                onGravityNotUp();
            else if (y<0)
                onGravityUp();
            else
                onGravityDown();*/
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


    void stopGravityListener(){
        mSensorManager.unregisterListener(mSensorListener);
    }

    void startGravityListener(){
        if (mSensorManager != null)
            mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    void initGravityDetector(){
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    }
}
