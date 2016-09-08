package arun.com.attendancesystem.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asundaramoorthy on 6/28/2016.
 */
public class AttendanceUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

    private static HashMap<Integer, String> monthNameMap = new HashMap<Integer, String>();

    static {
        monthNameMap.put(0,"Jan");
        monthNameMap.put(1,"Feb");
        monthNameMap.put(2,"Mar");
        monthNameMap.put(3,"Apr");
        monthNameMap.put(4,"May");
        monthNameMap.put(5,"Jun");
        monthNameMap.put(6,"Jul");
        monthNameMap.put(7,"Aug");
        monthNameMap.put(8,"Sep");
        monthNameMap.put(9,"Oct");
        monthNameMap.put(10,"Nov");
        monthNameMap.put(11,"Dec");
    }


    public static String getStartDate(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(cal.getTime());
    }

    public static String getEndDate(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }

    public static String getStartDate(String month, int year) {
        Calendar cal = Calendar.getInstance();
        int mnth = 0;
        for(Map.Entry entry : monthNameMap.entrySet()) {
            if(month.equalsIgnoreCase(entry.getValue().toString())) {

                mnth = (Integer)entry.getKey();
                break;
            }
        }
        cal.set(Calendar.MONTH, mnth);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(cal.getTime());
    }

    public static String getEndDate(String month, int year) {
        Calendar cal = Calendar.getInstance();

        int mnth = 0;
        for(Map.Entry entry : monthNameMap.entrySet()) {
            if(month.equalsIgnoreCase(entry.getValue().toString())) {
                mnth = (Integer)entry.getKey();
                break;
            }
        }
        cal.set(Calendar.MONTH, (mnth+1));
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }

    public static ArrayList<String> getMonthYearList() {
        ArrayList<String> monthYearList = new ArrayList<String>();
        Calendar today = Calendar.getInstance();
        int currentMonth = today.get(Calendar.MONTH);
        int currentYear = today.get(Calendar.YEAR);

        for(int j=currentMonth;j>=0;j--) {
            String monthYear = monthNameMap.get(j)+"-"+currentYear;
            monthYearList.add(monthYear);
        }
        for(int i =11; i>=(currentMonth-1);i--) {
            String monthYear = monthNameMap.get(i)+"-"+(currentYear-1);
            monthYearList.add(monthYear);
        }

        return monthYearList;
    }
}
