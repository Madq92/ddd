package cc.mikaka.ddd.service.event;

import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class EventMessageModel {
    /**
     * message_id-消息流水id
     */
    private String messageId;

    /**
     * message_type-消息类型
     */
    private BizType bizType;

    /**
     * action_type-业务类型
     */
    private ActionType actionType;

    /**
     * 数据
     */
    private Object baseEventModel;

    /**
     * 业务数据 == baseEventModel
     * 消息接受的时候使用
     */
    private String bizData;

    /**
     * state-消息状态
     */
    private String state;

    /**
     * 事件类型
     */
    private String eventMessageType;

    /**
     * 扩展信息
     */
    private Map<String, Object> extInfo = new HashMap<>();

    /**
     * 临时信息，不持久化
     */
    private Map<String, Object> tempInfo = new HashMap<>();

    /**
     * retry_times-重试次数
     */
    private Integer retryTimes;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 获取事件的消息模型(同步消息才有效)
     *
     * @return EventDataModel
     */
    public Object getBaseEventModelIfSync() {
        return this.baseEventModel;
    }
}

