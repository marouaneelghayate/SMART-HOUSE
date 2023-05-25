package com.example.appbeta;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlFragment extends Fragment {
    private Button ouvrePorte,fermePorte,ouvreFenetre,fermeFenetre,lightsOn,lightsOff,edit,consult;
    private ImageView record;
    private Commands commands;
    private TransferThread transferThread;
    private Utils utils;
    private Transcriber transcriber;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControlFragment newInstance(String param1, String param2) {
        ControlFragment fragment = new ControlFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        transcriber = new Transcriber();
        utils = Utils.getInstance(getActivity());
        utils.setConnected(false);
        commands=utils.getCommands();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_control, container, false);
        getViews(view);
        setViewsText();
        setViewsListeners();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setViewsText();
    }

    @Override
    public void onResume() {
        super.onResume();
        setViewsText();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void getViews(View view){
        ouvrePorte=view.findViewById(R.id.ouvrePorte);
        fermePorte=view.findViewById(R.id.fermePorte);
        ouvreFenetre = view.findViewById(R.id.ouvreFenetres);
        fermeFenetre = view.findViewById(R.id.fermeFenetres);
        lightsOn = view.findViewById(R.id.lightsOn);
        lightsOff = view.findViewById(R.id.lightsOff);
        record=view.findViewById(R.id.record);
        edit=view.findViewById(R.id.edit);
        consult = view.findViewById(R.id.consult);
    }
    private void setViewsText(){
        commands=Commands.getInstance(getActivity());
        ouvrePorte.setText(commands.getValue("ouvre la porte"));
        fermePorte.setText(commands.getValue("ferme la porte"));
        ouvreFenetre.setText(commands.getValue("ouvre les fenêtres"));
        fermeFenetre.setText(commands.getValue("ferme les fenêtres"));
        lightsOn.setText(commands.getValue("allume la lampe"));
        lightsOff.setText(commands.getValue("éteins la lampe"));
    }
    private void setViewsListeners(){
        ouvreFenetre.setOnClickListener(view1 ->{
            if(utils.isConnected()){
                Byte byteCommande = commands.getCommande(ouvreFenetre.getText().toString());
                Toast.makeText(getActivity(), ouvreFenetre.getText().toString(), Toast.LENGTH_LONG);
                if (byteCommande != null) {
                    transferThread.write(byteCommande);
                } else {
                    Toast.makeText(getActivity(), "commande invalide", Toast.LENGTH_LONG);
                }
            }
            else{
                connect(view1);
            }
        });
        fermeFenetre.setOnClickListener(view1 ->{
            if(utils.isConnected()){
                Byte byteCommande = commands.getCommande(fermeFenetre.getText().toString());
                Toast.makeText(getActivity(), fermeFenetre.getText().toString(), Toast.LENGTH_LONG);
                if (byteCommande != null) {
                    transferThread.write(byteCommande);
                } else {
                    Toast.makeText(getActivity(), "commande invalide", Toast.LENGTH_LONG);
                }
            }
            else{
                connect(view1);
            }
        });
        ouvrePorte.setOnClickListener(view1 ->{
            if(utils.isConnected()){
                Byte byteCommande = commands.getCommande(ouvrePorte.getText().toString());
                Toast.makeText(getActivity(), ouvrePorte.getText().toString(), Toast.LENGTH_LONG);
                if (byteCommande != null) {
                    transferThread.write(byteCommande);
                } else {
                    Toast.makeText(getActivity(), "commande invalide", Toast.LENGTH_LONG);
                }
            }
            else{
                connect(view1);
            }
        });
        fermePorte.setOnClickListener(view1 ->{
            if(utils.isConnected()){
                Byte byteCommande = commands.getCommande(fermePorte.getText().toString());
                Toast.makeText(getActivity(), fermePorte.getText().toString(), Toast.LENGTH_LONG);
                if (byteCommande != null) {
                    transferThread.write(byteCommande);
                } else {
                    Toast.makeText(getActivity(), "commande invalide", Toast.LENGTH_LONG);
                }
            }
            else{
                connect(view1);
            }
        });
        lightsOn.setOnClickListener(view1 ->{
            if(utils.isConnected()){
                Byte byteCommande = commands.getCommande(lightsOn.getText().toString());
                Toast.makeText(getActivity(), lightsOn.getText().toString(), Toast.LENGTH_LONG);
                if (byteCommande != null) {
                    transferThread.write(byteCommande);
                } else {
                    Toast.makeText(getActivity(), "commande invalide", Toast.LENGTH_LONG);
                }
            }
            else{
                connect(view1);
            }
        });
        lightsOff.setOnClickListener(view1 ->{
            if(utils.isConnected()){
                Byte byteCommande = commands.getCommande(lightsOff.getText().toString());
                Toast.makeText(getActivity(), lightsOff.getText().toString(), Toast.LENGTH_LONG);
                if (byteCommande != null) {
                    transferThread.write(byteCommande);
                } else {
                    Toast.makeText(getActivity(), "commande invalide", Toast.LENGTH_LONG);
                }
            }
            else{
                connect(view1);
            }
        });
        record.setOnClickListener(view1->{

            if(utils.isConnected())
                startActivityForResult(transcriber.getIntent(),69);
            else
                connect(view1);
        });
        edit.setOnClickListener(view1->{
            Navigation.findNavController(view1).navigate(R.id.action_controlFragment_to_editFragment);
            utils.setConnected(false);
        });
        consult.setOnClickListener(view1->{

            if(utils.isConnected()){
                utils.setConnected(false);

                Navigation.findNavController(view1).navigate(R.id.action_controlFragment_to_consultFragment);
            }
            else{
                connect(view1);
            }




        });
    }
    private void connect(View view){
        if(utils.getBluetoothSocket() == null){
            Navigation.findNavController(view).navigate(R.id.action_controlFragment_to_firstFragment);
        }
        else if (!utils.isConnected()){
            transferThread = new TransferThread(utils);
            utils.setTransferThread(transferThread);
            transferThread.start();
            view.performClick();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 69 && resultCode == RESULT_OK) {
            String transcription = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0).toLowerCase();
            Byte byteCommande = commands.getCommande(transcription);
            if (byteCommande != null) {
                transferThread.write(byteCommande);
            } else {
                Toast.makeText(getActivity(), "commande invalide", Toast.LENGTH_LONG);
            }
        }
    }

}