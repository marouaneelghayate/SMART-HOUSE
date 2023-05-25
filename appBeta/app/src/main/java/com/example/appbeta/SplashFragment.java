package com.example.appbeta;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;
    private ImageView start;
    private final int DELAY_MILLS=3000;//3000;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SplashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SplashFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SplashFragment newInstance(String param1, String param2) {
        SplashFragment fragment = new SplashFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        start = view.findViewById(R.id.start);
        sharedPreferences = getActivity().getSharedPreferences("first_time", Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();
        if (!firstTime()){
            start.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_controlFragment);
                }
            },DELAY_MILLS);
        }
        else{
            start.setOnClickListener(view1 -> {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_firstFragment);
            });
        }
        sharedPreferences.getBoolean("first time",true);
        return view;
    }
    private boolean firstTime(){
        if(sharedPreferences.getBoolean("first time",true)){
            sharedEditor.putBoolean("first time",false);
            sharedEditor.commit();
            sharedEditor.apply();
            return true;
        }
        return false;
    }
}