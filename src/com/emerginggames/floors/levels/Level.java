package com.emerginggames.floors.levels;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.emerginggames.floors.R;
import com.emerginggames.floors.model.Item;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 21.08.12
 * Time: 9:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class Level extends RelativeLayout {
    LevelListener levelListener;
    protected ViewGroup rootView;
    Context context;
    Elevator elevator;
    SparseArray<Item> items;

    public Level(LevelListener levelListener, Context context) {
        super(context);
        this.levelListener = levelListener;
        this.context = context;
        initView();
        addView(rootView);
    }



    public void initItems(){
        if (items != null) {
            int viewId;
            Item item;
            for (int i = 0; i < items.size(); i++) {
                viewId = items.keyAt(i);
                item = items.valueAt(i);
                View itemView = rootView.findViewById(viewId);
                itemView.setTag(item);
                itemView.setOnClickListener(itemClickListener);
                itemView.setVisibility(VISIBLE);
            }
        }
    }

    public void start() {
        initItems();
        elevator.reset();
    }

    public void onResume(){};
    public void onPause(){};
    public void itemSelected(Item item) {}

    protected void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = (ViewGroup) inflater.inflate(getLevelLayoutId(), null);
        elevator = ((Elevator) rootView.findViewById(R.id.elevator));
        elevator.getElevatorInnerView().setOnClickListener(elevatorDoneListener);
    }

    protected void onItemClickListener(View itemView) {
        levelListener.addItem((Item)itemView.getTag());
        itemView.setVisibility(GONE);
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
    }
}
