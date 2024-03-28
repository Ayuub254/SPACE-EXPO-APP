package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder>{

    private List<User> userList;
    public AdminAdapter(List<User> userList) {
        this.userList = userList;
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.usernameTextView.setText(user.getUsername());
        holder.emailTextView.setText(user.getEmail());

        // Fetch user data from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("USERS").document(user.getId());
        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String userName = documentSnapshot.getString("Name");
                String userEmail = documentSnapshot.getString("Email_Id");
                holder.usernameTextView.setText(userName);
                holder.emailTextView.setText(userEmail);
            } else {
                // User document does not exist
                holder.usernameTextView.setText(""); // Set an empty name or handle it as needed
                holder.emailTextView.setText(""); // Set an empty email or handle it as needed
            }
        }).addOnFailureListener(e -> {
            // Handle failure to fetch user document
            holder.usernameTextView.setText(""); // Set an empty name or handle it as needed
            holder.emailTextView.setText(""); // Set an empty email or handle it as needed
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Delete user from Firestore
            db.collection("USERS").document(user.getId()).delete()
                    .addOnSuccessListener(aVoid -> {
                        // Sign out user locally
                        FirebaseAuth.getInstance().signOut();

                        // Remove user from the list and notify the adapter
                        userList.remove(position);
                        notifyItemRemoved(position);
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure to delete user
                    });
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;
        public TextView emailTextView;
        public ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
