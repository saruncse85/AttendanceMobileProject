package arun.com.attendancesystem;

import android.app.Activity;
import android.content.Context;

import arun.com.attendancesystem.db.AttendanceDBHelper;

/**
 * Created by asundaramoorthy on 5/10/2016.
 */
public class InitializeApp extends Activity {

    AttendanceDBHelper dbHelper = null;

    public InitializeApp(Context context) {
        createDatabase(context);
    }

    /**
     * Method initializes database and all the tables.
     * @param context
     */
    private void createDatabase(Context context) {
        dbHelper = new AttendanceDBHelper(context);
        dbHelper.createTables();
    }

}
