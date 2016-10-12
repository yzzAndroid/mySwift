package com.qianfeng.yyz.myswift.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qianfeng.yyz.myswift.R;
import com.qianfeng.yyz.myswift.callback.ISendCallback;
import com.qianfeng.yyz.myswift.http.HttpLoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendEmailActivity extends AppCompatActivity implements ISendCallback {

    @BindView(R.id.send_email_contacts)
    EditText mContacts;
    @BindView(R.id.send_email_contact)
    EditText mContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        ButterKnife.bind(this);
    }

    public void back(View view) {
        finish();
    }

    public void send(View view) {
        String desc = mContacts.getText().toString();
        String contact = mContact.getText().toString();
        if (TextUtils.isEmpty(desc)||TextUtils.isEmpty(contact)){
            Toast.makeText(SendEmailActivity.this,getString(R.string.send_email_limit), Toast.LENGTH_SHORT).show();
        }
        HttpLoginUtils.sendEmail(contact,desc,this,this);
    }

    @Override
    public void callback(boolean flag, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SendEmailActivity.this, msg, Toast.LENGTH_SHORT).show();
                mContacts.setText("");
                mContact.setText("");
            }
        });

    }
}
