package cc.mikaka.ddd.service.event;

import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import cc.mikaka.ddd.common.util.BizUtil;
import com.google.common.collect.ArrayListMultimap;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class BizEventHandleComponent implements ApplicationContextAware {
    private final ArrayListMultimap<String, EventMessageHandle> domainEventHandleList = ArrayListMultimap.create();
    private ApplicationContext applicationContext;

    public List<EventMessageHandle> getHandle(BizType bizType, ActionType actionType) {
        return domainEventHandleList.get(BizUtil.getBizKey(bizType, actionType));
    }

    public List<EventMessageHandle> getHandle(String bizKey) {
        return domainEventHandleList.get(bizKey);
    }

    @PostConstruct
    private void init() {
        String[] eventMessageHandleNames = applicationContext.getBeanNamesForAnnotation(EventHandleable.class);
        if (ArrayUtils.isNotEmpty(eventMessageHandleNames)) {
            for (String eventMessageHandleName : eventMessageHandleNames) {
                var handle = applicationContext.getBean(eventMessageHandleName, EventMessageHandle.class);
                Assert.notNull(handle, "没有找到事件处理Handle:" + eventMessageHandleName);
                EventHandleable eventHandleable = AnnotationUtils.findAnnotation(handle.getClass(), EventHandleable.class);
                Assert.notNull(eventHandleable, "没有定义事件处理注解:" + eventMessageHandleName);
                domainEventHandleList.put(BizUtil.getBizKey(eventHandleable.bizType(), eventHandleable.actionType()), handle);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
