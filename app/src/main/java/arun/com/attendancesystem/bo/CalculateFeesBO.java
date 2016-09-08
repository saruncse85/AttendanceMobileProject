package arun.com.attendancesystem.bo;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import arun.com.attendancesystem.AttendanceBase;
import arun.com.attendancesystem.bean.StudentFees;
import arun.com.attendancesystem.db.MarkAttendanceHelper;
import arun.com.attendancesystem.db.StudentDBHelper;
import arun.com.attendancesystem.util.AttendanceUtil;

/**
 * Created by asundaramoorthy on 7/6/2016.
 */
public class CalculateFeesBO extends AttendanceBase{

    StudentDBHelper studentDBHelper;
    MarkAttendanceHelper markAttendanceHelper;
    Application appln;

    public CalculateFeesBO() {

    }

    public CalculateFeesBO(Application application) {
        this.appln = application;
    }

    public StudentFees calculateStudentFees(String batchName, String studentName, String monthYear) {
        StudentFees studentFees = new StudentFees();

        studentFees.setBatchName(batchName);
        studentFees.setStudentName(studentName);
        studentFees.setMonthYear(monthYear);

        //get no of classes enrolled per week
        markAttendanceHelper = new MarkAttendanceHelper(this.appln.getApplicationContext());
        String month = monthYear.substring(0,3);
        String year = monthYear.substring(4, monthYear.length());
        String startDate = AttendanceUtil.getStartDate(month, Integer.parseInt(year));
        String endDate = AttendanceUtil.getEndDate(month, Integer.parseInt(year));
        int noOfClassesEnrolled = markAttendanceHelper.getNoOfDaysStudentEnrolled(batchName, studentName, startDate, endDate);

        ArrayList<Object> objList = new ArrayList<Object>();
        objList = getTotalNoOfClasses(startDate, endDate);

        int totalNoOfClasses = (Integer)objList.get(0);
        studentFees.setNoOfDays(totalNoOfClasses);

        ArrayList<String> dateList = (ArrayList<String>)objList.get(1);
        int noOfDaysPresent = markAttendanceHelper.getNoOfDaysPresent(batchName, studentName, dateList);
        studentFees.setDaysPresent(noOfDaysPresent);
        studentFees.setDaysAbsent(totalNoOfClasses-noOfDaysPresent);
        studentFees.setNoOfClassesEnrolled(noOfClassesEnrolled);
        double price = markAttendanceHelper.getStudentBatchPrice(batchName, studentName);
        studentFees.setRate(price);
        return studentFees;
    }

    public ArrayList<Object> getTotalNoOfClasses(String startDate, String endDate){
        ArrayList<Object> objList = new ArrayList<Object>();
        int noOfClasses = 0;
        Calendar startCal = Calendar.getInstance();

        ArrayList<String> dateList = new ArrayList<String>();
        int start_month = Integer.parseInt(startDate.substring(0,2));
        int start_day = Integer.parseInt(startDate.substring(3,5));
        int start_year = Integer.parseInt(startDate.substring(6, startDate.length()));

        startCal.set(Calendar.MONTH, (start_month-1));
        startCal.set(Calendar.DAY_OF_MONTH, start_day);
        startCal.set(Calendar.YEAR, start_year);

        int end_day = Integer.parseInt(endDate.substring(3,5));

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        for(int i=start_day;i<=end_day;i++){
            startCal.set(Calendar.DAY_OF_MONTH,i);

            if(startCal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ||
                    startCal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                //dateList.add(start_month+"-"+i+"-"+start_year);
                startCal.set(Calendar.DAY_OF_MONTH, i);
                dateList.add(sdf.format(startCal.getTime()));
                noOfClasses++;
            }
        }
        objList.add(noOfClasses);
        objList.add(dateList);
        return objList;
    }

    public double calculateTotalFees(int classesEnrolled, int noOfClasses, int noOfDaysPresent) {
        double fees = 0;
        if(classesEnrolled == 2) {
            double feePerClass = (double)50/noOfClasses;
            System.out.println(feePerClass);
            fees = feePerClass * noOfDaysPresent;
            System.out.println(fees);
        } else if(classesEnrolled == 3) {
            double feePerClass = 70/noOfClasses;
            fees = feePerClass * noOfDaysPresent;
        }
        return fees;
    }

    public double calculateTotalFees(StudentFees studentFees) {
        double fees = 0;
        double feePerClass = studentFees.getRate()/studentFees.getNoOfDays();
        fees = feePerClass * studentFees.getDaysPresent();
        /*if(classesEnrolled == 2) {
            double feePerClass = (double)50/noOfClasses;
            System.out.println(feePerClass);
            fees = feePerClass * noOfDaysPresent;
            System.out.println(fees);
        } else if(classesEnrolled == 3) {
            double feePerClass = 70/noOfClasses;
            fees = feePerClass * noOfDaysPresent;
        }*/
        return fees;
    }
}
