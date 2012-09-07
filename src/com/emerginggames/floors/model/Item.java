package com.emerginggames.floors.model;

import com.emerginggames.floors.levels.Level;

/**
 * Created with IntelliJ IDEA.
 * User: babay
 * Date: 21.08.12
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public class Item {
    public int inventoryDrawableId;
    public int itemId;
    public boolean activated;

    public Item(int itemId, int inventoryDrawableId) {
        this.itemId = itemId;
        this.inventoryDrawableId = inventoryDrawableId;
    }

    public Item(int itemId, int inventoryDrawableId, boolean activated) {
        this.itemId = itemId;
        this.inventoryDrawableId = inventoryDrawableId;
        this.activated = activated;
    }

    public void onActivate(Level levelView){}
    public void onDeactivate(Level levelView){}
}
