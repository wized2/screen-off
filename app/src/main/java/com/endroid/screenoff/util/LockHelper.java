package com.endroid.screenoff.util;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;

import com.endroid.screenoff.service.LockAccessibilityService;

import java.util.List;

/** Shared helpers for accessibility status and screen lock. */
public final class LockHelper {

    private LockHelper() {
    }

    public static boolean isAccessibilityServiceEnabled(Context context) {
        AccessibilityManager am =
                (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (am == null) {
            return false;
        }
        List<AccessibilityServiceInfo> services =
                am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        if (services == null) {
            return false;
        }
        String pkg = context.getPackageName();
        for (AccessibilityServiceInfo info : services) {
            if (info.getResolveInfo() != null
                    && info.getResolveInfo().serviceInfo != null
                    && pkg.equals(info.getResolveInfo().serviceInfo.packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if the lock action was requested via the live accessibility service
     */
    public static boolean tryLock() {
        LockAccessibilityService service = AppServiceHolder.service;
        if (service == null) {
            return false;
        }
        service.lockScreen();
        return true;
    }
}
