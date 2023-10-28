package com.changge.code.utils;

import java.util.Collection;
import java.util.Map;

public class CollUtils {

    //region Collection
    public static boolean isEmpty(Collection coll){
        return coll == null || coll.isEmpty();
    }

    public static boolean anyNull(Collection coll){
        return isEmpty(coll) || coll.stream().anyMatch(item -> item == null);
    }

    public static boolean isNull(Collection s){
        return s == null;
    }

    public static boolean isNotNull(Collection s){
        return !isNull(s);
    }

    public static boolean noneNull(Collection coll){
        return isNotEmpty(coll) && coll.stream().noneMatch(item -> item == null);
    }

    public static boolean isNotEmpty(Collection s){
        return !isEmpty(s);
    }
    //endregion

    //region Map
    public static boolean isEmpty(Map coll){
        return coll == null || coll.isEmpty();
    }

    public static boolean isNull(Map s){
        return s == null;
    }

    public static boolean isNotNull(Map s){
        return !isNull(s);
    }

    public static boolean isNotEmpty(Map s){
        return !isEmpty(s);
    }
    //endregion
}
