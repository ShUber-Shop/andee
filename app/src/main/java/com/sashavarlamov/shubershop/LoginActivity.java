package com.sashavarlamov.shubershop;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;


public class LoginActivity extends ActionBarActivity {
    private EditText emailText = null;
    private EditText passwordText = null;
    private WebAPI api = new WebAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.email_input);
        passwordText = (EditText) findViewById(R.id.password_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doLoginC(View view) {
        JSONObject resp = api.signinConsumer(emailText.getText().toString(), passwordText.getText().toString());
        if(resp != null && resp.has("session")) {
            Intent intent = new Intent(this, CDecisionView.class);
            startActivity(intent);
            System.out.println("Started the activity as consumer");
        } else {
            // TODO: Create a dialog to tell the user it didn't work!
        }
    }

    public void doLoginS(View view) {
        JSONObject resp = api.signinShopper(emailText.getText().toString(), passwordText.getText().toString());
        if(resp != null && resp.has("session")) {
            // TODO: Get the data from the response
            Intent intent = new Intent(this, CDecisionView.class);
            startActivity(intent);
            System.out.println("Started the activity as shopper");
        } else {
            // TODO: Create a dialog to tell the user it didn't work!
        }
    }
}
