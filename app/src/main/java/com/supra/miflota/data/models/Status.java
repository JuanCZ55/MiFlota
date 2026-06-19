package com.supra.miflota.data.models;

public class Status {
    private boolean isAll, isActive;


    public Status(boolean isAll, boolean isActive) {
        this.isAll = isAll;
        this.isActive = isActive;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean isAll) {
        this.isAll = isAll;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
