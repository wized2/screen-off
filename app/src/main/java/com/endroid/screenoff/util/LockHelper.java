package com.endroid.screenoff.util;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
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
        return tryLock(null);
    }

    public static boolean tryLock(Context context) {
        LockAccessibilityService service = AppServiceHolder.service;
        if (service == null) {
            return false;
        }
        service.lockScreen();
        if (context != null) {
            Prefs.markLocked(context);
            if (Prefs.vibrateOnLock(context)) {
                vibrate(context);
            }
        }
        return true;
    }

    private static void vibrate(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                VibratorManager vm =
                        (VibratorManager) context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
                if (vm != null) {
                    vm.getDefaultVibrator()
                            .vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            } else {
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if (v != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        v.vibrate(40);
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
}
