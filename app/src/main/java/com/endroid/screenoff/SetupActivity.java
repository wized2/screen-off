package com.endroid.screenoff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class SetupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(64, 64, 64, 64);

        MaterialTextView title = new MaterialTextView(this);
        title.setText("Screen Off");
        title.setTextSize(28);
        title.setTypeface(title.getTypeface(), android.graphics.Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 32);

        MaterialTextView desc = new MaterialTextView(this);
        desc.setText("To turn off the screen like a power button\n(keeping fingerprint & face unlock active),\nplease enable the \"Screen Off\" service\nin your Accessibility settings.");
        desc.setTextSize(16);
        desc.setGravity(Gravity.CENTER);
        desc.setPadding(0, 0, 0, 48);

        MaterialButton btnEnable = new MaterialButton(this);
        btnEnable.setText("Open Accessibility Settings");
        btnEnable.setAllCaps(false);
        btnEnable.setPadding(48, 16, 48, 16);
        btnEnable.setTextSize(16);

        btnEnable.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });

        MaterialTextView hint = new MaterialTextView(this);
        hint.setText("After enabling, tap the app icon again\nto instantly lock your screen.");
        hint.setTextSize(14);
        hint.setGravity(Gravity.CENTER);
        hint.setPadding(0, 48, 0, 0);

        root.addView(title);
        root.addView(desc);
        root.addView(btnEnable);
        root.addView(hint);
        setContentView(root);
    }
}