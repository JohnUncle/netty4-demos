package com.waylau.netty.demo.protocol;

import com.lxm.idgenerator.configuration.AutoConfiguration;
import com.lxm.idgenerator.factory.IdServiceBeanFactory;
import com.lxm.idgenerator.service.intf.IdService;

public class IdUtil {
    private static IdUtil _instance = new IdUtil();

    private static IdService idService;

    public static IdService service() {
        return idService;
    }

    private IdUtil() {
        // 使用提供的工厂类生成idService
        idService =  IdServiceBeanFactory.getService(new AutoConfiguration());
//        idService = IdServiceFactoryBean.idService(new AutoConfiguration());
    }
}

