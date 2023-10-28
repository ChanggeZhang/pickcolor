package com.changge.code.core;

import com.changge.code.core.bean.BaseBean;
import com.changge.code.core.config.GlobalConfig;
import com.changge.code.core.factory.GlobalConfigFactory;
import com.changge.code.core.parser.ColorParser;
import com.changge.code.view.MainWindow;

public class Application {
    private static final BaseBean baseBean = BaseBean.getBaseBean();
    public static <T> BaseBean start(Class<T> startClass, String[] args) {
        loadAllBean(startClass);
        refreshEnv(startClass,args);
        return start();
    }

    private static <T> void loadAllBean(Class<T> startClass) {
        baseBean.registry(BaseBean.BeanContainerName.FACTORY,GlobalConfigFactory.class);
        baseBean.registry(BaseBean.BeanContainerName.PARSER, ColorParser.class);
    }

    private static BaseBean start(){
        MainWindow window = new MainWindow(GlobalConfig.instance());
        baseBean.setWindow(window);
        return baseBean;
    }

    private static <T> void refreshEnv(Class<T> startClass, String[] args){
        baseBean.iteratorConfig(startClass,args);
    }
}
