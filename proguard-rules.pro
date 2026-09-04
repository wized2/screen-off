# Screen Off — proguard-rules.pro

# System-invoked entry points (referenced only via AndroidManifest.xml,
# never directly from your code — R8 can't trace these, so they need
# explicit keeps or the OS will fail to find/bind them)
-keep class com.endroid.screenoff.LockAccessibilityService { *; }
-keep class com.endroid.screenoff.LockTileService { *; }
-keep class com.endroid.screenoff.LockActivity { *; }
-keep class com.endroid.screenoff.SetupActivity { *; }

# Suppress build warnings from androidx/material (doesn't affect
# shrinking/size — these libraries ship their own consumer rules,
# and every Material/AndroidX class you use directly, like
# MaterialButton/MaterialTextView in SetupActivity, is already kept
# automatically by R8's reachability analysis)
-dontwarn androidx.**
-dontwarn com.google.android.material.**

# Crash-report readability
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
