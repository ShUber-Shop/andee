package com.sashavarlamov.shubershop;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;


public class NewListActivity extends ActionBarActivity {
    private TextView nameInput = null;
    private TextView addrInput = null;
    private WebAPI api = new WebAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        nameInput = (TextView) findViewById(R.id.list_name_input);
        addrInput = (TextView) findViewById(R.id.list_address_input);

        setTitle("Create a New List");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_list, menu);
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

    public void createList(View view) {
        JSONObject ret = api.createList(nameInput.getText().toString(), addrInput.getText().toString());
        if(ret != null && ret.has("name")) {
            //finishActivity(0);
            CDecisionView cd = (CDecisionView) getParent();
            cd.loadData();
            finish();
        } else {
            // TODO: Notify the user that we couldn't create the new list
        }
    }
}
