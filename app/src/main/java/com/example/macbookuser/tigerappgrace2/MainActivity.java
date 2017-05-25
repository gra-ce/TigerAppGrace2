package com.example.macbookuser.tigerappgrace2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private Button submit_Button;
    private EditText editText_comment;
    public String inputText;
    private boolean checkSignedIn;
    public String timeStamp;
    public TextView commentText;
    public TextView timeStampText;
    public TextView usernameText;
    public String logInURL;
    public User tempUser;

//    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        if(fm.findFragmentByTag("UserFragment")==null){
            fm.beginTransaction().add(R.id.user_list_container, new UserFragment(), "UserFragment").commit();


        }

        wireWidgets();
        submit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSignedIn && editText_comment.getText().length()>0){
                    inputText = editText_comment.getText() + "";
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH: mm");
                    timeStamp = df.format(Calendar.getInstance().getTime());
                    commentText.setText(inputText);
                    timeStampText.setText(timeStamp);
                    commentText.setText(tempUser.getUsername());
                    /**
                     * public String baseUrl;
                     * public String username;
                     * (these would be above the constructor)
                     * baseUrl = "gmail...";
                     * commentText.setText(new SWPersonSearch().execute(baseUrl));
                     **/

                    logInURL = tempUser.getLogInURL();



                }
            }
        });



    }

    private void wireWidgets() {
        submit_Button = (Button)(findViewById(R.id.button_submit));
        editText_comment = (EditText)(findViewById(R.id.editText_comment));
        checkSignedIn=false;
        usernameText = (TextView) findViewById(R.id.textView_username);
        timeStampText = (TextView) findViewById(R.id.textView_timestamp);
        commentText = (TextView) findViewById(R.id.textView_comment);
    }

    private class SWPersonSearch extends AsyncTask<String, Void, String> {
        String jsonString = "";
        @Override
        protected String doInBackground(String... urls) {

               try {
                   URL url = new URL(urls[0]);
                   URLConnection connection = null;
                   connection = url.openConnection();
                   InputStream inputStream = connection.getInputStream();
                   String line;
                   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                   while ((line = reader.readLine()) != null) {
                       jsonString += line;

                       //return "Something";
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
            JSONObject jsonData = null;
            try {
                jsonData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonData != null) {

                /**

                username = jsonData.optJSONArray("...").optJSONObject(...).optString("...", "FAILURE");

                Log.d(TAG, jsonData.toString());

                 instead of return null it would be return username;
                 **/



            }


            return null;
        }
    }
}
