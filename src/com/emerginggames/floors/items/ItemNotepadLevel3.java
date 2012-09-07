package com.emerginggames.floors.items;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.emerginggames.floors.Metrics;
import com.emerginggames.floors.R;
import com.emerginggames.floors.levels.Level;
import com.emerginggames.floors.model.Item;
import com.emrg.view.ImageView;

import java.lang.reflect.Method;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 08.09.12
 * Time: 1:35
 * To change this template use File | Settings | File Templates.
 */
public class ItemNotepadLevel3 extends Item {
    ImageView view;

    public ItemNotepadLevel3(int itemId, int inventoryDrawableId) {
        super(itemId, inventoryDrawableId);
    }

    public ItemNotepadLevel3(int itemId, int inventoryDrawableId, boolean activated) {
        super(itemId, inventoryDrawableId, activated);
    }

    @Override
    public void onActivate(final Level levelView) {
        view = new ImageView(levelView.getContext());
        view.setImageResource(R.drawable.fl3_note_large);
        view.setScaleType(android.widget.ImageView.ScaleType.FIT_CENTER);
        Drawable dr = view.getDrawable();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)(dr.getIntrinsicWidth() * Metrics.scale),
                (int)(dr.getIntrinsicHeight() * Metrics.scale));
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        levelView.addView(view, lp);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelView.removeView(view);
                view = null;
            }
        });
    }

    @Override
    public void onDeactivate(Level levelView) {
        if (view != null)
            levelView.removeView(view);
        view = null;
    }
}
