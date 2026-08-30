# Keep Material components
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# Keep AndroidX core
-keep class androidx.** { *; }
-dontwarn androidx.**

# Keep widget provider (important!)
-keep public class * extends android.appwidget.AppWidgetProvider

# Keep TypefaceSpan (used in SpannableString)
-keep class android.text.style.TypefaceSpan { *; }

# Keep all classes used in RemoteViews
-keep class * extends android.widget.RemoteViews { *; }

# Keep your MainActivity and inner ClockWidget
-keep class com.endroid.clock.MainActivity { *; }
-keep class com.endroid.clockwidget.MainActivity$ClockWidget { *; }

# Keep SpannableString
-keep class android.text.SpannableString { *; }