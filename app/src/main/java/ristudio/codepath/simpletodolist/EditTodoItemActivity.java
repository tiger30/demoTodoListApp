package ristudio.codepath.simpletodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditTodoItemActivity extends AppCompatActivity {

    private int itemPosition;
    private String itemText;
    private EditText etEditItemContain;

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
        etEditItemContain.setText(itemText, TextView.BufferType.EDITABLE);
        etEditItemContain.setSelection(etEditItemContain.getText().length());

        etEditItemContain.requestFocus();
    }

    public void onSaveClick (View view) {
        etEditItemContain = (EditText)findViewById(R.id.etEditItemContain);

        Intent data = new Intent();

        data.putExtra("newItemText", etEditItemContain.getText().toString());
        data.putExtra("editedItemPosition", itemPosition);
        setResult(RESULT_OK, data);

        this.finish();
    }
}
