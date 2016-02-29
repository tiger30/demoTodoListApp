package ristudio.codepath.simpletodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity {

    private ListView lvItems;
    private EditText etEditText;

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> todoItemsAdapter;

    private final int EDIT_ITEM_REQUEST_CODE = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvItems = (ListView) findViewById(R.id.lvItems);
        populateArrayItem();
        lvItems.setAdapter(todoItemsAdapter);

        setupListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE){

            String newItemText = data.getExtras().getString("newItemText");
            int itemPosition = data.getExtras().getInt("editedItemPosition", 0);
            //Toast the name to display  temporarily on screen
            Toast.makeText(this, newItemText + "was edited", Toast.LENGTH_SHORT).show();

            todoItems.set(itemPosition, newItemText);

//            todoItemsAdapter.notifyDataSetChanged();
//            writeItems();
            notifyDataChangedAndSave();
        }
    }

    public void populateArrayItem(){
        readItems(); // load items list when start
        todoItemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    public void onAddItem(View view) {
        etEditText = (EditText) findViewById(R.id.etNewItem);
        String newTodoItemText = etEditText.getText().toString();
        todoItemsAdapter.add(newTodoItemText);
        etEditText.setText("");
        writeItems(); // save items when a new list item is added
    }

    private void setupListViewListener(){
        // Get event LongClick on a list item
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);

                notifyDataChangedAndSave();
                return true;
            }
        });
        // Get event Click on a list item
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                launchEditView(position, todoItems.get(position));
            }
        });

    }

    private void launchEditView (int itemPosition, String itemText){
        Intent intentEditingItem = new Intent(TodoListActivity.this, EditTodoItemActivity.class);
        intentEditingItem.putExtra("itemPosition", itemPosition);
        intentEditingItem.putExtra("itemText", itemText);

        startActivityForResult(intentEditingItem, EDIT_ITEM_REQUEST_CODE);
    }

    private void readItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir,"todo.txt");
        try {
            todoItems = new ArrayList<>(FileUtils.readLines(file));
        } catch (IOException e){
            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir,"todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void notifyDataChangedAndSave(){
        todoItemsAdapter.notifyDataSetChanged();
        writeItems();
    }

}
