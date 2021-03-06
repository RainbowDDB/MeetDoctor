package com.example.meetdoctor.model;

import android.support.annotation.NonNull;

/**
 * Created By Rainbow on 2019/4/30.
 */
public class EventMessage<T> {

    private int code;
    private T data;

    public EventMessage(int code) {
        this.code = code;
    }

    public EventMessage(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        return "EventMessage{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}