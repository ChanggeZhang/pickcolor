package com.changge.code.core.parser;

import com.changge.code.core.exception.SystemException;
import com.changge.code.utils.CollUtils;
import com.changge.code.utils.StrUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateParser implements Parser {

    private static final List<String> supportFormatter = new ArrayList<>();

    static {
        supportFormatter.add("yyyy");
        supportFormatter.add("yyyy-MM");
        supportFormatter.add("yyyy-MM-dd");
        supportFormatter.add("yyyy-MM-dd HH");
        supportFormatter.add("yyyy-MM-dd HH:mm");
        supportFormatter.add("yyyy-MM-dd HH:mm:ss");
        supportFormatter.add("yyyy-MM-dd HH:mm:ss.SSS");
        supportFormatter.add("yyyy/MM");
        supportFormatter.add("yyyy/MM/dd");
        supportFormatter.add("yyyy/MM/dd HH");
        supportFormatter.add("yyyy/MM/dd HH:mm");
        supportFormatter.add("yyyy/MM/dd HH:mm:ss");
        supportFormatter.add("yyyy/MM/dd HH:mm:ss.SSS");
        supportFormatter.add("HH");
        supportFormatter.add("HH:mm");
        supportFormatter.add("HH:mm:ss");
        supportFormatter.add("HH:mm:ss.SSS");
        supportFormatter.add("yyyyMMdd");
        supportFormatter.add("HHmmss");
        supportFormatter.add("yyyyMMddHHmmss");
    }

    @Override
    public boolean support(Class<?> type) {
        return Date.class.isAssignableFrom(type)
                || Time.class.isAssignableFrom(type)
                || Timestamp.class.isAssignableFrom(type)
                || LocalDate.class.isAssignableFrom(type)
                || LocalTime.class.isAssignableFrom(type)
                || LocalDateTime.class.isAssignableFrom(type);
    }

    @Override
    public boolean parse(String val, Object target, Field field, Method setter)
            throws InvocationTargetException, IllegalAccessException {
        Class<?> type = field.getType();
        Date date = parseDate(val);
        if(Date.class.isAssignableFrom(type)){
            setter.invoke(target,date);
        }else if(Time.class.isAssignableFrom(type)){
            setter.invoke(target,new Time(date.getTime()));
        }else if(Timestamp.class.isAssignableFrom(type)){
            setter.invoke(target,new Timestamp(date.getTime()));
        }else if(LocalDate.class.isAssignableFrom(type)){
            LocalDate localDate = LocalDate.from(date.toInstant().atZone(ZoneId.systemDefault()));
            setter.invoke(target,localDate);
        }else if(LocalTime.class.isAssignableFrom(type)){
            LocalTime localTime = LocalTime.from(date.toInstant().atZone(ZoneId.systemDefault()));
            setter.invoke(target,localTime);
        }else if(LocalDateTime.class.isAssignableFrom(type)){
            LocalDateTime localDateTime = LocalDateTime.from(date.toInstant().atZone(ZoneId.systemDefault()));
            setter.invoke(target,localDateTime);
        }else{
            throw new SystemException("当前值不能转为日期：" + val);
        }
        return true;
    }

    public static Date parseDate(String val,String... parttern) {
        if(val.length() == 10 && canBeLong(val)){
            return new Date(Long.parseLong(val) * 1000);
        }else if(val.length() == 13 && canBeLong(val)){
            return new Date(Long.parseLong(val));
        }
        List<DateFormat> dateFormatList = initialFormater(parttern);
        for (DateFormat dateFormat : dateFormatList) {
            try {
                return dateFormat.parse(val);
            } catch (ParseException e) {
            }
        }
        throw new SystemException("传入的值无法被转换成目标类型： " + val);
    }

    public static String formatDate(Date date,String parttern) {
        return new SimpleDateFormat(parttern).format(date);
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String formatTime(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    public static String formatDateTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    private static boolean canBeLong(String val) {
        try {
            Long.parseLong(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static List<DateFormat> initialFormater(String[] parttern) {
        if(parttern == null || parttern.length == 0){
            parttern = supportFormatter.toArray(new String[0]);
        }
        return Arrays.stream(parttern).filter(p -> StrUtils.isNotBlank(p)).map(p -> new SimpleDateFormat(p)).collect(Collectors.toList());
    }
}
