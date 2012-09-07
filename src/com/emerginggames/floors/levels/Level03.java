package com.emerginggames.floors.levels;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.SparseArray;
import android.view.View;
import com.emerginggames.floors.R;
import com.emerginggames.floors.items.ItemNotepadLevel3;
import com.emerginggames.floors.model.Item;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 26.08.12
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class Level03 extends Level {
    public Level03(LevelListener levelListener, Context context) {
        super(levelListener, context);
        items = new SparseArray<Item>(1);
        items.append(R.id.note, new ItemNotepadLevel3(1, R.drawable.fl3_tool_note));
        initShakeDetector();
    }

    @Override
    protected void onElementClicked(View elementView) {
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_03;
    }

    @Override
    public void start() {
        super.start();
        startShakeListener();
    }

    @Override
    protected void scaleViews() {
        scaleImageSize(R.id.up_btn);
        scaleImageSize(R.id.down_btn);

        scaleImageSize(R.id.note);
        scaleMargins(R.id.note, true, false, false, true);
        scaleImageSize(R.id.note_large);
    }

    @Override
    public void onResume() {
        startShakeListener();
    }

    @Override
    public void onPause() {
        stopShakeListener();
    }

    void onShake(){
        if (!elevator.isOpening() && !elevator.isOpen()){
            elevator.openDoors();
            stopShakeListener();
        }
    }




    //Shake detector

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if (mAccel > 3)
                onShake();
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };



    void stopShakeListener(){
        mSensorManager.unregisterListener(mSensorListener);
    }

    void startShakeListener(){
        if (mSensorManager != null)
            mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    void initShakeDetector(){
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }
}
