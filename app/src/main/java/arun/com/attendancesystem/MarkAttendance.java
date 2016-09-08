package arun.com.attendancesystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import arun.com.attendancesystem.db.MarkAttendanceHelper;

public class MarkAttendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String batchName;
    private String studentName;
    private int presentAbsent;
    private SimpleDateFormat dateFormat;

    Spinner spinBatchName;
    Spinner spinStuBatchName;
    MarkAttendanceHelper markAttendanceHelper;
    private DatePickerDialog attendanceDatePicker;
    EditText valueDate;
    RadioButton presentRadioBtn;
    RadioButton absentRadioBtn;
    RadioGroup radioGroup;

    Button saveBtn;
    Button clearBtn;
    List<String> studentList;

    ArrayAdapter<String> studentNameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        spinBatchName = (Spinner)findViewById(R.id.spinBtchName);
        spinStuBatchName = (Spinner)findViewById(R.id.spinStuName);

        valueDate = (EditText)findViewById(R.id.attendanceDate);

        saveBtn = (Button)findViewById(R.id.btnSaveAttendance);
        clearBtn = (Button) findViewById(R.id.btnClearAttendance);

        markAttendanceHelper = new MarkAttendanceHelper(getApplicationContext());
        List<String> batchList = new ArrayList<String>();
        batchList.add("--Select Batch--");
        batchList.addAll(markAttendanceHelper.getAllBatchNames());

        ArrayAdapter<String> batchNameAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, batchList);
        batchNameAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinBatchName.setAdapter(batchNameAdapter);
        spinBatchName.setOnItemSelectedListener(this);

        studentList = new ArrayList<String>();
        studentList.add("----Select Student----");
        studentNameAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, studentList);
        studentNameAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinStuBatchName.setAdapter(studentNameAdapter);

        dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        Date currentDate = new Date();
        String todayStr = dateFormat.format(currentDate);
        valueDate = (EditText)findViewById(R.id.attendanceDate);
        valueDate.setText(todayStr);
        valueDate.setInputType(InputType.TYPE_NULL);

        Calendar newCalendar = Calendar.getInstance();
        attendanceDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                valueDate.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        presentRadioBtn = (RadioButton)findViewById(R.id.radio_present);
        absentRadioBtn = (RadioButton)findViewById(R.id.radio_absent);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
    }

    public void saveAttendanceBtnClicked(View view){
        String batchName = spinBatchName.getSelectedItem().toString();
        String studentName = spinStuBatchName.getSelectedItem().toString();
        String valueDateStr = valueDate.getText().toString();
        markAttendanceHelper.markAttendance(batchName,studentName, presentAbsent, valueDateStr);

        Toast.makeText(MarkAttendance.this, "Attendance marked for "+studentName+" successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ActionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void clearAttendanceBtnClicked(View view){
        spinStuBatchName.setSelection(0);
        spinBatchName.setSelection(0);
        valueDate.setText("");
        radioGroup.clearCheck();
    }

    public void valueDatePickerClick(View view) {
        attendanceDatePicker.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position > 0) {
            String batchName = parent.getSelectedItem().toString();
            ArrayList<String> studentList = new ArrayList<String>();
            studentList.add("---- Select Student ----");
            studentList.addAll(markAttendanceHelper.getAllStudentsForBatch(batchName));

            studentNameAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, studentList);
            studentNameAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinStuBatchName.setAdapter(studentNameAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void attendanceClicked(View view) {
        if(view.getId() == R.id.radio_present) {
            presentAbsent = 1;
        } else if(view.getId() ==  R.id.radio_absent) {
            presentAbsent = 0;
        }
    }
}
