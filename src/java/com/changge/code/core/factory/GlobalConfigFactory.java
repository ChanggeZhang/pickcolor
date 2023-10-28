package com.changge.code.core.factory;

import com.changge.code.core.annotation.AppConfig;
import com.changge.code.core.bean.BaseBean;
import com.changge.code.core.config.GlobalConfig;
import com.changge.code.core.exception.SystemException;
import com.changge.code.core.parser.ColorParser;
import com.changge.code.utils.*;
import sun.reflect.FieldAccessor;

import java.awt.*;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class GlobalConfigFactory<T> implements ConfigFactory<T> {

    GlobalConfig globalConfig = GlobalConfig.instance();
    Class<GlobalConfig> clazz = GlobalConfig.class;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void build(Class<T> startClass, String[] args){
        String baseFile = Arrays.stream(args).filter(a -> a.startsWith("baseConfigFile=")).findFirst().map(a -> a.replace("baseConfigFile=","")).orElse("");
        AppConfig appConfig = startClass.getAnnotation(AppConfig.class);
        String baseUri = baseFile;
        if(StrUtils.isBlank(baseFile)){
            baseUri = appConfig == null ? "" : appConfig.baseConfigFile();
        }
        InputStream input = ResourceUtils.read(baseUri);
        String conf = StreamUtil.streamToString(input);
        Assert.isNotBlank(conf,"配置文件丢失：{0}",baseUri);
        String[] split = conf.split("\r\n");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (StrUtils.isNotBlank(s) && !s.startsWith("##")){
                Assert.contains(s,"=","配置文件出错,项目配置只支持【K=V】的结构，实际上:{0}，错误行数:{1}",s,i + 1);
                String[] kv = s.split("=");
                Assert.isTrue(kv.length == 2,"配置文件出错,项目配置只支持【K=V】的结构，实际上:{0}，错误行数:{1}",s,i + 1);
                Assert.isNotBlank(kv[0]);
                if(StrUtils.isNotBlank(kv[1])){
                    try {
                        Field field = FieldUtil.findField(clazz,kv[0]);
                        Method method = clazz.getMethod(StrUtils.buildSetName(field),field.getType());
                        method.invoke(globalConfig,buildValue(kv[1],field.getType()));
                    } catch (Exception e) {
                        throw new SystemException("配置初始化失败",e);
                    }
                }
            }
        }
    }

    private <P>P buildValue(String s, Class<P> type) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (Collection.class.isAssignableFrom(type)) {
            Collection<String> collection = (Collection<String>)type.getConstructor(Collection.class).newInstance(Arrays.asList(s.split("[,，]")));
            return (P)collection;
        }else if(type.isArray()){
            Collection<String> collection = (Collection<String>)type.getConstructor(Collection.class).newInstance(Arrays.asList(s.split("[,，]")));
            return (P)collection.toArray(new String[0]);
        }else if(type.equals(byte.class) || Byte.class.isAssignableFrom(type)){
            if(s.startsWith("#")){
                return type.cast(Byte.valueOf(s.substring(1),16));
            }else{
                return type.cast(Byte.valueOf(s));
            }
        }else if(type.equals(short.class) || Short.class.isAssignableFrom(type)){
            if(s.startsWith("#")){
                return type.cast(Short.valueOf(s.substring(1),16));
            }else{
                return type.cast(Short.valueOf(s));
            }
        }else if(type.equals(int.class) || Integer.class.isAssignableFrom(type)){
            if(s.startsWith("#")){
                return type.cast(Integer.valueOf(s.substring(1),16));
            }else{
                return type.cast(Integer.valueOf(s));
            }
        }else if(type.equals(long.class) || Long.class.isAssignableFrom(type)){
            if(s.startsWith("#")){
                return type.cast(Long.valueOf(s.substring(1),16));
            }else{
                return type.cast(Long.valueOf(s));
            }
        }else if(type.equals(float.class) || Float.class.isAssignableFrom(type)){
            return type.cast(Long.valueOf(s));
        }else if(type.equals(double.class) || Double.class.isAssignableFrom(type)){
            return type.cast(Double.valueOf(s));
        }else if(type.equals(boolean.class) || Boolean.class.isAssignableFrom(type)){
            return type.cast(Boolean.valueOf(s));
        }else if(type.equals(char.class) || Character.class.isAssignableFrom(type)){
            return type.cast(s.charAt(0));
        }else if(type.equals(Date.class)){
            Date d;
            try {
                d = dateFormat.parse(s);
            } catch (ParseException e) {
                try {
                    d = timeFormat.parse(s);
                } catch (ParseException ex) {
                    try {
                        d = dateTimeFormat.parse(s);
                    } catch (ParseException exc) {
                        throw new SystemException(MessageFormat.format("日期转换不支持，目前仅支持：{0}，{1}，{2}三种格式","yyyy-MM-dd","HH:mm:ss","yyyy-MM-dd HH:mm:ss"),exc);
                    }
                }
            }
            return type.cast(d);
        }else if(CharSequence.class.isAssignableFrom(type)){
            return type.getConstructor(String.class).newInstance(s);
        }else if(Color.class.isAssignableFrom(type)){
            Color color = BaseBean.getBaseBean().iteratorParser(type).parse(s);
            return (P)color;
        }else{
            throw new SystemException(MessageFormat.format("不支持的数据类型序列化:{0}",type.getName()));
        }
    }
}
