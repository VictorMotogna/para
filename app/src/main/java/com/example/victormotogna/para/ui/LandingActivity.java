package com.example.victormotogna.para.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.victormotogna.para.R;
import com.example.victormotogna.para.dal.AppDatabase;
import com.example.victormotogna.para.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

/**
 * Created by victormotogna on 10/29/17.
 */

@EActivity(R.layout.activity_landing)
public class LandingActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 13;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Click(R.id.button_google_login)
    public void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private static User addUserToDb(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Signedin", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            User user = new User(acct.getDisplayName(), acct.getEmail());
            user = addUserToDb(AppDatabase.getUserAppDatabase(this), user);
            goToProfile(user);
        } else {
            // could not sign in, restarting activity
            restartActivity();
        }
    }

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void goToProfile(User user) {
        Intent intent = new Intent(LandingActivity.this, UserProfileActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        bundle.putString("caller", "GoogleSignIn");
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
