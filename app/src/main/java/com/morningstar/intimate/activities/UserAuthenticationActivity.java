/*
 * Created by Sujoy Datta. Copyright (c) 2019. All rights reserved.
 *
 * To the person who is reading this..
 * When you finally understand how this works, please do explain it to me too at sujoydatta26@gmail.com
 * P.S.: In case you are planning to use this without mentioning me, you will be met with mean judgemental looks and sarcastic comments.
 */

package com.morningstar.intimate.activities;

import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.widget.TextView;
import android.widget.Toast;

import com.morningstar.intimate.R;
import com.morningstar.intimate.managers.BiometricManager;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserAuthenticationActivity extends AppCompatActivity {

    @BindView(R.id.setUpFingerprint)
    TextView textViewSetFingerprint;
    @BindView(R.id.setUpPinCode)
    TextView textViewSetPin;

    private CancellationSignal cancellationSignal;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;
    private BiometricPrompt biometricPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.setUpFingerprint)
    public void setUpFingerprint() {
        biometricPrompt = BiometricManager.getBiometricPrompt(this);

        if (biometricPrompt != null) {
            if (BiometricManager.getAuthenticationCallback(this) != null) {
                cancellationSignal = BiometricManager.getCancellationSignal();
                cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(UserAuthenticationActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                authenticationCallback = BiometricManager.getAuthenticationCallback(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    biometricPrompt.authenticate(cancellationSignal, getMainExecutor(), authenticationCallback);
                }
            }
        }
    }

    @OnClick(R.id.setUpPinCode)
    public void setUpPinCode() {

    }
}
