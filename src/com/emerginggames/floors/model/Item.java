package com.emerginggames.floors.model;

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

    public Item(int itemId, int inventoryDrawableId) {
        this.itemId = itemId;
        this.inventoryDrawableId = inventoryDrawableId;
    }
}
