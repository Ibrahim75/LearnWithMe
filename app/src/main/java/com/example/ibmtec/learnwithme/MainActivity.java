package com.example.ibmtec.learnwithme;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
private static final int RC_SIGN_IN = 0;
private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth auth = FirebaseAuth.getInstance();



        if(auth.getCurrentUser() != null)
        {
            //utilisateur deja connecté
            Log.d("AUTH", auth.getCurrentUser().getEmail());

        }
        else
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setProviders(AuthUI.FACEBOOK_PROVIDER,AuthUI.EMAIL_PROVIDER,AuthUI.GOOGLE_PROVIDER).build(), RC_SIGN_IN);
        }

        findViewById(R.id.log_out_button).setOnClickListener(this);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN)
        {
            if(resultCode == RESULT_OK)
            {
                //utilisateur connecté
                Log.d("AUTH", auth.getCurrentUser().getEmail());
            }
            else
            {
                Log.d("AUTH", "Non Authentifie");
            }
        }



    }

    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.log_out_button)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    Log.d("AUTH","L'utilisateur s'est bien déconnecté");
                    finish();
                }
            });
        }


    }


}