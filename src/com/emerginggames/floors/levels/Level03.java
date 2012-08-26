package com.emerginggames.floors.levels;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.SparseArray;
import android.view.View;
import com.emerginggames.floors.R;
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
        items.append(R.id.note, new Item(1, R.drawable.fl3_tool_note));
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
    }

    @Override
    protected void initView() {
        super.initView();
        rootView.findViewById(R.id.note_large).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.note_large).setVisibility(GONE);
            }
        });
    }

    @Override
    public void itemSelected(Item item) {
        levelListener.resetCurrentItem();
        findViewById(R.id.note_large).setVisibility(VISIBLE);
    }

    void onShake(){
        if (!elevator.isOpening() && !elevator.isOpen())
            elevator.openDoors();
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
            if (mAccel > 2)
                onShake();
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
    }

    void initShakeDetector(){
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }
}
