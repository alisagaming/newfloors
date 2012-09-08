package com.emerginggames.floors.levels;

import android.content.Context;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;
import com.emerginggames.floors.Settings;
import com.emerginggames.floors.elevators.Elevator;
import com.emerginggames.floors.model.Item;
import com.emerginggames.floors.view.ScaledRelativeLayout;
import com.emrg.view.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 21.08.12
 * Time: 9:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class Level extends ScaledRelativeLayout {
    LevelListener levelListener;
    Context context;
    Elevator elevator;
    SparseArray<Item> items;

    public Level(LevelListener levelListener, Context context) {
        super(context);
        this.levelListener = levelListener;
        this.context = context;
        initView();
        scaleViews();
    }

    public void initItems() {
        if (items != null) {
            int viewId;
            Item item;
            for (int i = 0; i < items.size(); i++) {
                viewId = items.keyAt(i);
                item = items.valueAt(i);
                if (item.activated)
                    levelListener.addItem(item);
                else {
                    View itemView = findViewById(viewId);
                    if (itemView != null){
                        itemView.setTag(item);
                        itemView.setOnClickListener(itemClickListener);
                        itemView.setVisibility(VISIBLE);
                    }
                }
            }
        }
    }

    void setControl(int id){
        findViewById(id).setOnClickListener(controlClickListener);
    }

    void setControl(View v, int n){
        v.setTag(n);
        v.setOnClickListener(controlClickListener);
        if (!Settings.DEBUG)
            v.setSoundEffectsEnabled(false);
    }

    public void start() {
        initItems();
        elevator.reset();
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void itemSelected(Item item) {
    }

    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(getLevelLayoutId(), null));
        elevator = ((Elevator) findViewById(R.id.elevator));
        elevator.getElevatorInnerView().setOnClickListener(elevatorDoneListener);
    }

    protected void onItemClickListener(View itemView) {
        levelListener.addItem((Item) itemView.getTag());
        itemView.setVisibility(GONE);
        itemView.setOnClickListener(null);
    }

    protected abstract void onElementClicked(View elementView);

    protected abstract int getLevelLayoutId();

    protected View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onItemClickListener(v);
        }
    };

    protected View.OnClickListener controlClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onElementClicked(v);
        }
    };

    protected OnClickListener elevatorDoneListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (elevator.isOpen())
                levelListener.levelComplete();
        }
    };

    public interface LevelListener {
        Item getCurrentItem();

        void addItem(Item item);

        void removeCurrentItem();

        void resetCurrentItem();

        void levelComplete();

        int getCoveredBottomHeight();
    }
}
