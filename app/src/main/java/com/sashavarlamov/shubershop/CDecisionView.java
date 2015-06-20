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
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CDecisionView extends ActionBarActivity {
    private ListView shoppingListsView;
    private String[] ids = null;
    private String[] names = null;
    private final WebAPI api = new WebAPI();
    private CDecisionView me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdecision_view);

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cdecision_view, menu);
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

    public void newList(){
        Intent intent = new Intent(this, NewListActivity.class);
        startActivity(intent);
    }

    public void loadData(){
        HashMap<String, String> raw = api.indexLists();
        ids = new String[raw.size()];
        names = new String[raw.size()];
        int current = 0;
        for(Map.Entry<String, String> entry : raw.entrySet()) {
            ids[current] = entry.getKey();
            names[current] = entry.getValue();
            current++;
        }
        shoppingListsView = (ListView) findViewById(R.id.shopping_lists_list);
        ArrayAdapter<String> lists = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_row, names);
        shoppingListsView.setAdapter(lists);
        shoppingListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(me, ListViewActivity.class);
                intent.putExtra("listId", ids[arg2]);
                intent.putExtra("listName", names[arg2]);
                startActivity(intent);
                System.out.println("Started the activity");
            }
        });
    }
}
