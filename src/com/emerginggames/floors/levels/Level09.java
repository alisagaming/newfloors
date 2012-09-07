package com.emerginggames.floors.levels;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import com.emerginggames.floors.R;
import com.emerginggames.floors.items.ItemNotepadLevel3;
import com.emerginggames.floors.model.Item;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 07.09.12
 * Time: 23:44
 * To change this template use File | Settings | File Templates.
 */
public class Level09 extends Level {
    TextView digit1;
    TextView digit2;
    TextView digit3;
    TextView digit4;
    TextView digit5;
    TextView digit6;
    TextView digit7;
    TextView digit8;
    TextView digit9;

    public Level09(LevelListener levelListener, Context context) {
        super(levelListener, context);
        items = new SparseArray<Item>(1);
        items.append(R.id.note, new ItemNotepadLevel3(1, R.drawable.fl3_tool_note, true));
    }

    @Override
    protected void onElementClicked(View elementView) {
        switch (elementView.getId()){
            case R.id.digit1:
            case R.id.digit2:
            case R.id.digit3:
            case R.id.digit4:
            case R.id.digit5:
            case R.id.digit6:
            case R.id.digit7:
            case R.id.digit8:
            case R.id.digit9:
                int value = Integer.parseInt(((TextView)elementView).getText().toString());
                value++;
                if (value > 9)
                    value = 0;
                ((TextView)elementView).setText(Integer.toString(value));
                break;

            case R.id.icon:
                if (checkResult())
                    elevator.openDoors();
                break;
        }
    }

    boolean checkResult(){
        return digit1.getText().equals("8") &&
            digit2.getText().equals("0") &&
            digit3.getText().equals("1") &&
            digit4.getText().equals("2") &&
            digit5.getText().equals("4") &&
            digit6.getText().equals("3") &&
            digit7.getText().equals("3") &&
            digit8.getText().equals("5") &&
            digit9.getText().equals("0");
    }

    @Override
    protected int getLevelLayoutId() {
        return R.layout.level_09;
    }

    @Override
    protected void scaleViews() {
        scaleView(R.id.digit1);
        scaleView(R.id.digit2);
        scaleView(R.id.digit3);
        scaleView(R.id.digit4);
        scaleView(R.id.digit5);
        scaleView(R.id.digit6);
        scaleView(R.id.digit7);
        scaleView(R.id.digit8);
        scaleView(R.id.digit9);
        scaleMargins(R.id.digits);
        scaleImageSize(R.id.icon);
    }

    @Override
    protected void initView() {
        super.initView();
        setControl(R.id.digit1);
        setControl(R.id.digit2);
        setControl(R.id.digit3);
        setControl(R.id.digit4);
        setControl(R.id.digit5);
        setControl(R.id.digit6);
        setControl(R.id.digit7);
        setControl(R.id.digit8);
        setControl(R.id.digit9);
        setControl(R.id.icon);

        digit1 = (TextView)findViewById(R.id.digit1);
        digit2 = (TextView)findViewById(R.id.digit2);
        digit3 = (TextView)findViewById(R.id.digit3);
        digit4 = (TextView)findViewById(R.id.digit4);
        digit5 = (TextView)findViewById(R.id.digit5);
        digit6 = (TextView)findViewById(R.id.digit6);
        digit7 = (TextView)findViewById(R.id.digit7);
        digit8 = (TextView)findViewById(R.id.digit8);
        digit9 = (TextView)findViewById(R.id.digit9);
    }
}
