package com.m2049r.xmrwallet.login;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.m2049r.xmrwallet.R;
import com.m2049r.xmrwallet.dialog.DonationFragment;
import com.m2049r.xmrwallet.layout.Toolbar;

public class LoginActivity extends AppCompatActivity {

    static final String TAG = "LoginActivity";


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState,
            @Nullable final PersistableBundle persistentState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setOnButtonListener(new Toolbar.OnButtonListener() {
            @Override
            public void onButton(int type) {
                switch (type) {
                    case Toolbar.BUTTON_BACK:
                        onBackPressed();
                        break;
                    case Toolbar.BUTTON_CLOSE:
                        finish();
                        break;
                    case Toolbar.BUTTON_DONATE:
                        DonationFragment.display(getSupportFragmentManager());
                        break;
                    case Toolbar.BUTTON_NONE:
                    default:
                        Log.e(TAG, "Button " + type + "pressed - how can this be?");
                }
            }
        });

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (loginFragment == null) {
            loginFragment = new LoginFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, loginFragment);
            transaction.commit();
        }

        new LoginPresenter(loginFragment);

        //TODO -> into presenter
//        if (Helper.getWritePermission(this)) {
//            if (savedInstanceState == null) startLoginFragment();
//        } else {
//            Log.i(TAG, "Waiting for permissions");
//        }

    }
}
