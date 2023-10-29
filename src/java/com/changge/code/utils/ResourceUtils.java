package com.changge.code.utils;

import com.changge.code.core.config.GlobalConfig;
import com.changge.code.core.enums.LocationPrefix;
import com.changge.code.core.exception.SystemException;

import java.io.*;
import java.text.MessageFormat;
import java.util.Properties;

public class ResourceUtils {

    public static InputStream read(String uri){
        InputStream inputStream = null;
        try {
            inputStream = resolve(uri);
        } catch (FileNotFoundException e) {
            throw new SystemException(MessageFormat.format("找不到目标文件:{0}",uri),e);
        }
        return inputStream;
    }

    public static Properties loadProperty(String uri){
        InputStream stream = read(uri);
        Assert.isNotNull(stream,"没有找到合适的资源：{0}",uri);
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new SystemException("配置加载失败",e);
        }
        return properties;
    }

    private static InputStream resolve(String uri) throws FileNotFoundException {
        Assert.isNotBlank(uri);
        uri = uri.trim();
        if (uri.startsWith(LocationPrefix.CLASSPATH.getPrefix())) {
            return GlobalConfig.class.getClassLoader().getResourceAsStream(uri.replace(LocationPrefix.CLASSPATH.getPrefix(),""));
        }else if(uri.startsWith(LocationPrefix.HTTP.getPrefix()) || uri.startsWith(LocationPrefix.HTTPS.getPrefix())){
            return HttpUtil.get(uri);
        }else if(isAbsolute(uri)){
            return new FileInputStream(uri);
        }
        return new FileInputStream(uri);
    }

    public static boolean isAbsolute(String uri){
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().contains("win")){
            return uri.matches("([a-zA-Z]:)?[/\\\\].*");
        }else{
            return uri.startsWith("/");
        }
    }
}
