package com.sashavarlamov.shubershop;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class InspectOffer extends ActionBarActivity {
    private String listId = null;
    private String listName = null;
    private String jobId = null;
    private Job job = null;
    private WebAPI api = new WebAPI();
    private Button acceptButton = null;
    private Button payButton = null;
    private Button reviewButton = null;
    private TextView fromText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_offer);

        Intent intent = getIntent();
        listId = intent.getStringExtra("listId");
        listName = intent.getStringExtra("listName");
        jobId = intent.getStringExtra("jobId");

        acceptButton = (Button) findViewById(R.id.accept_offer_button);
        payButton = (Button) findViewById(R.id.pay_offer_button);
        reviewButton = (Button) findViewById(R.id.review_offer_button);
        fromText = (TextView) findViewById(R.id.from_text_view);

        setTitle(intent.getStringExtra("title"));

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

    public void loadData() {
        job = api.getJob(listId, jobId);
        fromText.setText("From: " + job.shopperName);
        if(job.done) {
            acceptButton.setVisibility(View.GONE);
        } else if (job.accepted){
            acceptButton.setVisibility(View.GONE);
            reviewButton.setVisibility(View.GONE);
        } else {
            payButton.setVisibility(View.GONE);
            reviewButton.setVisibility(View.GONE);
        }
    }


    // TODO: For all of the *offer(View view) methods, notify the user of all situations
    public void acceptOffer(View view) {
        if (api.acceptJob(job.id, listId)) {
            job.accepted = true;
            acceptButton.setVisibility(View.GONE);
            payButton.setVisibility(View.VISIBLE);
            reviewButton.setVisibility(View.GONE);
        } else {

        }
    }

    public void payOffer(View view) {
        if (api.payForJob(job.id, listId)) {
            acceptButton.setVisibility(View.GONE);
            payButton.setVisibility(View.GONE);
            reviewButton.setVisibility(View.VISIBLE);
        } else {

        }
    }

    public void reviewOffer(View view) {
        if(api.reviewJob(job.id, listId)) {
            acceptButton.setVisibility(View.GONE);
            payButton.setVisibility(View.GONE);
            reviewButton.setVisibility(View.GONE);
            finish();
        } else {

        }
    }
}
