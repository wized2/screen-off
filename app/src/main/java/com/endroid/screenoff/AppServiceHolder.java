package com.endroid.screenoff;

/** Holds the live accessibility service instance (set by the system when bound). */
public final class AppServiceHolder {
    public static volatile LockAccessibilityService service;

    private AppServiceHolder() {}
}
