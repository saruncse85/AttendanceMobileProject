package arun.com.attendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import arun.com.attendancesystem.db.BatchDBHelper;

public class AddBatchActivity extends AppCompatActivity {

    int batchID;
    EditText batchNameText;
    Button btnSave;

    BatchDBHelper batchDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        batchID = getIntent().getIntExtra(ActionsActivity.NEW_BATCH_ID,0);

        setContentView(R.layout.new_batch);
        batchNameText = (EditText)findViewById(R.id.batchName);
        btnSave = (Button)findViewById(R.id.btnSaveBatch);
    }

    public void saveBatch(View view) {
        batchDBHelper = new BatchDBHelper(getApplicationContext());
        String batchName = batchNameText.getText().toString();
        batchDBHelper.insertBatch(batchName);
        Toast.makeText(getApplicationContext(), "Created New Batch "+batchName,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ActionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
