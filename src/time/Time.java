/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

/**
 *
 * @author DELL
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * <h1>Time class!</h1>
 * The Time class gets the current system date and time.
 * <p>
 * <b>Note:</b>
 * 
* @author Simon Karanja
 * @version 1.0
 * @since 2014-09-31
 */
public class Time {

    private static String date = null;
    private static String time = null;
    private static TimeZone tz = null;
    private static GregorianCalendar gCalendar = null;

    public static void intialize() {
        tz = TimeZone.getTimeZone("Africa/Nairobi");
        gCalendar = new GregorianCalendar();
        gCalendar.setTimeZone(tz);
    }

    public static String getFullTime() {
        time = "" + gCalendar.get(Calendar.HOUR_OF_DAY) + ":" + gCalendar.get(Calendar.MINUTE) + ":" + gCalendar.get(Calendar.SECOND);
        return time;
    }

    public static String getFullDate() {
        date = "" + gCalendar.get(Calendar.DATE) + "-" + (gCalendar.get(Calendar.MONTH) + 1) + "-" + gCalendar.get(Calendar.YEAR);
        return date;
    }

    public static String getFullDateTime() {
        date = "" + gCalendar.get(Calendar.DATE) + "-" + (gCalendar.get(Calendar.MONTH) + 1) + "-" + gCalendar.get(Calendar.YEAR);
        time = "" + gCalendar.get(Calendar.HOUR_OF_DAY) + "." + gCalendar.get(Calendar.MINUTE) + "." + gCalendar.get(Calendar.SECOND);
        return date + "[" + time + "])";
    }

    public static String getHumanDateTime() {
        date = "" + gCalendar.get(Calendar.DATE) + "-" + (gCalendar.get(Calendar.MONTH) + 1) + "-" + gCalendar.get(Calendar.YEAR);
        time = "" + gCalendar.get(Calendar.HOUR_OF_DAY) + ":" + gCalendar.get(Calendar.MINUTE) + ":" + gCalendar.get(Calendar.SECOND);
        return "Date: " + date + "  /  " + time;
    }

    public static String getTabbedFullDateTime() {
        date = "" + gCalendar.get(Calendar.DATE) + "-" + (gCalendar.get(Calendar.MONTH) + 1) + "-" + gCalendar.get(Calendar.YEAR);
        time = "" + gCalendar.get(Calendar.HOUR_OF_DAY) + "." + gCalendar.get(Calendar.MINUTE) + "." + gCalendar.get(Calendar.SECOND);
        return date + "[" + time + "]";
    }

    /**
     * This method return the current date and time in the format yyyy/MM/dd
     * HH:mm:ss.SSS
     *
     * @return
     */
    public static String timeNow() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date date = new Date();
        return "[ " + dateFormat.format(date) + " ] \t";
    }

    /**
     * This method returns the current data and time in the format HH:mm:ss
     *
     * @return
     */
    public static String currentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
