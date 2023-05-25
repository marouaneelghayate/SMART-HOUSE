package com.example.appbeta;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditDialogFragment extends DialogFragment {
    private String newCommand,oldCommand;
    private EditText editText;
    private ImageView recordButton;
    private Transcriber transcriber;
    private RerenderDialog reRender;
    public interface EditDialogListener {
        public void onDialogPositiveClick(EditDialogFragment dialog);
        public void onDialogNegativeClick(EditDialogFragment dialog);
    }
    EditDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (EditDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement EditDialogListener");
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.fragment_edit_dialog,null);
        transcriber=new Transcriber();
        editText = view.findViewById(R.id.editText);
        recordButton = view.findViewById(R.id.recordDialog);
        reRender= new RerenderDialog(getActivity());
        recordButton.setOnClickListener(view1 -> {
            if(isAdded()){
                startActivityForResult(transcriber.getIntent(),70);
            }
        });
        builder.setView(view);
        builder//.setMessage("Entrez la nouvelle commande")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        newCommand=editText.getText().toString();
                        listener.onDialogPositiveClick(EditDialogFragment.this);
                        reRender.show();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(EditDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 70 && resultCode == RESULT_OK) {
            newCommand = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0).toLowerCase();
            editText.setText(newCommand);
        }
    }
    public String getNewCommand(){
        return newCommand;
    }
    public String getOldCommand() { return oldCommand; }
    public void setOldCommand(String oldCommand){
        this.oldCommand = oldCommand;
    }
}
