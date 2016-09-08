package arun.com.attendancesystem;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AttendanceActivity extends AppCompatActivity {

    Button btnTakeAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        InitializeApp initializeApp = new InitializeApp(getApplicationContext());
        btnTakeAttendance = (Button)findViewById(R.id.takeAttendance);
        btnTakeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AttendanceActivity.this, ActionsActivity.class);
                Bundle bndAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                startActivity(intent, bndAnimation);
            }
        });
    }
}
