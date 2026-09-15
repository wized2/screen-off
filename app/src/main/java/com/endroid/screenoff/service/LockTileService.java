package com.endroid.screenoff.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import com.endroid.screenoff.R;
import com.endroid.screenoff.ui.SetupActivity;
import com.endroid.screenoff.util.LockHelper;

/** Quick Settings tile: one tap to lock when accessibility is ready. */
public class LockTileService extends TileService {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTileState();
    }

    @Override
    public void onClick() {
        if (LockHelper.tryLock(getApplicationContext())) {
            return;
        }
        if (LockHelper.isAccessibilityServiceEnabled(this)) {
            handler.postDelayed(() -> {
                if (LockHelper.tryLock(getApplicationContext())) {
                    return;
                }
                handler.postDelayed(() -> {
                    if (LockHelper.tryLock(getApplicationContext())) {
                        return;
                    }
                    Toast.makeText(this, R.string.service_not_ready, Toast.LENGTH_SHORT).show();
                    openSetup();
                }, 180);
            }, 120);
            return;
        }
        openSetup();
    }

    private void openSetup() {
        Intent intent = new Intent(this, SetupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 34) {
            PendingIntent pi = PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            startActivityAndCollapse(pi);
        } else {
            startActivityAndCollapse(intent);
        }
    }

    private void updateTileState() {
        Tile tile = getQsTile();
        if (tile == null) {
            return;
        }
        boolean enabled = LockHelper.isAccessibilityServiceEnabled(this);
        tile.setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tile.setSubtitle(getString(enabled ? R.string.tile_ready : R.string.tile_setup_needed));
        }
        tile.updateTile();
    }
}
