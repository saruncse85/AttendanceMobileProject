package arun.com.attendancesystem.student;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import arun.com.attendancesystem.ActionsActivity;
import arun.com.attendancesystem.R;
import arun.com.attendancesystem.db.StudentDBHelper;

public class AddStudentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int studentID = 0;
    EditText studentName;
    EditText parentName;
    EditText startDate;
    EditText endDate;
    EditText rate;
    Button btnSaveStudent;
    Button btnClear;

    private DatePickerDialog startDatePicker;
    private DatePickerDialog endDatePicker;
    private SimpleDateFormat dateFormat;

    Spinner spinStuBatchName;
    Spinner spinnerDaysPerWeek;
    StudentDBHelper studentHelper;
    int daysPerWeek = 0;
    int checkedCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        studentID = getIntent().getIntExtra(ActionsActivity.NEW_STUDENT_ID, 0);
        setContentView(R.layout.activity_add_student);

        studentName = (EditText)findViewById(R.id.studentName);
        parentName = (EditText)findViewById(R.id.parentName);
        rate = (EditText)findViewById(R.id.Rate);

        spinnerDaysPerWeek = (Spinner) findViewById(R.id.spinDaysPerWeek);
        spinStuBatchName = (Spinner)findViewById(R.id.spinBatchName);
        checkedCount = 0;
        List<String> noOfDaysList = new ArrayList<String>();
        noOfDaysList.add("--Select No of days per week--");
        noOfDaysList.add("2");
        noOfDaysList.add("3");
        noOfDaysList.add("4");
        noOfDaysList.add("5");
        noOfDaysList.add("6");
        ArrayAdapter<String> noOfDaysAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, noOfDaysList);
        noOfDaysAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerDaysPerWeek.setAdapter(noOfDaysAdapter);
        spinnerDaysPerWeek.setOnItemSelectedListener(this);

        btnSaveStudent = (Button)findViewById(R.id.btnSaveStudent);
        btnClear = (Button)findViewById(R.id.btnClearContent);

        studentHelper = new StudentDBHelper(getApplicationContext());
        List<String> list = new ArrayList<String>();
        list.add("--Select Batch--");
        list.addAll(studentHelper.getAllBatchNames());

        ArrayAdapter<String> batchNameAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        batchNameAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinStuBatchName.setAdapter(batchNameAdapter);


        dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        startDate = (EditText)findViewById(R.id.startDate);
        startDate.setInputType(InputType.TYPE_NULL);

        endDate = (EditText)findViewById(R.id.endDate);
        endDate.setInputType(InputType.TYPE_NULL);


        Calendar newCalendar = Calendar.getInstance();
        startDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDate.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        endDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void saveStudent(View view) {
        String studentNameStr = studentName.getText().toString();
        String daysPerWeekStr = spinnerDaysPerWeek.getSelectedItem().toString();
        String parentNameStr = parentName.getText().toString();
        String startDateStr = startDate.getText().toString();
        String endDateStr = endDate.getText().toString();
        String rateStr = rate.getText().toString();

        String studentBatchStr = spinStuBatchName.getSelectedItem().toString();
        studentHelper.insertStudent(studentNameStr, studentBatchStr, daysPerWeekStr, parentNameStr, startDateStr, endDateStr, rateStr);
        Toast.makeText(AddStudentActivity.this, "Created Student "+studentNameStr+" successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ActionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void clearContents(View view) {
        studentName.setText("");
        parentName.setText("");
        startDate.setText("");
        endDate.setText("");
        spinnerDaysPerWeek.setSelection(0);
        spinStuBatchName.setSelection(0);
    }

    public void dayCheckboxClicked(View view) {
        CheckBox chkbox;
            switch (view.getId()) {
                case R.id.checkbox_sunday:
                    chkbox = (CheckBox)findViewById(R.id.checkbox_sunday);
                    if(chkbox.isChecked())
                        checkedCount++;
                    else if (!chkbox.isChecked())
                        checkedCount--;
                    if(checkIfChecboxCheckedMoreThanRequired(checkedCount)){
                        Toast.makeText(AddStudentActivity.this, "Please select only "+daysPerWeek+" days per week ", Toast.LENGTH_SHORT).show();
                        chkbox.setChecked(false);
                        checkedCount--;
                    }
                    break;
                case R.id.checkbox_monday:
                    chkbox = (CheckBox)findViewById(R.id.checkbox_monday);
                    if(chkbox.isChecked())
                        checkedCount++;
                    else if (!chkbox.isChecked())
                        checkedCount--;
                    if(checkIfChecboxCheckedMoreThanRequired(checkedCount)){
                        Toast.makeText(AddStudentActivity.this, "Please select only "+daysPerWeek+" days per week ", Toast.LENGTH_SHORT).show();
                        chkbox.setChecked(false);
                        checkedCount--;
                    }
                    break;
                case R.id.checkbox_tuesday:
                    chkbox = (CheckBox)findViewById(R.id.checkbox_tuesday);
                    if(chkbox.isChecked())
                        checkedCount++;
                    else if (!chkbox.isChecked())
                        checkedCount--;
                    if(checkIfChecboxCheckedMoreThanRequired(checkedCount)){
                        Toast.makeText(AddStudentActivity.this, "Please select only "+daysPerWeek+" days per week ", Toast.LENGTH_SHORT).show();
                        chkbox.setChecked(false);
                        checkedCount--;
                    }
                    break;
                case R.id.checkbox_wednesday:
                    chkbox = (CheckBox)findViewById(R.id.checkbox_wednesday);
                    if(chkbox.isChecked())
                        checkedCount++;
                    else if (!chkbox.isChecked())
                        checkedCount--;
                    if(checkIfChecboxCheckedMoreThanRequired(checkedCount)){
                        Toast.makeText(AddStudentActivity.this, "Please select only "+daysPerWeek+" days per week ", Toast.LENGTH_SHORT).show();
                        chkbox.setChecked(false);
                        checkedCount--;
                    }
                    break;
                case R.id.checkbox_thursday:
                    chkbox = (CheckBox)findViewById(R.id.checkbox_thursday);
                    if(chkbox.isChecked())
                        checkedCount++;
                    else if (!chkbox.isChecked())
                        checkedCount--;
                    if(checkIfChecboxCheckedMoreThanRequired(checkedCount)){
                        Toast.makeText(AddStudentActivity.this, "Please select only "+daysPerWeek+" days per week ", Toast.LENGTH_SHORT).show();
                        chkbox.setChecked(false);
                        checkedCount--;
                    }
                    break;
                case R.id.checkbox_friday:
                    chkbox = (CheckBox)findViewById(R.id.checkbox_friday);
                    if(chkbox.isChecked())
                        checkedCount++;
                    else if (!chkbox.isChecked())
                        checkedCount--;
                    if(checkIfChecboxCheckedMoreThanRequired(checkedCount)){
                        Toast.makeText(AddStudentActivity.this, "Please select only "+daysPerWeek+" days per week ", Toast.LENGTH_SHORT).show();
                        chkbox.setChecked(false);
                        checkedCount--;
                    }
                    break;
                case R.id.checkbox_saturday:
                    chkbox = (CheckBox)findViewById(R.id.checkbox_saturday);
                    if(chkbox.isChecked())
                        checkedCount++;
                    else if (!chkbox.isChecked())
                        checkedCount--;
                    if(checkIfChecboxCheckedMoreThanRequired(checkedCount)){
                        Toast.makeText(AddStudentActivity.this, "Please select only "+daysPerWeek+" days per week ", Toast.LENGTH_SHORT).show();
                        chkbox.setChecked(false);
                        checkedCount--;
                    }
                    break;
            }

    }

    public boolean checkIfChecboxCheckedMoreThanRequired(int checkedCount) {
        if(checkedCount > daysPerWeek) {
            return true;
        } else {
            return false;
        }
    }
    public void startDatePickerClick(View view) {
        startDatePicker.show();
    }

    public void endDatePickerClick(View view) {
        endDatePicker.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>0) {
            daysPerWeek = Integer.parseInt(spinnerDaysPerWeek.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
