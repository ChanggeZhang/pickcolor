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

import static com.changge.code.core.parser.ColorParser.parse;

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
        BaseBean baseBean = BaseBean.getBaseBean();
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
                        baseBean.parse(globalConfig,kv[1],field,method);
                    } catch (Exception e) {
                        throw new SystemException("配置初始化失败",e);
                    }
                }
            }
        }
    }
}
