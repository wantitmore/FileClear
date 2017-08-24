package com.konka.fileclear.entity;

/**
 * Created by user001 on 2017-8-24.
 */

public class Others {
    private int id;
    private String path;
    private String name;

    public Others() {
    }

    public Others(int id, String path, String name) {
        this.id = id;
        this.path = path;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
