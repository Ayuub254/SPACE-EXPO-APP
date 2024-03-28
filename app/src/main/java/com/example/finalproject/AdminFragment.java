package com.example.finalproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class AdminFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdminAdapter adapter;
    private List<User> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewAdmin);
        userList = new ArrayList<>();
        adapter = new AdminAdapter(userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        loadUsers();
        return view;
    }
    private void loadUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("USERS")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String username = document.getString("username");
                            String email = document.getString("email");
                            User user = new User(id, username, email);
                            userList.add(user);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}
