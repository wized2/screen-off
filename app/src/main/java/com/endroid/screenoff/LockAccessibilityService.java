package com.endroid.screenoff;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

public class LockAccessibilityService extends AccessibilityService {
    @Override
    public void onServiceConnected() {
        AppServiceHolder.service = this;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Intentionally empty — we only need GLOBAL_ACTION_LOCK_SCREEN.
    }

    @Override
    public void onInterrupt() {
        // Required override.
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
