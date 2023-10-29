package com.changge.code.utils;

import com.changge.code.core.exception.SystemException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

    public static InputStream get(String uri){
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(36000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setReadTimeout(36000);
            conn.setDefaultUseCaches(false);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36 Edg/118.0.2088.76");
            conn.connect();
            return conn.getInputStream();
        } catch (IOException e) {
            throw new SystemException("图片加载失败",e);
        }

    }
}
