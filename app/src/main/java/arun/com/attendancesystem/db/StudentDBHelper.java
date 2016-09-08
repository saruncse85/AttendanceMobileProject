package arun.com.attendancesystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import arun.com.attendancesystem.AttendanceConstants;

/**
 * Created by asundaramoorthy on 5/11/2016.
 */
public class StudentDBHelper extends AttendanceDBHelper {

    public StudentDBHelper(Context context) {
        super(context);
    }

    public boolean insertStudent(String studentName, String batchName, String daysPerWeek, String parentName, String startDate, String endDate, String rate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STU_COL_NAME, studentName);
        contentValues.put(STU_COL_BATCH_NAME, batchName);
        contentValues.put(STU_COL_DAYS_PER_WEEK, Integer.parseInt(daysPerWeek));
        contentValues.put(STU_COL_RATE, rate);
        contentValues.put(STU_COL_PARENT, parentName);
        contentValues.put(STU_COL_START_DATE, startDate);
        contentValues.put(STU_COL_END_DATE, endDate);

        db.insert(STU_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public List<String> getAllBatchNames() {
        List<String> batchList = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ BATCH_TABLE_NAME+" WHERE DELETED_FLAG = 'N'", null);
        if(cursor.moveToFirst()) {
            do {
                batchList.add(cursor.getString(1));
            } while(cursor.moveToNext());
        }
        db.close();
        return batchList;
    }

}
