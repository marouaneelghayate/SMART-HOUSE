package com.example.appbeta;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class RerenderDialog {
    private Dialog dialog;
    private Button restartButton;

    public RerenderDialog(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.rerender_dialog);

        restartButton = dialog.findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the activity object using the context
                Activity activity = (Activity) context;

                // Start the activity again
                Intent intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                activity.startActivity(intent);
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                activity.finishAffinity();

                // Dismiss the dialog
                dialog.dismiss();
            }
        });
    }

    public void show() {
        dialog.show();
    }
}

