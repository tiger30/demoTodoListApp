package ristudio.codepath.simpletodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditTodoItemActivity extends AppCompatActivity {

    int itemPosition;
    String itemText;
    EditText etEditItemContain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        itemPosition = getIntent().getIntExtra("itemPosition", 0);
        itemText = getIntent().getStringExtra("itemText");

        etEditItemContain = (EditText) findViewById(R.id.etEditItemContain);
        etEditItemContain.setText(itemText);
        etEditItemContain.setSelection(etEditItemContain.getText().length());
    }

    public void onSubmit(View view) {
        Intent data = new Intent();
        data.putExtra("editedItem", etEditItemContain.getText().toString());
        data.putExtra("itemPosition", itemPosition);

        setResult(RESULT_OK, data);

        this.finish();
    }
}
