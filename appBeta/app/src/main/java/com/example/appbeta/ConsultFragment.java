package com.example.appbeta;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultFragment extends Fragment {
    private TextView door, window, light ;
    private ImageView doorStatus, windowStatus, lightStatus,tempstatus,humidStatus ;

    // TODO: Rename parameter arguments, choose names that match
    TextView temp,humid;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TransferThread transferThread;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConsultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultFragment newInstance(String param1, String param2) {
        ConsultFragment fragment = new ConsultFragment();
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
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_consult, container, false);
        //transferThread = new TransferThread(Utils.getInstance(getActivity()));
        //transferThread.start();
        temp = view.findViewById(R.id.temperature);
        tempstatus = view.findViewById(R.id.tempStatus);
        humidStatus = view.findViewById(R.id.humidStatus);
        humid = view.findViewById(R.id.humidity);
        door = view.findViewById(R.id.door) ;
        doorStatus = view.findViewById(R.id.doorStatus);

        if(State.doorOpen) {
            door.setText("ouverte");
            doorStatus.setImageResource(R.drawable.online_dot);
        }
        else{
            door.setText("fermée");
            doorStatus.setImageResource(R.drawable.offline_dot);
        }



        /* === Window section === */
        window = view.findViewById(R.id.window) ;
        windowStatus = view.findViewById(R.id.windowStatus);

        if(State.windowsOpen) {
            window.setText("ouvertes");
            windowStatus.setImageResource(R.drawable.online_dot);
        }
        else{
            window.setText("fermées");
            windowStatus.setImageResource(R.drawable.offline_dot);
        }


        /* === Light Section === */

        light = view.findViewById(R.id.light);
        lightStatus = view.findViewById(R.id.lightStatus) ;

        if(State.lightsOn) {
            light.setText("allumé");
            lightStatus.setImageResource(R.drawable.online_dot);
        }
        else{
            light.setText("éteinte");
            lightStatus.setImageResource(R.drawable.offline_dot);
        }

       try {
            getData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return view;


    }
    private void getData() throws InterruptedException {
        byte[] arr = Utils.getInstance(getActivity()).getData();
        String[] message = new String(arr).split("sep");
        if (message.length < 3) {
            Log.d("array elements", new String(arr));
        } else {
            String temperature = message[1].substring(0, 2);
            String humidite = message[2].substring(0, 2);
            if (Integer.parseInt(temperature) > 15) {
                if (Integer.parseInt(temperature) > 25)
                    tempstatus.setImageResource(R.drawable.hot);
                else
                    tempstatus.setImageResource(R.drawable.warm);
            } else
                tempstatus.setImageResource(R.drawable.cold);
            temp.setText(temperature + "°C");
            humid.setText(humidite + "%");
            if (Integer.parseInt(humidite) > 50)
                humidStatus.setImageResource(R.drawable.low_humidity);
            else
                humidStatus.setImageResource(R.drawable.high_humidity);

        }
    }
}