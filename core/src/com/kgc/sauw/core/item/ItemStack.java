package com.kgc.sauw.core.item;

public class ItemStack {
    private Item item;
    private int amount;

    public ItemStack(Item itemIn) {
        this(itemIn, 0);
    }

    public ItemStack(Item itemIn, int amount) {
        this.item = itemIn;
        this.amount = amount;
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }
}
