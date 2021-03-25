package com.whh.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView tv;

    private CameraManager manager;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);

        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    close();
                } else {
                    open();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        open();
    }

    private void open() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                manager.setTorchMode("0", true);

                btn.setBackground(getResources().getDrawable(R.drawable.icon_toggle_on));

                tv.setText(getString(R.string.current_open));
                tv.setTextColor(getResources().getColor(R.color.current_open));

                isOpen = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void close() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                manager.setTorchMode("0", false);

                btn.setBackground(getResources().getDrawable(R.drawable.icon_toggle_off));

                tv.setText(getString(R.string.current_close));
                tv.setTextColor(getResources().getColor(R.color.current_close));

                isOpen = false;

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if(isOpen){
            close();
        }
        super.onDestroy();
    }
}