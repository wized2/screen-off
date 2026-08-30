package com.endroid.screenoff;

import android.service.quicksettings.TileService;
import android.view.accessibility.AccessibilityManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LockTileService extends TileService {

    @Override
    public void onClick() {
        if (isAccessibilityServiceEnabled()) {
            if (AppServiceHolder.service != null) {
                AppServiceHolder.service.lockScreen(); // locks instantly
                // The Quick Settings panel will close automatically
            } else {
                Toast.makeText(this, "Accessibility service not ready", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SetupActivity.class));
            }
        } else {
            startActivity(new Intent(this, SetupActivity.class));
        }
    }

    private boolean isAccessibilityServiceEnabled() {
        AccessibilityManager am = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (am == null) return false;
        for (android.accessibilityservice.AccessibilityServiceInfo info :
                am.getEnabledAccessibilityServiceList(
                        android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_ALL_MASK)) {
            if (info.getResolveInfo().serviceInfo.packageName.equals(getPackageName())) {
                return true;
            }
        }
        return false;
    }
}