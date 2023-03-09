package com.example.registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateFragment extends Fragment {
    final String TAG = "UPDATE";
    View view;
    EditText emailTxt, passWordTxt;
    Button updateButtonEmail, updateButtonPassword;
    FirebaseUser user;
    String updatedEmail, updatedPassword;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        emailTxt = view.findViewById(R.id.editTextTextEmailAddress3);
        passWordTxt = view.findViewById(R.id.editTextTextPassword);
        updateButtonEmail = view.findViewById(R.id.buttonUpdateEmail);
        updateButtonPassword = view.findViewById(R.id.buttonUpdatePassword);

        //UPDATE EMAIL
        updateButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    updatedEmail = emailTxt.getText().toString();
                    //validation
                    if(!updatedEmail.isEmpty()){
                        updateEmail(updatedEmail); //method call
                        showToast("Email Updated");
                    }else{
                        showToast("Please enter an email");
                    }
            }
        });
        //UPDATE PASSWORD
        updateButtonPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedPassword = passWordTxt.getText().toString();
                //validation
                if(!updatedPassword.isEmpty()){
                    updatePassword(updatedPassword); //method call
                    showToast("Password Changed!");
                }else{
                    showToast("Please enter a password");
                }
            }
        });

        return view;
    }

    private void logOut(){
        FirebaseAuth.getInstance().signOut(); //logout user
        loginActivity(); //re authenticate
    }

    private void loginActivity() {
        Intent intent = new Intent(getActivity(),Login.class);
        startActivity(intent);
    }

    private void updateEmail(String newEmail){
        user.updateEmail(newEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG,"Email Updated");

                        }
                        else{
                            showToast("Failed to update email!");
                        }
                    }
                });
    }

    private void updatePassword(String newPassword){
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "Password updated.");
                        }
                        else{
                            showToast("Failed to update password!");
                        }
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
    private void reAuth(AuthCredential credential){
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User re-authenticated.");
                            updateEmail(updatedEmail);
                            updatePassword(updatedPassword);
                        } else {
                            Log.d(TAG, "User re-authentication failed.");
                        }
                    }
                });
    }**/

    /**
    public void search(){
        //current user's FirebaseUser object
        user = FirebaseAuth.getInstance().getCurrentUser();
        // Define a reference to the user's data in Firestore
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users")
                .document(user.getUid());
        // Retrieve the user's data from Firestore
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String email = documentSnapshot.getString("email");
                    String password = documentSnapshot.getString("password");
                    emailTxt.setText(email);
                    passWordTxt.setText(password);
                } else {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle error or take other actions
            }
        });
    }**/

}