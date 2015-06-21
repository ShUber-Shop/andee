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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ListViewActivity extends ActionBarActivity {
    private static String listId = null;
    private static String listName = null;
    private JSONObject list = null;
    private TextView addrLabel = null;
    private ListView shoppingItemsView;
    private ArrayList<ShoppingItem> itemsArrayList = null;
    private String[] ids = null;
    private String[] names = null;
    private WebAPI api = new WebAPI();
    private final ListViewActivity me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        System.out.println("In the onCreate()");
        if(getIntent().getStringExtra("listId") != null) {
            listId = getIntent().getStringExtra("listId");
            listName = getIntent().getStringExtra("listName");
        }

        setTitle(listName);

        addrLabel = (TextView) findViewById(R.id.deliver_to_text);

        loadData();

        try {
            addrLabel.setText("Delivery to: " + list.getString("address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
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
        list = api.viewList(listId);

        ArrayList<ShoppingItem> raw = api.indexItems(listId);
        itemsArrayList = raw;
        names = new String[raw.size()];
        int current = 0;
        for(ShoppingItem si : raw) {
            names[current] = si.name;
            current++;
        }
        shoppingItemsView = (ListView) findViewById(R.id.shopping_items_list);
        ArrayAdapter<String> items = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_row, names);
        shoppingItemsView.setAdapter(items);
        shoppingItemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(me, InspectItemActivity.class);
                intent.putExtra("name", itemsArrayList.get(arg2).name);
                intent.putExtra("notes", itemsArrayList.get(arg2).notes);
                intent.putExtra("id", itemsArrayList.get(arg2).id);
                startActivity(intent);
                System.out.println("Started the activity");
            }
        });
    }

    public void gotoAddItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("listId", listId);
        intent.putExtra("listName", listName);
        startActivity(intent);
    }

    public void seeOffers(View view) {
        Intent intent = new Intent(this, IndexOffersActivity.class);
        intent.putExtra("listId", listId);
        intent.putExtra("listName", listName);
        startActivity(intent);
    }

    public void sendList(View view) {
        // TODO: Notify the user!
        JSONObject resp = api.createJob(listId);
        System.out.println(resp);
    }
}
