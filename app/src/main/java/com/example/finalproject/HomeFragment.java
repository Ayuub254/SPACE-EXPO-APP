package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton buttonNebulas = view.findViewById(R.id.imageButtonNebulas);
        buttonNebulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NebulasActivity.class));
            }
        });

        ImageButton buttonGalaxies = view.findViewById(R.id.imageButtonGalaxy);
        buttonGalaxies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GalaxiesActivity.class));
            }
        });

        ImageButton buttonBlackHoles = view.findViewById(R.id.imageButtonBlackholes);
        buttonBlackHoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BlackHolesActivity.class));
            }
        });

        ImageButton buttonStars = view.findViewById(R.id.imageButtonStars);
        buttonStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StarsActivity.class));
            }
        });

        Button buttonSolarSystem = view.findViewById(R.id.buttonSolarSystem);
        buttonSolarSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SolarSystem.class));
            }
        });

        Button buttonMissions = view.findViewById(R.id.buttonMissions);
        buttonMissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Missions.class));
            }
        });

        Button buttonSpaceCrafts = view.findViewById(R.id.buttonSpacecrafts);
        buttonSpaceCrafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SpaceCrafts.class));
            }
        });

        return view;
    }
}
