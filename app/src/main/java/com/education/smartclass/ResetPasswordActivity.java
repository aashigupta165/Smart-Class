package com.education.smartclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextView nextbtn;
    private EditText number;
    private int randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        number = findViewById(R.id.number);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        nextbtn = findViewById(R.id.nextbtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });
    }

    public void sendSms() {
        try {
            String apiKey = "apikey=" + "TY/2/f1h88c-MZJCXJaNUZyfBzg3kdpTCmKEf1ccbR";
            Random random = new Random();
            randomNumber = random.nextInt(999999);
            String message = "&message=" + "Your OTP for reset password is" + randomNumber;
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + number.getText().toString();

            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            Toast.makeText(ResetPasswordActivity.this, "OTP Send Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ResetPasswordActivity.this, OtpActivity.class);
            intent.putExtra("OTP", randomNumber);
            startActivity(intent);
            //return stringBuffer.toString();
        } catch (Exception e) {
//            System.out.println("Error SMS "+e);
//            return "Error "+e;
            Toast.makeText(ResetPasswordActivity.this, "Error Sms " + e, Toast.LENGTH_SHORT).show();
        }
    }
}