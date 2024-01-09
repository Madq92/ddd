package cc.mikaka.ddd.service.processor;

import cc.mikaka.ddd.common.constants.PaaSConstants;
import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 领域处理器组件
 */
@Component
public class BizProcessorComponent implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    /**
     * 领域能力处理器集合
     */
    private final Map<String, BaseProcessor> domainProcessorList = new HashMap<>(64);

    public BaseProcessor getProcessor(String key) {
        return domainProcessorList.get(key);
    }

    @PostConstruct
    private void init() {
        String[] processorNames = applicationContext.getBeanNamesForAnnotation(Processable.class);
        if (ArrayUtils.isNotEmpty(processorNames)) {
            for (String processorName : processorNames) {
                var processor = applicationContext.getBean(processorName, BaseProcessor.class);
                Assert.notNull(processor, "没有找到业务处理器:" + processorName);
                Processable processable = AnnotationUtils.findAnnotation(processor.getClass(), Processable.class);
                Assert.notNull(processable, "没有定义业务处理注解:" + processorName);
                domainProcessorList.put(getBizKey(processable.bizType(), processable.actionType()), processor);
            }
        }
    }

    /**
     * 获得业务key
     */
    private String getBizKey(BizType bizType, ActionType actionType) {
        return bizType.getCode() + PaaSConstants.SEP_UNDERLINE + actionType.getCode();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
