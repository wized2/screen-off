package com.endroid.screenoff.util;

import android.content.Context;
import android.content.SharedPreferences;

public final class Prefs {
    private static final String NAME = "screen_off";

    private Prefs() {}

    private static SharedPreferences p(Context c) {
        return c.getApplicationContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static boolean vibrateOnLock(Context c) {
        return p(c).getBoolean("vibrate_on_lock", true);
    }

    public static void setVibrateOnLock(Context c, boolean on) {
        p(c).edit().putBoolean("vibrate_on_lock", on).apply();
    }

    public static long lastLockAt(Context c) {
        return p(c).getLong("last_lock_at", 0L);
    }

    public static void markLocked(Context c) {
        p(c).edit().putLong("last_lock_at", System.currentTimeMillis()).apply();
    }
}
