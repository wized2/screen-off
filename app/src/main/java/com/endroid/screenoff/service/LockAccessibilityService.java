package com.endroid.screenoff.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import com.endroid.screenoff.util.AppServiceHolder;

/**
 * Accessibility service used solely for {@link #GLOBAL_ACTION_LOCK_SCREEN}.
 * Does not inspect window content.
 */
public class LockAccessibilityService extends AccessibilityService {

    @Override
    public void onServiceConnected() {
        AppServiceHolder.service = this;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Unused — lock is triggered explicitly.
    }

    @Override
    public void onInterrupt() {
        // Required by API.
    }

    @Override
    public void onDestroy() {
        if (AppServiceHolder.service == this) {
            AppServiceHolder.service = null;
        }
        super.onDestroy();
    }

    public void lockScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
        }
    }
}
