package arun.com.attendancesystem.listeners;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import arun.com.attendancesystem.R;
import arun.com.attendancesystem.db.MarkAttendanceHelper;

/**
 * Created by asundaramoorthy on 6/5/2016.
 */
public class SpinnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Context context;
    ArrayAdapter<String> studentNameAdapter;

    public SpinnerActivity(Context appContext) {
        context = appContext;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position > 0) {
            String batchName = parent.getSelectedItem().toString();
            MarkAttendanceHelper markAttendanceHelper = new MarkAttendanceHelper(context);
            Spinner studentSpinner = (Spinner) view.findViewById(R.id.spinStuName);
            ArrayList<String> studentList = new ArrayList<String>();
            studentList.add("---- Select Student ----");
            studentList.addAll(markAttendanceHelper.getAllStudentsForBatch(batchName));

            studentNameAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, studentList);
            studentNameAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            studentSpinner.setAdapter(studentNameAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
