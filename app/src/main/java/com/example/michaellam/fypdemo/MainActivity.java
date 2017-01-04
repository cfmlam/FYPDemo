package com.example.michaellam.fypdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView login = (WebView) findViewById(R.id.Oauth);
        String loginURL = "https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=22874V&redirect_uri=http%3A%2F%2Fwww.ust.hk%2Fcallback.php&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800";
        login.loadUrl(loginURL);

        Button gotoView = (Button) findViewById(R.id.GoToView);
        TextView data = (TextView) findViewById(R.id.DataView);

        gotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        login.setWebViewClient(new WebViewClient()
        {
            public void onPageFinished(WebView view, String url) {
                //System.out.println(login.getUrl());

                String[] resultURL = login.getUrl().split("#access_token=");
                token = "#access_token=" + resultURL[resultURL.length - 1];
                System.out.println(token);
                System.out.println("Authentication Complete");
                login.setVisibility(View.GONE);

            }
        });
    }
}
