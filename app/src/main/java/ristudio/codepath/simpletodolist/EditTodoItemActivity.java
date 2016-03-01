package ristudio.codepath.simpletodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditTodoItemActivity extends AppCompatActivity {

    private EditText etEditItem;
    private EditText etEditNotes;
    private DatePicker dpDeadline;
    private RadioGroup rgPriority;

    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String itemText = getIntent().getStringExtra("itemText");
        String notes = getIntent().getStringExtra("itemNotes");
        pos = getIntent().getIntExtra("itemPosition", 0);
        TaskPriority p = TaskPriority.fromValue(getIntent().getIntExtra("priority", 0));


        long millis = getIntent().getLongExtra("dateInMillis", -1);
        Calendar due = new GregorianCalendar();
        due.setTimeInMillis(millis);
        dpDeadline = (DatePicker) findViewById(R.id.dpDeadline);
        dpDeadline.updateDate(due.get(Calendar.YEAR), due.get(Calendar.MONTH), due.get(Calendar.DAY_OF_MONTH));

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(itemText);

        etEditNotes = (EditText) findViewById(R.id.etNotes);
        etEditNotes.setText(notes);


        rgPriority = (RadioGroup) findViewById(R.id.rgPriority);

        switch (p) {
            case LOW:
                rgPriority.check(R.id.radio_low);
                break;
            case MEDIUM:
                rgPriority.check(R.id.radio_med);
                break;
            case HIGH:
                rgPriority.check(R.id.radio_high);
                break;
        }

        etEditItem.requestFocus();
    }

    public void onSubmit(View v) {
        Intent intent = new Intent();

        String newItemText = etEditItem.getText().toString();
        String newItemNotes = etEditNotes.getText().toString();
        Calendar deadline = new GregorianCalendar(dpDeadline.getYear(), dpDeadline.getMonth(), dpDeadline.getDayOfMonth());
        int checked = rgPriority.getCheckedRadioButtonId();
        TaskPriority p;
        switch (checked) {
            case R.id.radio_low:
                p = TaskPriority.LOW;
                break;
            case R.id.radio_med:
                p = TaskPriority.MEDIUM;
                break;
            case R.id.radio_high:
                p = TaskPriority.HIGH;
                break;
            default:
                p = TaskPriority.LOW;
        }




        intent.putExtra("itemText", newItemText);
        intent.putExtra("itemNotes", newItemNotes);
        intent.putExtra("itemPosition", pos);
        intent.putExtra("dateInMillis", deadline.getTimeInMillis());
        intent.putExtra("priority", p.ordinal());

        setResult(RESULT_OK, intent);

        this.finish();
    }

}
