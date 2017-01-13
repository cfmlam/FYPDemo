package com.example.michaellam.fypdemo.Activity;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by MichaelLam on 5/1/2017.
 */

public class httpGet extends AsyncTask<String , Void ,String> {
    String server_response;

    @Override
    protected String doInBackground(String... strings) {

        URL url;
        HttpsURLConnection urlConnection = null;

        try {
            url = new URL(strings[0]);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", strings[1]);

            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpsURLConnection.HTTP_OK){
                server_response = readStream(urlConnection.getInputStream());
                //Log.v("CatalogClient", server_response);
                JSONObject obj = stringtoJSON(server_response);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //Log.e("Response", "" + server_response);


    }

    //Parsing JSON
    private JSONObject stringtoJSON(String s)
    {
        JSONObject json = null;
        try {
            json = new JSONObject(s);
            //Log.d("Result:" , json.toString());
            JSONArray inner = json.getJSONArray("activities-heart");
            //Log.d("inner", inner.toString());
            for(int i=0; i < inner.length() ; i++)
            {
                JSONObject a = inner.getJSONObject(i);

                Log.d("Content", a.toString());
            }

        }catch(Throwable tx)
        {
            Log.e("My App", "Could not parse malformed JSON: \"" + s + "\"");
        }
        return json;

    }


// Converting InputStream to String

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
