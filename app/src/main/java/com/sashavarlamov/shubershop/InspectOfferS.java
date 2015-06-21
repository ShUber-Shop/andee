package com.sashavarlamov.shubershop;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class InspectOfferS extends ActionBarActivity {
    private final WebAPI api = new WebAPI();
    private String listId = null;
    private String listName = null;
    private Job job = null;
    private String jobId = null;
    private String title = null;
    private Button doneBtn = null;
    private Button doBtn = null;
    private EditText et = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_offer_s);

        Intent intent = getIntent();
        listId = intent.getStringExtra("listId");
        listName = intent.getStringExtra("listName");
        jobId = intent.getStringExtra("jobId");
        title = intent.getStringExtra("title");

        doneBtn = (Button) findViewById(R.id.done_with_offer_button);
        doBtn = (Button) findViewById(R.id.do_offer_button);
        et = (EditText) findViewById(R.id.price_input);

        setTitle(title);

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inspect_offer, menu);
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

    public void loadData(){
        job = api.getJob(listId, jobId);
        if(job.accepted) {
            et.setVisibility(View.GONE);
            doneBtn.setVisibility(View.VISIBLE);
            doBtn.setVisibility(View.GONE);
        } else {
            doneBtn.setVisibility(View.GONE);
            doBtn.setVisibility(View.VISIBLE);
        }
    }

    public void doneWithOffer(View view) {
        api.finishJob(jobId, listId);
        finish();
    }

    public void doOffer(View view) {
        api.doJob(jobId, listId, 50);
        finish();
    }

    public void gotoListInspector(View view) {
        Intent intent = new Intent(this, InspectListSActivity.class);
        intent.putExtra("listId", listId);
        intent.putExtra("listName", listName);
        intent.putExtra("jobId", jobId);
        startActivity(intent);
    }
}
