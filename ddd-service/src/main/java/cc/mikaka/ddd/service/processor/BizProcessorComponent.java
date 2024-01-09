package cc.mikaka.ddd.service.processor;

import cc.mikaka.ddd.common.util.BizUtil;
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
    /**
     * 领域能力处理器集合
     */
    private final Map<String, BaseProcessor> domainProcessorList = new HashMap<>(64);
    private ApplicationContext applicationContext;

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
                domainProcessorList.put(BizUtil.getBizKey(processable.bizType(), processable.actionType()), processor);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
