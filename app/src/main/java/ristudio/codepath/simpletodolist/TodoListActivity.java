package ristudio.codepath.simpletodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    private List<Task> items;
    private ArrayAdapter itemsAdapter;

    private ListView lvItems;
    private EditText etNewItem;

    private final int EDIT_ITEM_REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);

        readItems();

        itemsAdapter = new TaskListAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

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

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                notifyDataChangedAndSave();

                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchEditView(items.get(position), position);
            }
        });
    }

    private void launchEditView(Task task, int itemPosition) {
        Intent intent = new Intent(TodoListActivity.this, EditTodoItemActivity.class);
        intent.putExtra("itemText", task.getName());
        intent.putExtra("itemNotes", task.getDescription());
        intent.putExtra("dateInMillis", Calendar.getInstance().getTimeInMillis());
        intent.putExtra("itemPosition", itemPosition);
        intent.putExtra("priority", task.getPriority().ordinal());

        startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM_REQUEST_CODE) {

            Bundle bundle = data.getExtras();

            String newItemText = bundle.getString("itemText");
            String newDescriptionText = bundle.getString("itemNotes");
            int pos = bundle.getInt("itemPosition", 0);
            TaskPriority priority = TaskPriority.fromValue(bundle.getInt("priority", 0));
            Calendar deadline = new GregorianCalendar();
            deadline.setTimeInMillis(bundle.getLong("dateInMillis"));


            Task t = items.get(pos);
            t.setName(newItemText);
            t.setDescription(newDescriptionText);
            t.setPriority(priority);
            t.setDeadline(deadline);

            notifyDataChangedAndSave();
        }
    }

    public void onAddItem(View view) {
        String itemText = etNewItem.getText().toString();

        Task task = new Task(itemText, "");
        items.add(task);
        etNewItem.setText("");

        notifyDataChangedAndSave();
        // writeItems();
    }

    private void notifyDataChangedAndSave() {
        sortItemsByPriority();
        itemsAdapter.notifyDataSetChanged();
        writeItems();
    }

    private void sortItemsByPriority() {
        Collections.sort(items, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getPriority().compareTo(t2.getPriority()) * -1;
            }
        });
    }


    // Persistance
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {

            List<String> lines = new ArrayList<String>(FileUtils.readLines(todoFile));
            items = new ArrayList<Task>();

            String[] t;
            for (String line : lines) {
                t = line.split(",");
                if (t.length > 2) {
                    items.add(new Task(t[0], t[1], Long.parseLong(t[2]), Integer.parseInt(t[3])));
                } else {
                    items.add(new Task(t[0], t[1]));
                }

            }

        } catch (IOException e) {
            items = new ArrayList<Task>();
        }

    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {

            List<String> lines = new ArrayList<String>();
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
