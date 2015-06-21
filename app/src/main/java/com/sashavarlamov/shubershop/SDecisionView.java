package com.sashavarlamov.shubershop;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class SDecisionView extends ActionBarActivity {
    private final WebAPI api = new WebAPI();
    private String listId = null;
    private String listName = null;
    private ListView shoppersOffersView = null;
    private ArrayList<Job> jobs = null;
    private SDecisionView me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdecision_view);

        setTitle(WebAPI.firstName + " " + WebAPI.lastName);

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sdecision_view, menu);
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
        jobs = api.indexJobs(listId);
        ArrayList<String> fin = new ArrayList<String>();
        for(int i = 0; i < jobs.size(); i++) {
            if(jobs.get(i).offer > 0) {
                fin.add("You offered " + jobs.get(i).offer + " Delivered.");
            } else {
                fin.add("Make an Offer!");
            }
        }
        String[] finArr;
        if(fin.size() == 0) {
            finArr = new String[1];
            finArr[0] = "You Don't Have any Offers Yet :(";
        } else {
            finArr = new String[fin.size()];
            int cnt = 0;
            for(String s : fin) {
                finArr[cnt] = s;
                cnt++;
            }
        }
        shoppersOffersView = (ListView) findViewById(R.id.shoppers_offers_list);
        ArrayAdapter<String> lists = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_row, finArr);
        shoppersOffersView.setAdapter(lists);
        shoppersOffersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(me, InspectOfferS.class);
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
