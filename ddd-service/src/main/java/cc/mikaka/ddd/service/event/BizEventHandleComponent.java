package cc.mikaka.ddd.service.event;

import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import cc.mikaka.ddd.common.util.BizUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class BizEventHandleComponent implements ApplicationContextAware {
    private final ArrayListMultimap<String, EventMessageHandle> domainEventHandleList = ArrayListMultimap.create();
    private ApplicationContext applicationContext;

    public List<EventMessageHandle> getHandle(BizType bizType, ActionType actionType) {
        return this.getHandle(BizUtil.getBizKey(bizType, actionType));
    }

    private List<EventMessageHandle> getHandle(String bizKey) {
        return domainEventHandleList.get(bizKey);
    }

    @PostConstruct
    private void init() {
        String[] eventMessageHandleNames = applicationContext.getBeanNamesForAnnotation(EventHandleable.class);
        if (ArrayUtils.isNotEmpty(eventMessageHandleNames)) {
            for (String eventMessageHandleName : eventMessageHandleNames) {
                var handle = applicationContext.getBean(eventMessageHandleName, EventMessageHandle.class);
                Assert.notNull(handle, "没有找到事件处理Handle:" + eventMessageHandleName);

                if (!(handle instanceof AbstractAsyncEventHandle
                        || handle instanceof AbstractSyncEventHandle)) {
                    Assert.isTrue(false, "事件Handle应继承AbstractAsyncMqEventHandle、AbstractAsyncEventHandle、AbstractSyncEventHandle，HandlName = " + eventMessageHandleName);
                }

                List<String> bizKeyList = Lists.newArrayList();
                EventHandleables eventHandleables = AnnotationUtils.findAnnotation(handle.getClass(), EventHandleables.class);
                if (null != eventHandleables) {
                    List<String> bizKeys = Arrays.stream(eventHandleables.value())
                            .map(eventHandleable -> BizUtil.getBizKey(eventHandleable.bizType(), eventHandleable.actionType()))
                            .filter(StringUtils::isNotEmpty)
                            .toList();
                    if (!bizKeys.isEmpty()) {
                        bizKeyList.addAll(bizKeys);
                    }
                } else {
                    EventHandleable eventHandleable = AnnotationUtils.findAnnotation(handle.getClass(), EventHandleable.class);
                    bizKeyList.add(BizUtil.getBizKey(eventHandleable.bizType(), eventHandleable.actionType()));
                }
                Assert.notEmpty(bizKeyList, "没有定义事件处理注解:" + eventMessageHandleName);
                bizKeyList.forEach(bizKey -> domainEventHandleList.put(bizKey, handle));
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
