package com.emerginggames.floors.levels;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import com.emerginggames.floors.R;
import com.emerginggames.floors.elevators.Elevator_oneDoor;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 09.09.12
 * Time: 20:26
 * To change this template use File | Settings | File Templates.
 */
public class Level12 extends Level {

    public Level12(LevelListener levelListener, Context context) {
        super(levelListener, context);
        initGravityDetector();
    }

    @Override
    protected void onElementClicked(View elementView) {
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_12;
    }

    @Override
    protected void scaleViews() {
        scaleMargins(elevator);
        scaleImage(R.id.hint);
        scaleMargins(R.id.hint);
    }

    @Override
    public void onResume() {
        startGravityListener();
    }

    @Override
    public void onPause() {
        stopGravityListener();
    }

    void onGravityUp(){
        if (!elevator.isOpen() && !elevator.isOpening())
            elevator.openDoors();
    }

    void onGravityNotUp(){
        if (elevator.isOpening())
            ((Elevator_oneDoor)elevator).pauseDoors();
    }

    void onGravityDown(){
        if (!elevator.isOpen())
            elevator.closeDoors();
    }

    //Gravity direction detector

    private SensorManager mSensorManager;
    float x, y, z;

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            x = se.values[0];
            y = se.values[1];
            z = se.values[2];

            if (Math.abs(y)* 0.8 < Math.abs(x) + Math.abs(z))
                onGravityNotUp();
            else if (y<0)
                onGravityUp();
            else
                onGravityDown();
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
