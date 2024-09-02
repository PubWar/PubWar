package com.pubwar.tv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EnterUrlActivity extends Activity {
    EditText urlEditText;
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_url);

         urlEditText = findViewById(R.id.url_edit_text);
         button = findViewById(R.id.button_go);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      openMainScreen();
            }
        });

        urlEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Handle action here
                    openMainScreen();
                    return true; // Return true if you have handled the action
                }
                return false;
            }
        });

    }

    private void openMainScreen()
    {
        Intent intent = new Intent(EnterUrlActivity.this, MainActivity.class);
        intent.putExtra("url", urlEditText.getText().toString());
        startActivity(intent);
    }



}
