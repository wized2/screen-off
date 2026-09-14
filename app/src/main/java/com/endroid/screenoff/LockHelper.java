package com.endroid.screenoff;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

/** Shared helpers for accessibility status and locking. */
public final class LockHelper {
    private LockHelper() {}

    public static boolean isAccessibilityServiceEnabled(Context context) {
        AccessibilityManager am =
                (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (am == null) return false;
        List<AccessibilityServiceInfo> services =
                am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        if (services == null) return false;
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
     * Attempts to lock the screen.
     * @return true if the accessibility service was available and the lock action was requested
     */
    public static boolean tryLock() {
        LockAccessibilityService service = AppServiceHolder.service;
        if (service == null) return false;
        service.lockScreen();
        return true;
    }
}
