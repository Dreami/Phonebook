package com.example.phonebook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    PhoneStateListener mListener;
    TelephonyManager mTelMgr;
    TextView mapleCel, mapleCasa, kanun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListener = new CallEndedListener();
        mTelMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

        mapleCel = (TextView) findViewById(R.id.textView);
        mapleCasa = (TextView) findViewById(R.id.textView2);
        kanun = (TextView) findViewById(R.id.textView3);

        mapleCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone("8117993915");
            }
        });

        mapleCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone("5283577651");
            }
        });

        kanun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone("31640360493");
            }
        });

    }

    public void callPhone(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            try {
                mTelMgr.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
                Intent myCallIntent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null));
                startActivity(myCallIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class CallEndedListener extends PhoneStateListener {
        boolean called = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            if (state == TelephonyManager.CALL_STATE_OFFHOOK)
                called = true;

            if (called && state == TelephonyManager.CALL_STATE_IDLE) {
                called = false;
                mTelMgr.listen(this, PhoneStateListener.LISTEN_NONE);
            }
        }
    }
}
