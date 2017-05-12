package com.zeyad.backbase.screens.help;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.zeyad.backbase.R;

public class HelpActivity extends AppCompatActivity {

    WebView wvHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
}
