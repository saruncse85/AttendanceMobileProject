package arun.com.attendancesystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import arun.com.attendancesystem.bo.AttendanceBO;
import arun.com.attendancesystem.db.MarkAttendanceHelper;
import arun.com.attendancesystem.util.AttendanceUtil;

public class CalendarView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner batchSpinner;
    private Spinner studentSpinner;
    private MarkAttendanceHelper markAttendanceHelper;

    //add the present dates to this list
    public static ArrayList<Integer> attendanceList = new ArrayList<Integer>();
    public static int noOfDaysEnrolled = 0;
    public static String batchName = "";
    public static String studentName = "";

    private List<String> studentList;
    private ArrayAdapter<String> studentNameAdapter;

    private CalendarObj calendarObj;
    private AttendanceBO attendanceBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        calendarObj = (CalendarObj) findViewById(R.id.calendar_obj);
        calendarObj.setVisibility(View.INVISIBLE);

        batchSpinner = (Spinner)findViewById(R.id.calSpinBatch);
        studentSpinner = (Spinner)findViewById(R.id.calSpinStudent);

        markAttendanceHelper = new MarkAttendanceHelper(getApplicationContext());
        List<String> batchList = new ArrayList<String>();
        batchList.add("--Select Batch--");
        batchList.addAll(markAttendanceHelper.getAllBatchNames());


        ArrayAdapter<String> batchNameAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, batchList);
        batchNameAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        batchSpinner.setAdapter(batchNameAdapter);
        batchSpinner.setOnItemSelectedListener(this);

        studentList = new ArrayList<String>();
        studentList.add("----Select Student----");

        studentNameAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, studentList);
        studentNameAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        studentSpinner.setAdapter(studentNameAdapter);
        studentSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(view.getParentForAccessibility().equals(batchSpinner)){
            if(position > 0) {
                String batchName = parent.getSelectedItem().toString();
                ArrayList<String> studentList = new ArrayList<String>();
                studentList.add("---- Select Student ----");
                studentList.addAll(markAttendanceHelper.getAllStudentsForBatch(batchName));

                studentNameAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, studentList);
                studentNameAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                studentSpinner.setAdapter(studentNameAdapter);
            }
        } else {
            if(position > 0) {
                Spinner batchSpin = (Spinner)findViewById(R.id.calSpinBatch);
                //String batchName = batchSpin.getSelectedItem().toString();
                CalendarView.batchName = batchSpin.getSelectedItem().toString();
                CalendarView.studentName = parent.getSelectedItem().toString();
                attendanceBO = new AttendanceBO(markAttendanceHelper, calendarObj);
                Calendar today = Calendar.getInstance();
                attendanceBO.pullStudentDetails(today);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // No action take if nothing is selected
    }
}
