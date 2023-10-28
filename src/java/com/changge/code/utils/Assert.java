package com.changge.code.utils;

import com.changge.code.core.exception.SystemException;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

public class Assert {

    //region Object is null
    public static void isNull(Object o){
        if(o != null) throw new SystemException("传入对象不为空");
    }

    public static void isNull(Object o,String message,Object... args){
        if(o != null) throw new SystemException(MessageFormat.format(message,args));
    }

    public static void isNotNull(Object o){
        if(o == null) throw new SystemException("传入对象为空");
    }

    public static void isNotNull(Object o,String message,Object... args){
        if(o == null) throw new SystemException(MessageFormat.format(message,args));
    }
    //endregion

    //region String isBlank
    public static void isBlank(CharSequence o){
        if(StrUtils.isNotBlank(o)) throw new SystemException("传入对象不为空");
    }

    public static void isBlank(CharSequence o,String message,Object... args){
        if(StrUtils.isNotBlank(o)) throw new SystemException(MessageFormat.format(message,args));
    }

    public static void isNotBlank(CharSequence o){
        if(StrUtils.isBlank(o)) throw new SystemException("传入字符串为空");
    }

    public static void isNotBlank(CharSequence o,String message,Object... args){
        if(StrUtils.isBlank(o)) throw new SystemException(MessageFormat.format(message,args));
    }

    public static void contains(CharSequence o,CharSequence c){
        if(StrUtils.isNull(o) || StrUtils.isNull(c) || !o.toString().contains(c)) throw new SystemException(MessageFormat.format("{0}不包含{1}",o,c));
    }

    public static void notContains(CharSequence o,CharSequence c){
        if(StrUtils.isNotNull(o) && StrUtils.isNotBlank(c) && o.toString().contains(c)) throw new SystemException(MessageFormat.format("{0}包含{1}",o,c));
    }

    public static void contains(CharSequence o,CharSequence c,String message,Object... args){
        if(StrUtils.isNull(o) || StrUtils.isNull(c) || !o.toString().contains(c))  throw new SystemException(MessageFormat.format(message,args));
    }

    public static void notContains(CharSequence o,CharSequence c,String message,Object... args){
        if(StrUtils.isNotNull(o) && StrUtils.isNotBlank(c) && o.toString().contains(c))  throw new SystemException(MessageFormat.format(message,args));
    }
    //endregion

    //region String empty
    public static void isEmpty(CharSequence o){
        if(StrUtils.isNotEmpty(o)) throw new SystemException("传入字符串不为空");
    }

    public static void isEmpty(CharSequence o,String message,Object... args){
        if(StrUtils.isNotEmpty(o)) throw new SystemException(MessageFormat.format(message,args));
    }

    public static void isNotEmpty(CharSequence o){
        if(StrUtils.isEmpty(o)) throw new SystemException("传入字符串为空");
    }

    public static void isNotEmpty(CharSequence o,String message,Object... args){
        if(StrUtils.isEmpty(o)) throw new SystemException(MessageFormat.format(message,args));
    }
    //endregion

    //region LIST empty
    public static void isEmpty(Collection o){
        if(CollUtils.isNotEmpty(o)) throw new SystemException("传入集合不为空");
    }

    public static void isEmpty(Collection o,String message,Object... args){
        if(CollUtils.isNotEmpty(o)) throw new SystemException(MessageFormat.format(message,args));
    }

    public static void isNotEmpty(Collection o){
        if(CollUtils.isEmpty(o)) throw new SystemException("传入集合为空");
    }

    public static void isNotEmpty(Collection o,String message,Object... args){
        if(CollUtils.isEmpty(o)) throw new SystemException(MessageFormat.format(message,args));
    }
    //endregion

    //region MAP empty
    public static void isEmpty(Map o){
        if(CollUtils.isNotEmpty(o)) throw new SystemException("传入映射集不为空");
    }

    public static void isEmpty(Map o,String message,Object... args){
        if(CollUtils.isNotEmpty(o)) throw new SystemException(MessageFormat.format(message,args));
    }

    public static void isNotEmpty(Map o){
        if(CollUtils.isEmpty(o)) throw new SystemException("传入映射集为空");
    }

    public static void isNotEmpty(Map o,String message,Object... args){
        if(CollUtils.isEmpty(o)) throw new SystemException(MessageFormat.format(message,args));
    }
    //endregion

    //region boolean值
    public static void isTrue(boolean b) {
        if(!b) throw new SystemException("这里必须是对的");
    }
    public static void isTrue(boolean b, String message, Object... args) {
        if(!b) throw new SystemException(MessageFormat.format(message,args));
    }
    public static void isFalse(boolean b) {
        if(b) throw new SystemException("这里必须是错的");
    }
    public static void isFalse(boolean b, String message, Object... args) {
        if(b) throw new SystemException(MessageFormat.format(message,args));
    }
    //endregion
}
