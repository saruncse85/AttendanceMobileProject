package arun.com.attendancesystem.bo;

import android.view.View;

import java.util.Calendar;

import arun.com.attendancesystem.CalendarObj;
import arun.com.attendancesystem.CalendarView;
import arun.com.attendancesystem.db.MarkAttendanceHelper;
import arun.com.attendancesystem.util.AttendanceUtil;

/**
 * Created by asundaramoorthy on 6/29/2016.
 */
public class AttendanceBO {

    private CalendarObj calendarObj;

    private MarkAttendanceHelper markAttendanceHelper;

    public AttendanceBO() {
    }

    public AttendanceBO(MarkAttendanceHelper attendanceHelper, CalendarObj calObj) {
        this.calendarObj = calObj;
        this.markAttendanceHelper = attendanceHelper;
    }

    public void pullStudentDetails(Calendar today) {
        String startDate = AttendanceUtil.getStartDate(today.get(Calendar.MONTH));
        String endDate =  AttendanceUtil.getEndDate(today.get(Calendar.MONTH));
        // get no of days the student enrolled
        CalendarView.noOfDaysEnrolled = markAttendanceHelper.getNoOfDaysStudentEnrolled(CalendarView.batchName, CalendarView.studentName, startDate, endDate);
        // get no of days the student present

        CalendarView.attendanceList = markAttendanceHelper.getDaysStudentPresent(CalendarView.batchName, CalendarView.studentName, startDate, endDate);
        calendarObj.updateCalendar();
        calendarObj.setVisibility(View.VISIBLE);
    }

}
