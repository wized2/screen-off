package com.endroid.screenoff.util;

import com.endroid.screenoff.service.LockAccessibilityService;

/**
 * Holds the bound {@link LockAccessibilityService} instance.
 * Set by the system when the accessibility service connects.
 */
public final class AppServiceHolder {

    public static volatile LockAccessibilityService service;

    private AppServiceHolder() {
    }
}
