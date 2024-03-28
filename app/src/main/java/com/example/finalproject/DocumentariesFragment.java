package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DocumentariesFragment extends Fragment {
    public DocumentariesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documentaries, container, false);

        // Find the image buttons by their IDs
        ImageButton imageButtonPlanetsQuiz = view.findViewById(R.id.imageButtonPlanetsQuiz);
        ImageButton imageButtonGalaxiesQuiz = view.findViewById(R.id.imageButtonGalaxiesQuiz);
        ImageButton imageButtonNebulaeQuiz = view.findViewById(R.id.imageButtonNebulaeQuiz);
        ImageButton imageButtonSpaceCraftsQuiz = view.findViewById(R.id.imageButtonSpaceCraftsQuiz);
        ImageButton imageButtonBackHome = view.findViewById(R.id.imageButtonBackHome);


        // Set click listeners for each image button
        imageButtonPlanetsQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your functionality for imageButton1 here
                Toast.makeText(getActivity(), "Image Button Planets Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), PlanetQuiz.class));
            }
        });

        imageButtonGalaxiesQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your functionality for imageButton2 here
                Toast.makeText(getActivity(), "Image Button Galaxies Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), GalaxyQuiz.class));
            }
        });

        imageButtonNebulaeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your functionality for imageButton3 here
                Toast.makeText(getActivity(), "Image Button Nebulae Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), NebulasQuiz.class));

            }
        });

        imageButtonSpaceCraftsQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your functionality for imageButton3 here
                Toast.makeText(getActivity(), "Image Button space crafts Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), SpaceCraftsQuiz.class));
            }
        });

        // Set click listener for the back button to navigate back to the main activity
        imageButtonBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity to go back to the previous activity
                getActivity().finish();
            }
        });
        return view;
    }
}
