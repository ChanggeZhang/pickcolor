package com.changge.code.core.parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class PrimitiveParser implements Parser {
    @Override
    public boolean support(Class<?> type) {
        return type.isPrimitive();
    }

    @Override
    public boolean parse(String val, Object target, Field field, Method setter)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Class<?> type = field.getType();
        if(char.class.equals(type)){
            setter.invoke(target,val.charAt(0));
        }else if(Character.class.isAssignableFrom(type)){
            setter.invoke(target,Character.valueOf(val.charAt(0)));
        }else if(byte.class.equals(type)){
            setter.invoke(target,Byte.parseByte(val));
        }else if(short.class.equals(type)){
            setter.invoke(target,Short.parseShort(val));
        }else if(int.class.equals(type)){
            setter.invoke(target,Integer.parseInt(val));
        }else if(long.class.equals(type)){
            setter.invoke(target,Long.parseLong(val));
        }else if(float.class.equals(type)){
            setter.invoke(target,Float.parseFloat(val));
        }else if(double.class.equals(type)){
            setter.invoke(target,Double.parseDouble(val));
        }else if(boolean.class.equals(type)){
            setter.invoke(target,Boolean.parseBoolean(val));
        }else if(Byte.class.isAssignableFrom(type)){
            setter.invoke(target,Byte.valueOf(val));
        }else if(Short.class.isAssignableFrom(type)){
            setter.invoke(target,Short.valueOf(val));
        }else if(Integer.class.isAssignableFrom(type)){
            setter.invoke(target,Integer.valueOf(val));
        }else if(Long.class.isAssignableFrom(type)){
            setter.invoke(target,Long.valueOf(val));
        }else if(Float.class.isAssignableFrom(type)){
            setter.invoke(target,Float.valueOf(val));
        }else if(Double.class.isAssignableFrom(type)){
            setter.invoke(target,Double.valueOf(val));
        }else if(Boolean.class.isAssignableFrom(type)){
            setter.invoke(target,Boolean.valueOf(val));
        }else if(BigDecimal.class.isAssignableFrom(type)){
            setter.invoke(target,new BigDecimal(val));
        }else{
            return false;
        }
        return true;
    }
}
