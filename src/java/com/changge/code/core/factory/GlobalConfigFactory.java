package com.changge.code.core.factory;

import com.changge.code.core.annotation.AppConfig;
import com.changge.code.core.bean.BaseBean;
import com.changge.code.core.config.GlobalConfig;
import com.changge.code.core.exception.SystemException;
import com.changge.code.utils.*;

import java.awt.*;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

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
                int index = s.indexOf("=");
                Assert.isTrue(index > 1,"=","配置文件出错,项目配置只支持【K=V】的结构，实际上:{0}，错误行数:{1}",s,i + 1);
                if(index < 0 || index >= s.length()) continue;
                String key = s.substring(0,index);
                String val = s.substring(index + 1);
                if(StrUtils.isBlank(key) || StrUtils.isBlank(val)) continue;
                List<Field> fields = FieldUtil.findFields(clazz,key.trim());
                if(CollUtils.isEmpty(fields)) continue;
                for (Field field : fields) {
                    try {
                        Method method = clazz.getMethod(StrUtils.buildSetName(field),field.getType());
                        baseBean.parse(globalConfig,val.trim(),field,method);
                    } catch (Exception e) {
                        throw new SystemException("配置初始化失败",e);
                    }
                }
            }
        }
    }
}
