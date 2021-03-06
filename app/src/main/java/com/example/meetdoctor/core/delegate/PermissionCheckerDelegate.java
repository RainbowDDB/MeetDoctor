package com.example.meetdoctor.core.delegate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.example.meetdoctor.core.storage.LattePreference;
import com.example.meetdoctor.model.EventCode;
import com.example.meetdoctor.model.EventMessage;
import com.example.meetdoctor.ui.launcher.ScrollLauncherTag;
import com.example.meetdoctor.utils.EventBusUtils;

import java.util.ArrayList;

/**
 * Latte-Core
 * 进行权限检查
 * Created By Rainbow on 2019/4/30.
 */
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermissions();
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermissions() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        ArrayList<String> toApplyList = new ArrayList<>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED !=
                    ContextCompat.checkSelfPermission(getProxyActivity(), perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        // 如果是首次进入APP，则申请权限
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            if (!toApplyList.isEmpty()) {
                requestPermissions(toApplyList.toArray(tmpList), EventCode.PERMISSION_SUCCESS);
            }
        }
    }

    // 此处为android 6.0以上动态授权的回调，自行实现。
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // 通知WelcomeDelegate进行计时
        EventBusUtils.post(getProxyActivity(), new EventMessage(EventCode.PERMISSION_SUCCESS));
    }
}
