package com.zeyad.backbase.adapter;

/**
 * @author by zeyad on 20/05/16.
 */
public class ItemInfo<M> {
    public static final int HEADER = 1, FOOTER = 2, LOADING = 3, SECTION_HEADER = 4, CARD_SECTION_HEADER = 5;
    private M data;
    private int layoutId;
    private long id;
    private boolean isEnabled = true;

    public ItemInfo(M data, int layoutId) {
        this.data = data;
        this.layoutId = layoutId;
    }

    public long getId() {
        return id;
    }

    public ItemInfo<M> setId(long id) {
        this.id = id;
        return this;
    }

    public M getData() {
        return data;
    }

    public void setData(M data) {
        this.data = data;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public ItemInfo<M> setEnabled(boolean enabled) {
        isEnabled = enabled;
        return this;
    }
}