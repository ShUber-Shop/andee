package com.sashavarlamov.shubershop;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;


public class AddItemActivity extends ActionBarActivity {
    private WebAPI api = new WebAPI();
    private String listId = null;
    private String listName = null;
    private EditText itemNameEdit = null;
    private EditText itemNotesEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        listId = getIntent().getStringExtra("listId");
        listName = getIntent().getStringExtra("listName");

        setTitle("Add an Item to " + listName);

        itemNameEdit = (EditText) findViewById(R.id.item_name_input);
        itemNotesEdit = (EditText) findViewById(R.id.item_notes_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
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

    public void addItem(View view) {
        JSONObject jo = api.addToList(listId, itemNameEdit.getText().toString(), itemNotesEdit.getText().toString());
        if(jo.has("_id")){
            finish();
        } else {
            //TODO: Notify the user that the item could not be created
        }
    }
}
