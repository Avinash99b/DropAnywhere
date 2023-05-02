package com.azstories.dropanywhere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Receive extends AppCompatActivity {

    String result1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        TextView textView = findViewById(R.id.text);
        EditText editText = findViewById(R.id.id);
        Button send = findViewById(R.id.copy);
        sendpost sendpost = new sendpost();

        Button copybtn = findViewById(R.id.copy);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = sendpost.gettexxt(editText.getText().toString());
                textView.setText(result);
                result1 = result;
            }
        });

        copybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                ClipData clipData = ClipData.newPlainText("text" ,result1 );

                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(Receive.this, "Copied data to clipboard successfully" , Toast.LENGTH_SHORT).show();
            }
        });

    }
}