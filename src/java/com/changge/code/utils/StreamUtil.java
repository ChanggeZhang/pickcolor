package com.changge.code.utils;

import com.changge.code.core.exception.SystemException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.logging.Logger;

public class StreamUtil {

    private static final Logger log = Logger.getLogger(StreamUtil.class.getName());

    private static final int DEFAULT_BUFFER_SIZE = 10*1024*1024;


    public static String streamToString(InputStream inputStream){
        return streamToString(inputStream,null);
    }


    public static String streamToString(InputStream inputStream, Charset charset){
        Assert.isNotNull(inputStream);
        Charset targetCharset = charset == null ? Charset.defaultCharset() : charset;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(inputStream,out);
        return new String(out.toByteArray(),targetCharset);
    }

    public static boolean copy(InputStream in, OutputStream out){
        return copy(in,out,DEFAULT_BUFFER_SIZE);
    }

    public static boolean copy(InputStream in, OutputStream out,int bufferSize){
        Assert.isNotNull(in);
        Assert.isNotNull(out);
        try {
            byte[] bytes = new byte[Math.max(DEFAULT_BUFFER_SIZE,bufferSize)];
            int num = 0;
            while ((num = in.read(bytes)) != -1){
                out.write(bytes,0,num);
                out.flush();
            }
            out.flush();
            return true;
        } catch (IOException e) {
            log.severe(MessageFormat.format("数据流转换失败: {0}",e.getMessage()));
            return false;
        }finally{
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static BufferedImage streamToImage(String uri) {
        InputStream stream = ResourceUtils.read(uri);
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(stream);
        } catch (IOException e) {
            throw new SystemException("系统图标读取失败",e);
        }
        return icon;
    }
}
