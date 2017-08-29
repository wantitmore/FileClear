package com.konka.fileclear.entity;

import android.support.annotation.NonNull;

/**
 * Created by user001 on 2017-8-25.
 */

public class BigFile implements Comparable<BigFile> {
    private int id;
    private String name;
    private String path;
    private String size;
    private long realSize;

    public long getRealSize() {
        return realSize;
    }

    public void setRealSize(long realSize) {
        this.realSize = realSize;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int compareTo(@NonNull BigFile another) {

        return (int) (another.getRealSize() - this.realSize);
    }
}
