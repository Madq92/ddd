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
@EventHandleable(bizType = BizType.USER, actionType = ActionType.EDIT)
@EventHandleable(bizType = BizType.USER, actionType = ActionType.DELETE)
@EventHandleable(bizType = BizType.USER, actionType = ActionType.DISABLE)
@EventHandleable(bizType = BizType.USER, actionType = ActionType.ENABLE)
public class TrackLogHandle extends AbstractSyncEventHandle {
    @Override
    public EventResult doHandle(EventMessageModel eventMessageModel) {
        log.info("TrackLogHandle doHandle : {}", eventMessageModel);
        return EventResult.createSuccess();
    }
}
