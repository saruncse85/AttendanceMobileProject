package arun.com.attendancesystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by asundaramoorthy on 6/4/2016.
 */
public class MarkAttendanceHelper extends AttendanceDBHelper {

    public MarkAttendanceHelper(Context context){
        super(context);
    }

    public boolean markAttendance(String batchName, String studentName, int presentAbsent, String valueDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ATTN_COL_BATCH_NAME, batchName);
        contentValues.put(ATTN_COL_STUDENT_NAME, studentName);
        contentValues.put(ATTN_COL_PRESENT_ABSENT, presentAbsent);
        contentValues.put(ATTN_COL_VALUE_DATE, valueDate);
        contentValues.put(ATTN_COL_CREATED_ON, new Date().getTime());
        contentValues.put(ATTN_COL_CREATED_BY, "Admin");
        contentValues.put(ATTN_COL_DELETED, "N");

        db.insert(ATTN_TABLE_NAME, null, contentValues);
        return true;
    }

    public List<String> getAllStudentsForBatch(String batchName){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> studentList = new ArrayList<String>();
        Cursor studentRes = db.rawQuery("SELECT * FROM "+STU_TABLE_NAME+" WHERE batch_name = '"+batchName+"'",null);
        if(studentRes.moveToFirst()) {
            do {
                studentList.add(studentRes.getString(1));
            }while(studentRes.moveToNext());
        }

        return studentList;
    }

    public List<String> getAllBatchNames() {
        List<String> batchList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+BATCH_TABLE_NAME+" WHERE DELETED_FLAG = 'N'", null);
        if(res.moveToFirst()) {
            do {
                batchList.add(res.getString(1));
            }while(res.moveToNext());
        }

     return batchList;
    }

    public int getNoOfDaysStudentEnrolled(String batchName, String studentName, String startDate, String endDate) {
        int noOfDays=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT DAYS_PER_WEEK FROM "+STU_TABLE_NAME+" WHERE BATCH_NAME = '"+batchName+"'" +
                " AND NAME = '"+studentName+"' AND END_DATE >= '"+endDate+"'";
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()) {
            do{
                noOfDays = result.getInt(0);
            }while(result.moveToNext());
        }
        return noOfDays;
    }

    public ArrayList<Integer> getDaysStudentPresent(String batchName, String studentName, String startDate, String endDate) {
        ArrayList<Integer> dayList = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlStr = "SELECT VALUE_DATE FROM ATTN_ATTENDANCE WHERE BATCH_NAME = '"+batchName+"' AND STUDENT_NAME ='"+studentName+"' " +
                " AND VALUE_DATE BETWEEN '"+startDate+"' AND '"+endDate+"' AND PRESENT_ABSENT = 1";
        Cursor result =  db.rawQuery(sqlStr, null);

        if(result.moveToFirst()){
            do{
                String valDate = result.getString(0);
                int date = Integer.parseInt(valDate.substring(3, 5));
                dayList.add(date);
            }while(result.moveToNext());
        }
        return dayList;
    }

    public int getNoOfDaysPresent(String batchName, String studentName, ArrayList<String> dateList) {
        int noOfDaysPresent = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer strBuff = new StringBuffer();
        for(String date:dateList) {
            strBuff.append("'"+date+"',");
        }
        String dateListStr = strBuff.toString();
        dateListStr = dateListStr.substring(0, dateListStr.length()-1);
        String sqlStr = "SELECT COUNT(*) FROM ATTN_ATTENDANCE WHERE BATCH_NAME = '"+batchName+"' AND STUDENT_NAME = '"+studentName+"' AND " +
                "VALUE_DATE IN ("+dateListStr+")";
        Cursor result = db.rawQuery(sqlStr, null);
        if(result.moveToFirst()) {
            noOfDaysPresent = result.getInt(0);
        }
        return noOfDaysPresent;
    }

    public double getStudentBatchPrice(String batchName, String studentName) {
        double price=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlStr = "SELECT RATE FROM ATTN_STUDENT WHERE BATCH_NAME = '"+batchName+"' AND NAME = '"+studentName+"' ";
        Cursor result = db.rawQuery(sqlStr, null);
        if(result.moveToFirst()) {
            price = result.getDouble(0);
        }
        return price;
    }
}
