package com.konka.fileclear.dao;

/**
 * Created by user001 on 2017-8-19.
 */

public class StorageClear extends org.litepal.crud.DataSupport {
    private int id;
    private String lastClearTime;
    private long lastClearSize;
    private long totalClearSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastClearTime() {
        return lastClearTime;
    }

    public void setLastClearTime(String lastClearTime) {
        this.lastClearTime = lastClearTime;
    }

    public long getLastClearSize() {
        return lastClearSize;
    }

    public void setLastClearSize(long lastClearSize) {
        this.lastClearSize = lastClearSize;
    }

    public long getTotalClearSize() {
        return totalClearSize;
    }

    public void setTotalClearSize(long totalClearSize) {
        this.totalClearSize = totalClearSize;
    }
}
