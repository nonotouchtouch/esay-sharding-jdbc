package com.hanming.easy.sharding.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description 日期工具类
 * @Date 17:46 2019-11-26
 * @Param
 * @return
 */
public class DateUtils {

    /**
     * 分表时间格式 年月日
     */
    public static String DATE_FORMAT_DATE_ONLY = "yyyyMMdd";
    /**
     * 分表时间格式 年月
     */
    public static String DATE_FORMAT_MONTH_ONLY = "yyyyMM";

    /**
     * 分表时间格式 年月
     */
    public static String DATE_FORMAT_DATE = "yyyy-MM-dd";


    /**
     * yyyy-MM-dd HH:mm:ss
     * 根据标准格式获取时间
     *
     * @param standardDate
     * @return
     */
    public static Date getDateByStandardStr(String standardDate) {
        String format;
        switch (standardDate.trim().length()) {
            case 10:
                format = "yyyy-MM-dd";
                break;

            default:
                format = "yyyy-MM-dd HH:mm:ss";
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        Date dateValue = null;
        try {
            dateValue = dateFormat.parse(standardDate);
        } catch (ParseException e) {
        }
        return dateValue;
    }


    /**
     * yyyy-MM-dd HH:mm:ss
     * 根据标准格式获取时间
     *
     * @param date
     * @return
     */
    public static Date getDate(Object date) {
        if (date instanceof Date) {
            return (Date)date;
        } else {
            return   DateUtils.getDateByStandardStr(date.toString());
        }
    }
    /**
     * 获取年月日 用于分表
     *
     * @param date
     * @param format
     * @return
     */
    public static String getDateStr(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 获取年月 用于分表
     *
     * @param date
     * @param format
     * @return
     */
    public static String getMonthStr(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String getMonthStr(String str) {
        String[] strs = str.split("-");
        StringBuilder builder = new StringBuilder(strs[0].substring(3, 5));
        builder.append(strs[1]);
        return builder.toString();
    }


    /**
     * 获取当前日期的后面 N天
     *
     * @param date
     * @param num
     * @return
     */

    public static Date getTodayAfterNum(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendarTimeSet(calendar);
        calendar.add(Calendar.DATE, num);
        return calendar.getTime();
    }


    /**
     * 字符串转换为日期
     *
     * @param date 日期字符串
     * @param exp  表达式（默认为 yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static Date convertDate(String date, String exp) {
        if (null == date || "".equals(date)) {
            throw new IllegalArgumentException("Invalid string date!");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null != exp && !"".equals(exp)) {
            sdf = new SimpleDateFormat(exp);
        }
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期时间转字符串
     *
     * @param date 日期
     * @param exp  表达式（默认：yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String convertDate(Date date, String exp) {
        if (null == date) {
            throw new IllegalArgumentException("Invalid date!");
        }
        if (null != exp && !"".equals(exp)) {
            SimpleDateFormat sdf = new SimpleDateFormat(exp);
            return sdf.format(date);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
    }

    /**
     * 日期时间转字符串
     *
     * @param date 日期
     * @param exp  表达式（默认：yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static Date convertDateToDate(Date date, String exp) {
        if (null == date) {
            throw new IllegalArgumentException("Invalid date!");
        }
        try {
            if (null != exp && !"".equals(exp)) {
                SimpleDateFormat sdf = new SimpleDateFormat(exp);
                return sdf.parse(sdf.format(date));
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sdf.parse(sdf.format(date));
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date!");
        }
    }

    /**
     * 当初当前的时间戳，用于日志的输出
     */
    public static String currentDate() {
        return convertDate(new Date(), "");
    }



    /**
     * 获取当前月的第一天
     *
     * @param date
     * @return
     */
    public static Date getLastMonthDateStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);

        return getMonthDayStart(calendar.getTime());
    }


    /**
     * 获取季度开始时间
     *
     * @param date
     * @return
     */
    public static Date getQuarterDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentMonth = calendar.get(Calendar.MONTH);
        //区分当前月份在第几季度
        if (currentMonth >= Calendar.JANUARY && currentMonth <= Calendar.MARCH) {
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
        } else if (currentMonth >= Calendar.APRIL && currentMonth <= Calendar.JUNE) {
            calendar.set(Calendar.MONTH, Calendar.APRIL);
        } else if (currentMonth >= Calendar.JULY && currentMonth <= Calendar.SEPTEMBER) {
            calendar.set(Calendar.MONTH, Calendar.JULY);
        } else if (currentMonth >= Calendar.OCTOBER && currentMonth <= Calendar.DECEMBER) {
            calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendarTimeSet(calendar);

        return calendar.getTime();
    }

    /**
     * 获取指定时间月的最后一日的时间
     *
     * @param date
     * @return
     */
    public static Date getMonthDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendarTimeSet(calendar);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    /**
     * 获取指定时间月份的第一日
     *
     * @param date
     * @return
     */
    public static Date getMonthDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendarTimeSet(calendar);

        return calendar.getTime();
    }

    /**
     * 获取每周的最后一日的最后时间
     *
     * @param date
     * @return
     */
    public static Date getWeekDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendarTimeSet(calendar);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    /**
     * 获取每周的第一日的开始时间
     *
     * @param date
     * @return
     */
    public static Date getWeekDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendarTimeSet(calendar);

        return calendar.getTime();
    }



    /**
     * 获取小时开始时间
     *
     * @param date
     * @return
     */
    public static Date getHourStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        timeSet(calendar);
        return calendar.getTime();
    }

    /**
     * Calendar 设置，小时、分钟、秒、毫秒设置
     *
     * @param calendar
     */
    private static void calendarTimeSet(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        timeSet(calendar);
    }

    /**
     * 设置分账 秒和毫秒
     *
     * @param calendar
     */
    private static void timeSet(Calendar calendar) {
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 时间区间判断，当且仅当startDate所表示的时间点在endDate之前，才返回true,即合法
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public static boolean timeIntervalLegal(Date startDate, Date endDate) {
        if (null == startDate) {
            return false;
        } else if (null == endDate) {
            return true;
        } else if (startDate.after(endDate)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 时间是否在区间内检查
     * 目标时间大于或等于区间左值、小于或等于区间右值时，返回true,否则返回false
     *
     * @param left   区间左值
     * @param right  区间右值
     * @param target 待比较目标时间值
     * @return
     */
    public static boolean isTimeInInterval(Date left, Date right, Date target) {
        if (null == left || null == target) {
            throw new IllegalArgumentException("The date of left, target can not be null!");
        } else if (null == right) {
            if (!target.before(left)) {
                return true;
            }
        } else {
            if (!target.before(left) && !target.after(right)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算实时生效的生效结束时间(amount 秒后生效)
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date calculateEffectiveDate(Date date, int amount) {
        if (null == date) {
            throw new IllegalArgumentException("Illegal arguments for calculate next effective end date!");
        }
        //定义日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //在修改时间的基础上向后推 amount 秒
        calendar.add(Calendar.SECOND, amount);
        return calendar.getTime();
    }

    /**
     * 判断时间是否相等
     *
     * @param s
     * @param d
     * @param pattern
     * @return
     */
    public static boolean isEquals(String s, Date d, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(d).equals(s);
    }

    /**
     * @Description: 在传入的日期上加days天
     * @param: [date, days]
     * @return: java.util.Date
     */
    public static Date mothDayAdd(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }


    public static List<String> getMonthBetweenForSharding(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        //格式化为年月
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(convertDate(minDate, DATE_FORMAT_DATE));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(convertDate(maxDate, DATE_FORMAT_DATE));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(convertDate(curr.getTime(), "yyMM"));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }


}
