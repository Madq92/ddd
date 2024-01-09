package cc.mikaka.ddd.service.event;

import cc.mikaka.ddd.common.error.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
public class EventResult {
    private MessageStateEnum state;

    private boolean needRetry = false;

    @Getter
    @Setter
    private ErrorCode errorCode;

    @Getter
    @Setter
    private String errorMsg;

    public static EventResult createSuccess() {
        return createSuccess(null);
    }

    public static EventResult createSuccess(String message) {
        EventResult eventResult = new EventResult();
        eventResult.state = MessageStateEnum.SUCCESS;
        eventResult.errorMsg = message;
        return eventResult;
    }

    public static EventResult createNoRetryFail(String message) {
        EventResult eventResult = new EventResult();
        eventResult.state = MessageStateEnum.FAIL;
        eventResult.errorMsg = message;
        eventResult.needRetry = false;
        return eventResult;
    }

    public static EventResult createRetryFail(String message) {
        EventResult eventResult = new EventResult();
        eventResult.state = MessageStateEnum.FAIL;
        eventResult.errorMsg = message;
        eventResult.needRetry = true;
        return eventResult;
    }

    public static EventResult createSkip(String message) {
        EventResult eventResult = new EventResult();
        eventResult.state = MessageStateEnum.SKIP;
        eventResult.errorMsg = message;
        return eventResult;
    }

    public static EventResult createSkip() {
        EventResult eventResult = new EventResult();
        eventResult.state = MessageStateEnum.SKIP;
        return eventResult;
    }

}