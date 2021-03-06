package arun.com.attendancesystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

/**
 * Created by asundaramoorthy on 5/11/2016.
 */
public class BatchDBHelper extends AttendanceDBHelper {

    public BatchDBHelper(Context context) {
        super(context);
    }

    public boolean insertBatch(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BATCH_COLUMN_NAME, name);
        contentValues.put(BATCH_COLUMN_DELETED_FLAG, "N");
        contentValues.put(BATCH_COLUMN_CREATED_BY,"Admin");
        contentValues.put(BATCH_COLUMN_CREATED_ON, new Date().getTime());

        db.insert(BATCH_TABLE_NAME, null, contentValues);
        return true;
    }
}
