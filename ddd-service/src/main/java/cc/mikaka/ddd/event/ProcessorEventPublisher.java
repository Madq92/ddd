package cc.mikaka.ddd.event;

import org.springframework.stereotype.Component;

/**
 * 事件发布器
 */
@Component
public class ProcessorEventPublisher {
//    @Autowired
//    private EventMessageRepository eventMessageRepository;
//
//    @Autowired
//    private AlscDishSequenceService alscDishSequenceService;
//
//    @Autowired
//    private ThreadEventListener threadEventListener;
//
//    @Autowired
//    private MqEventListener mqEventListener;
//
//    @Autowired
//    private SyncEventListener syncEventListener;

    public void publishEvent(EventMessageModel eventData) {
//        List<EventMessageHandle> handles = EventMessageConfig.getHandle(
//                ActionType.getByCode(eventData.getActionType()),
//                BizType.getByCode(eventData.getBizType()));
//        if (CollectionUtils.isEmpty(handles)) {
//            return;
//        }
//
//        List<EventMessageHandle> asyncHandle = Lists.newArrayList();
//        for (EventMessageHandle handle : handles) {
//            if (handle instanceof AbstractSyncEventHandle) {
//                publishEvent(eventData, handle);
//            } else {
//                asyncHandle.add(handle);
//            }
//        }
//        for (EventMessageHandle handle : asyncHandle) {
//            publishEvent(eventData, handle);
//        }
    }

//    public void publishEvent(@NotNull EventMessageModel eventData, @NotNull EventMessageHandle handle) {
//        EventMessageModel fissionModel = EventMessageConvert.deepCopy(eventData);
//        fissionModel.setEventMessageType(handle.getEventMessageType().getCode());
//
//        if (handle instanceof AbstractSyncEventHandle) {
//            /*** 同步事件 ***/
//            syncEventListener.dispatcher(fissionModel, (AbstractSyncEventHandle) handle);
//        } else if (handle instanceof AbstractAsyncThreadHandle) {
//            /*** 异步事件 ***/
//            buildEventTracking(fissionModel);
//            threadEventListener.dispatcher(fissionModel, (AbstractAsyncThreadHandle) handle);
//        } else if (handle instanceof AbstractAsyncMqHandle) {
//            /*** 消息事件 ***/
//            buildEventTracking(fissionModel);
//            mqEventListener.dispatcher(fissionModel);
//        }
//    }
//
//    public void publishEvent(@NotNull EventMessageModel eventData, @NotNull EventMessageType eventMessageType) {
//        EventMessageHandle handle = EventMessageConfig
//                .getHandle(ActionType.getByCode(eventData.getActionType()),
//                        BizType.getByCode(eventData.getBizType()), eventMessageType);
//        publishEvent(eventData, handle);
//    }
//
//    private void buildEventTracking(EventMessageModel fissionModel) {
//        String msgId = alscDishSequenceService.buildAlscDishSequenceId(fissionModel.getMerchantId(),
//                SequenceIdEnum.EVENT_MESSAGE);
//        fissionModel.setMessageId(msgId);
//        fissionModel.setState(MessageStateEnum.INIT.getCode());
//        eventMessageRepository.insert(fissionModel);
//    }
}
