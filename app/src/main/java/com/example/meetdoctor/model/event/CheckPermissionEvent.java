package com.example.meetdoctor.model.event;

public class CheckPermissionEvent {

    private int requestCode;

    public CheckPermissionEvent(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
