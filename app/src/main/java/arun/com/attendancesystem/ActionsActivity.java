package arun.com.attendancesystem;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import arun.com.attendancesystem.student.AddStudentActivity;

public class ActionsActivity extends AppCompatActivity {

    public final static String NEW_BATCH_ID = "NEW_BATCH_ID";
    public final static String NEW_STUDENT_ID = "NEW_STUDENT_ID";

    public final static String MA_BATCH_ID = "MA_BATCH_ID";
    public final static String MA_STUDENT_ID = "MA_STUDENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions_activity);
    }

    public void handleClickAction(View view) {
        switch(view.getId()) {
            case R.id.addBatchBtn:
                Intent batchIntent =new Intent(getApplicationContext(), AddBatchActivity.class);
                batchIntent.putExtra(NEW_BATCH_ID, 0);
                Bundle bndAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                startActivity(batchIntent, bndAnimation);
                return;
            case R.id.addStudentBtn:
                Intent studentIntent = new Intent(getApplicationContext(), AddStudentActivity.class);
                studentIntent.putExtra(NEW_STUDENT_ID, 0);
                Bundle studentAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                startActivity(studentIntent, studentAnimation);
                return;
            case R.id.markAttendanceBtn:
                Intent markAttendanceIntent = new Intent(getApplicationContext(), MarkAttendance.class);
                markAttendanceIntent.putExtra(MA_BATCH_ID, 0);
                markAttendanceIntent.putExtra(MA_STUDENT_ID, 0);
                Bundle markAttendanceAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                startActivity(markAttendanceIntent, markAttendanceAnimation);
                return;
            case R.id.attendCalViewBtn:
                Intent calendarIntent = new Intent(getApplicationContext(), CalendarView.class);
                Bundle calAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                startActivity(calendarIntent, calAnimation);
                return;
            case R.id.feeCalcBtn:
                Intent feeIntent = new Intent(getApplicationContext(), CalculateFeesActivity.class);
                Bundle feeAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                startActivity(feeIntent, feeAnimation);
                return;
        }

    }
}
