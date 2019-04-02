package com.example.meetdoctor.utils;

import android.app.Activity;

import com.example.meetdoctor.model.EventMessage;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

public class EventBusUtils {

    /**
     * 注册 EventBus
     */
    public static void register(Activity activity, Object subscriber) {
        EventBus eventBus = EventBusActivityScope.getDefault(activity);
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber);
        }
    }

    /**
     * 解除注册 EventBus
     */
    public static void unregister(Activity activity, Object subscriber) {
        EventBus eventBus = EventBusActivityScope.getDefault(activity);
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber);
        }
    }

    /**
     * 发送事件消息
     */
    public static void post(Activity activity, EventMessage event) {
        EventBusActivityScope.getDefault(activity).post(event);
    }

    /**
     * 发送粘性事件消息
     */
    public static void postSticky(Activity activity, EventMessage event) {
        EventBusActivityScope.getDefault(activity).postSticky(event);
    }

    /**
     * 移除粘性事件
     */
    public static void removeStickyEvent(Activity activity, Class<?> eventType) {
        Object stickyEvent = EventBusActivityScope.getDefault(activity).removeStickyEvent(eventType);
        if (stickyEvent == null) {
            throw new RuntimeException("this sticky event is not subscribed.");
        }
    }

    @Deprecated
    public static void register(Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber);
        }
    }

    @Deprecated
    public static void unregister(Object subscriber) {
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber);
        }
    }

    @Deprecated
    public static void post(EventMessage event) {
        EventBus.getDefault().post(event);
    }

    @Deprecated
    public static void postSticky(EventMessage event) {
        EventBus.getDefault().postSticky(event);
    }

    @Deprecated
    public static void removeStickyEvent(Class<?> eventType) {
        Object stickyEvent = EventBus.getDefault().removeStickyEvent(eventType);
        if (stickyEvent == null) {
            throw new RuntimeException("this sticky event is not subscribed.");
        }
    }
}