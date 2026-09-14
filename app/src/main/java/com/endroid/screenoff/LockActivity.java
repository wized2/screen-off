package com.endroid.screenoff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/**
 * Transparent launcher entry: lock immediately when the accessibility service is ready,
 * otherwise open setup. Retries briefly if the service is enabled but not yet bound.
 */
public class LockActivity extends Activity {
    private static final int RETRY_MS = 80;
    private static final int MAX_ATTEMPTS = 8;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attempts = 0;
        tryLockOrSetup();
    }

    private void tryLockOrSetup() {
        if (LockHelper.tryLock()) {
            finish();
            return;
        }
        if (LockHelper.isAccessibilityServiceEnabled(this)) {
            // Service is toggled on but process may have been killed — wait for rebind.
            if (attempts < MAX_ATTEMPTS) {
                attempts++;
                handler.postDelayed(this::tryLockOrSetup, RETRY_MS);
                return;
            }
        }
        startActivity(new Intent(this, SetupActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
