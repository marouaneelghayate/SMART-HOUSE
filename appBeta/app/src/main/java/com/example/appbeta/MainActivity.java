package com.example.appbeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements EditDialogFragment.EditDialogListener{
    //public static BluetoothSocket bluetoothSocket;
    //public static boolean connected = false;
    private Commands commands;
    private State state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller;
        controller = WindowCompat.getInsetsController(getWindow(),getWindow().getDecorView());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        controller.hide(WindowInsetsCompat.Type.systemBars());
        setContentView(R.layout.activity_main);
        commands=Commands.getInstance(this);
        state=State.getInstance(this);
    }
    @Override
    public void onDialogPositiveClick(EditDialogFragment dialog) {
        // User touched the dialog's positive button
        String newCommand = dialog.getNewCommand();
        String oldCommand = dialog.getOldCommand();
        if(newCommand.equals("")){
            Toast.makeText(this,"Nouvelle commande invalide",Toast.LENGTH_LONG);
        }
        else{
            commands.setCommand(oldCommand,newCommand);
        }
    }

    @Override
    public void onDialogNegativeClick(EditDialogFragment dialog) {

    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}