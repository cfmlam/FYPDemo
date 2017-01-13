package com.example.michaellam.fypdemo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.michaellam.fypdemo.R;



public class MainActivity extends AppCompatActivity {

    String token = "";
    String userID = "";
    String scope = "";
    String tokenType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        final WebView login = (WebView) findViewById(R.id.Oauth);
        String loginURL = "https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=22874V&redirect_uri=http%3A%2F%2Fwww.ust.hk%2Fcallback.php&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800";
        login.loadUrl(loginURL);

        Button check = (Button) findViewById(R.id.tryButton);
        final TextView status = (TextView) findViewById(R.id.StatusText);

        Button checkHR = (Button) findViewById(R.id.getHR);

        checkHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V)
            {
                String[] inputS = {"https://api.fitbit.com/1/user/"+ userID +"/activities/heart/date/" + "today/1d.json", tokenType + " " + token};
                new httpGet().execute(inputS);
            }
        });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] inputS = {"https://api.fitbit.com/1/user/"+ userID +"/profile.json", tokenType + " " + token};
                new httpGet().execute(inputS);
            }
        });

        login.setWebViewClient(new WebViewClient()
        {
            public void onPageFinished(WebView view, String url) {
                //System.out.println(login.getUrl());

                String[] resultURL = login.getUrl().split("#access_token=");
                resultURL = resultURL[resultURL.length - 1].split("&user_id=");
                token = resultURL[0];
                resultURL = resultURL[resultURL.length - 1].split("&scope=");
                userID = resultURL[0];
                resultURL = resultURL[resultURL.length - 1].split("&token_type=");
                scope = resultURL[0];
                resultURL = resultURL[resultURL.length - 1].split("&expires_in=");
                tokenType = resultURL[0];
                System.out.println(token);
                System.out.println(userID);
                System.out.println(scope);
                System.out.println(tokenType);
                System.out.println("Authentication Complete");
                if (!login.getUrl().contains("fitbit")) {
                    login.setVisibility(View.GONE);
                    status.setText("Status: Success\n" + token);
                }
            }
        });
    }

    @Override
    public void onBackPressed(){

    }

}
