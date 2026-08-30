package com.endroid.screenoff;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class LockAccessibilityService extends AccessibilityService {
    @Override
    public void onServiceConnected() {
        AppServiceHolder.service = this;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    @Override
    public void onInterrupt() {}

    @Override
    public void onDestroy() {
        AppServiceHolder.service = null;
    }

    public void lockScreen() {
        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
    }
}