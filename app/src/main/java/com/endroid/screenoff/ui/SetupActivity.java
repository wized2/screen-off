package com.endroid.screenoff.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.endroid.screenoff.R;
import com.endroid.screenoff.util.AppServiceHolder;
import com.endroid.screenoff.util.LockHelper;
import com.endroid.screenoff.util.Prefs;

import java.text.DateFormat;
import java.util.Date;

/**
 * Material 3 setup screen: accessibility permission, lock action, preferences.
 */
public class SetupActivity extends Activity {

    private MaterialTextView status;
    private MaterialTextView version;
    private MaterialButton btnLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        status = findViewById(R.id.setup_status);
        version = findViewById(R.id.setup_version);
        btnLock = findViewById(R.id.btn_try_lock);
        MaterialButton btnPrimary = findViewById(R.id.btn_open_settings);

        btnPrimary.setOnClickListener(v ->
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));

        btnLock.setOnClickListener(v -> {
            if (LockHelper.tryLock(this)) {
                finish();
            } else {
                status.setText(R.string.service_not_ready);
            }
        });

        // Long-press version label toggles haptic lock feedback
        version.setOnLongClickListener(v -> {
            boolean next = !Prefs.vibrateOnLock(this);
            Prefs.setVibrateOnLock(this, next);
            refreshUi();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUi();
    }

    private void refreshUi() {
        boolean enabled = LockHelper.isAccessibilityServiceEnabled(this);
        boolean ready = enabled && AppServiceHolder.service != null;

        if (ready) {
            status.setText(R.string.setup_ready);
            btnLock.setVisibility(View.VISIBLE);
        } else if (enabled) {
            status.setText(R.string.setup_enabled_waiting);
            btnLock.setVisibility(View.VISIBLE);
        } else {
            status.setText(R.string.setup_needed);
            btnLock.setVisibility(View.GONE);
        }

        StringBuilder vb = new StringBuilder(getString(R.string.app_version_label));
        vb.append(Prefs.vibrateOnLock(this) ? " · haptic on" : " · haptic off");
        long at = Prefs.lastLockAt(this);
        long locks = Prefs.lockCount(this);
        if (at > 0L) {
            vb.append(" · last lock ")
                    .append(DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(at)));
        }
        if (locks > 0) vb.append(" · locks ").append(locks);
        vb.append("\n(long-press version to toggle haptic)");
        version.setText(vb.toString());
    }
}
