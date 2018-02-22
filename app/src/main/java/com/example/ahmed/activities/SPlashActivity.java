package com.example.ahmed.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ahmed.R;

import static com.facebook.internal.CallbackManagerImpl.RequestCodeOffset.Login;

public class SPlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread t=new Thread() {
            public void run() {
                try {

                    sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(SPlashActivity.this,MainActivity.class);
                    startActivity(i);
                }}};
        t.start();

}
}
