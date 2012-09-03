package com.emerginggames.floors.levels;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;
import com.emerginggames.floors.elevators.Elevator;
import com.emerginggames.floors.model.Item;
import com.emrg.view.ImageView;

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

    public void initItems() {
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

    void setControl(int id){
        rootView.findViewById(id).setOnClickListener(controlClickListener);
    }

    void setControl(View v, int n){
        v.setTag(n);
        v.setOnClickListener(controlClickListener);
        v.setSoundEffectsEnabled(false);
    }

    void scaleView(int id){
        ViewGroup.LayoutParams lp = rootView.findViewById(id).getLayoutParams();
        if (lp.width >0)
            lp.width = (int)(lp.width * Metrics.scale);
        if (lp.height >0)
            lp.height = (int)(lp.height * Metrics.scale);
    }

    void scaleImageSize(int id) {
        ImageView image = (ImageView) rootView.findViewById(id);
        MarginLayoutParams lp = (MarginLayoutParams) image.getLayoutParams();
        lp.width = (int) (image.getDrawable().getIntrinsicWidth() * Metrics.scale);
        lp.height = (int) (image.getDrawable().getIntrinsicHeight() * Metrics.scale);
    }

    void scaleMargins(int id) {
        View v = rootView.findViewById(id);
        MarginLayoutParams lp = (MarginLayoutParams) v.getLayoutParams();
        lp.leftMargin = (int) (lp.leftMargin * Metrics.scale);
        lp.topMargin = (int) (lp.topMargin * Metrics.scale);
        lp.rightMargin = (int) (lp.rightMargin * Metrics.scale);
        lp.bottomMargin = (int) (lp.bottomMargin * Metrics.scale);
    }

    void scaleMargins(int id, boolean adjustLeftMargin, boolean adjustTopMargin, boolean adjustRightMargin, boolean adjustBottomMargin) {
        View v = rootView.findViewById(id);
        MarginLayoutParams lp = (MarginLayoutParams) v.getLayoutParams();
        if (adjustLeftMargin)
            lp.leftMargin = (int) (lp.leftMargin * Metrics.scale);
        if (adjustTopMargin)
            lp.topMargin = (int) (lp.topMargin * Metrics.scale);
        if (adjustRightMargin)
            lp.rightMargin = (int) (lp.rightMargin * Metrics.scale);
        if (adjustBottomMargin)
            lp.bottomMargin = (int) (lp.bottomMargin * Metrics.scale);
    }

    void scalePaddings(int id) {
        View v = rootView.findViewById(id);
        float scale = Metrics.scale;
        v.setPadding((int) (v.getPaddingLeft() * scale), (int) (v.getPaddingTop() * scale), (int) (v.getPaddingRight() * scale), (int) (v.getPaddingBottom() * scale));
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
        rootView = (ViewGroup) inflater.inflate(getLevelLayoutId(), null);
        elevator = ((Elevator) rootView.findViewById(R.id.elevator));
        elevator.getElevatorInnerView().setOnClickListener(elevatorDoneListener);
        MarginLayoutParams lp = (MarginLayoutParams) elevator.getLayoutParams();
        lp.bottomMargin = (int) (lp.bottomMargin * Metrics.scale);
    }

    protected void onItemClickListener(View itemView) {
        levelListener.addItem((Item) itemView.getTag());
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
