package com.konka.fileclear.entity;

/**
 * Created by user001 on 2017-8-22.
 */

public class Audio {
    private int id;
    private String name;
    private String path;

    public Audio(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

}
