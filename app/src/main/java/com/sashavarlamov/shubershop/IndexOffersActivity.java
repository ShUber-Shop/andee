package com.sashavarlamov.shubershop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class IndexOffersActivity extends ActionBarActivity {
    private WebAPI api = new WebAPI();
    private String listId = null;
    private String listName = null;
    private ListView pendingOffersView = null;
    private ArrayList<Job> jobs = null;
    private IndexOffersActivity me = this;
    private ArrayList<Job> withOffers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_offers);

        listId = getIntent().getStringExtra("listId");
        listName = getIntent().getStringExtra("listName");

        setTitle("Offers for " + listName);

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index_offers, menu);
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
        jobs = api.indexJobs(listId);
        withOffers = new ArrayList<>();
        for(Job j : jobs) {
            if (j.offer > 0) {
                withOffers.add(j);
            }
        }
        String[] finArr;
        if(withOffers.size() == 0) {
            finArr = new String[1];
            finArr[0] = "No Offers Have Been Submitted Yet";
        } else {
            finArr = new String[withOffers.size()];
            int cnt = 0;
            for(Job j : withOffers) {
                finArr[cnt] = ((Double) j.offer).toString();
                cnt++;
            }
        }
        pendingOffersView = (ListView) findViewById(R.id.pending_offers_list);
        ArrayAdapter<String> lists = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_row, finArr);
        pendingOffersView.setAdapter(lists);
        pendingOffersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(me, InspectOffer.class);
                intent.putExtra("listId", listId);
                intent.putExtra("listName", listName);
                intent.putExtra("jobId", jobs.get(arg2).id);
                intent.putExtra("title", jobs.get(arg2).offer + " Delivered.");
                startActivity(intent);
                System.out.println("Clicked an item");
            }
        });
    }
}
