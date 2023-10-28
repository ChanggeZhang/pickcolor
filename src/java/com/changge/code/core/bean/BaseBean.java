package com.changge.code.core.bean;

import com.changge.code.core.exception.SystemException;
import com.changge.code.core.factory.ConfigFactory;
import com.changge.code.core.parser.Parser;
import com.changge.code.utils.Assert;
import com.changge.code.view.MainWindow;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Logger;

public class BaseBean {

    private static final Logger logger = Logger.getLogger(BaseBean.class.getName());


    public static BaseBean getBaseBean() {
        return Bean.ME.getBaseBean();
    }

    public enum Bean{
        ME(new BaseBean());

        private BaseBean baseBean;

        Bean(BaseBean baseBean) {
            this.baseBean = baseBean;
        }

        public BaseBean getBaseBean() {
            return baseBean;
        }
    }

    private MainWindow window;

    public MainWindow getWindow() {
        return window;
    }

    public void setWindow(MainWindow window) {
        this.window = window;
    }

    private Map<BeanContainerName,Map<Class<? extends Object>,Object>> beanMap = new HashMap<>();

    public enum BeanContainerName{
        FACTORY,
        PARSER
    }

    public void registry(BeanContainerName containerName,Class<? extends Object> factory){
        Assert.isNotNull(containerName);
        Assert.isNotNull(factory);
        if (beanMap.get(containerName) == null) {
            beanMap.put(containerName,new HashMap<>());
        }
        try {
            beanMap.get(containerName).put(factory,factory.newInstance());
        } catch (InstantiationException e) {
            throw new SystemException("配置加载失败",e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("配置加载失败",e);
        }
    }

    public <T extends Object>boolean registryBeans(BeanContainerName containerName,Class<T>... beans){
        Assert.isNotNull(containerName);
        if(beans == null || beans.length == 0 || Arrays.stream(beans).filter(f -> f == null).count() == 0L) return false;
        for (Class<T> bean : beans) {
            this.registry(containerName,bean);
        }
        return true;
    }

    public boolean unRegistryBean(BeanContainerName containerName,Object bean){
        Assert.isNotNull(containerName);
        return beanMap.get(containerName) == null ? true : beanMap.get(containerName).entrySet().removeIf(f -> f.getKey().equals(bean.getClass()));
    }

    public boolean unRegistryConfigFactory(BeanContainerName containerName,Class<? extends Object> beanClass){
        Assert.isNotNull(containerName);
        return beanMap.get(containerName) == null ? true : beanMap.get(containerName).entrySet().removeIf(f -> f.getKey().equals(beanClass));
    }

    public boolean unRegistryConfigFactory(BeanContainerName containerName,Object... factory){
        Assert.isNotNull(containerName);
        if(factory == null || factory.length == 0 || Arrays.stream(factory).filter(f -> f == null).count() == 0L) return false;
        List<Object> configFactories = new ArrayList<>(Arrays.asList(factory));
        return beanMap.get(containerName) == null ? true : beanMap.get(containerName).entrySet().removeIf(f -> configFactories.stream().map(c -> c.getClass()).anyMatch(c -> c.equals(f)));
    }

    public boolean unRegistryConfigFactory(BeanContainerName containerName,Class<? extends Object>... factory){
        Assert.isNotNull(containerName);
        if(factory == null || factory.length == 0 || Arrays.stream(factory).filter(f -> f == null).count() == 0L) return false;
        List<Class<? extends Object>> configFactories = new ArrayList<>(Arrays.asList(factory));
        return beanMap.get(containerName) == null ? true : beanMap.get(containerName).entrySet().removeIf(f -> configFactories.stream().anyMatch(c -> c.equals(f)));
    }

    public <T>void iteratorConfig(Class<T> starter,String[] args){
        for (Map.Entry<Class<? extends Object>, Object> classConfigFactoryEntry : this.beanMap.get(BeanContainerName.FACTORY).entrySet()) {
            try {
                ((ConfigFactory)classConfigFactoryEntry.getValue()).build(starter,args);
            } catch (Exception e) {
                logger.severe(MessageFormat.format("配置失败：{0}，：{1}",classConfigFactoryEntry.getKey(),e));
                throw e;
            }
        }
    }

    public Parser iteratorParser(Class type) {
        for (Map.Entry<Class<? extends Object>, Object> classConfigFactoryEntry : this.beanMap.get(BeanContainerName.PARSER).entrySet()) {
            try {
                Parser parser = ((Parser)classConfigFactoryEntry.getValue());
                if(parser.support(type)){
                    return parser;
                }
            } catch (Exception e) {
                logger.severe(MessageFormat.format("配置失败：{0}，：{1}",classConfigFactoryEntry.getKey(),e));
                throw e;
            }
        }
        throw new SystemException("无法适配合适的解析器");
    }
}
