
package com.crossper.utils;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    /**
     * Returns true if today is Saturday or Sunday else false
     * @return 
     */
    public static boolean isWeekend() {
        boolean isWeekend = false;
        Calendar c = Calendar.getInstance();
        c.setTime( new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
            isWeekend = true;
         return isWeekend;
    }
    
    /**
     * Returns true if today is weekday Monday to Friday
     * @return 
     */
    public static boolean isWeekday () {
        boolean isWeekDay = false;
        Calendar c = Calendar.getInstance();
        c.setTime( new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY)
            isWeekDay = true;
        return isWeekDay;
    }
    
    public static Date getDateBefore(Date currDate, int numOfDaysBefore) {
        Calendar calendar = getDateWithResetTime(currDate);
        calendar.add(Calendar.DAY_OF_YEAR, - numOfDaysBefore);
        Date newDate = calendar.getTime();
        return newDate;
    }
    public static Date getDateMonthBefore( Date currDate) {             
        Date lastMonthDate = getDateMonthsBefore( currDate, 1);
        return lastMonthDate;
    }
    public static Date getDateMonthsBefore( Date currDate, int numOfMonths) {
        Calendar calendar = getDateWithResetTime(currDate);
        calendar.add(Calendar.MONTH, - numOfMonths);
        Date lastMonthDate = calendar.getTime();
        return lastMonthDate;
    }
    public static Date getDateOneYearBefore(Date currDate) {
        Date lastYearDate = getDateYearsBefore( currDate, 1);
        return lastYearDate;
    }
    
     public static Date getDateYearsBefore(Date currDate, int numOfYears) {
        Calendar calendar = getDateWithResetTime(currDate);
        calendar.add(Calendar.YEAR, - numOfYears);
        Date lastYearDate = calendar.getTime();
        return lastYearDate;
    }
     /**
      * reset hrs, min sec to 0
      */
    private static Calendar getDateWithResetTime(Date dateWithTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateWithTime);
        //Reset time part
        calendar.set(Calendar.HOUR , 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }
}
