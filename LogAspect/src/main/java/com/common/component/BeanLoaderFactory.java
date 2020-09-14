package com.common.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.eunm.StrategyCrossEnums;

/**
 * 管理策略业务：spring启动时加载afterProperTiesSet方法
 */
@Component
public class BeanLoaderFactory implements InitializingBean {
    private Map<String, Object> contextMap = new HashMap<>();
    @Override
    public void afterPropertiesSet() throws Exception {
        //获取spring创建的bean
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        for(StrategyCrossEnums crossEnums : StrategyCrossEnums.values()){
            //获取原始目标对对象
            Object target = AopUtils.getTargetClass(applicationContext.getBean(crossEnums.getClassName()));
            contextMap.put(crossEnums.getCode(),  target);
        }
    }

    /**
     * 根据code获取业务原始目标对象
     * @param code
     * @return
     */
    public Object getStrategyMap(String code){
        Object target = contextMap.get(code);
        return target;
    }
}