package cc.mikaka.ddd.service.event.sync;

import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import cc.mikaka.ddd.service.event.AbstractSyncEventHandle;
import cc.mikaka.ddd.service.event.EventHandleable;
import cc.mikaka.ddd.service.event.EventMessageModel;
import cc.mikaka.ddd.service.event.EventResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EventHandleable(bizType = BizType.USER, actionType = ActionType.CREATE)
public class UserHandle extends AbstractSyncEventHandle {

    @Override
    public EventResult doHandle(EventMessageModel eventMessageModel) {
        log.info("UserHandle doHandle : {}", eventMessageModel);
        return null;
    }
}
