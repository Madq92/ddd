package cc.mikaka.ddd.processor;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * 领域处理器组件
 */
@Component
public class BizProcessorComponent {

    /**
     * 领域能力处理器集合
     */
    private Map<String, BaseProcessor> domainProcessorList = new HashMap<>(64);

    public BaseProcessor getProcessor(String key) {
        return domainProcessorList.get(key);
    }

    public Map<String, BaseProcessor> getDomainProcessorList() {
        return domainProcessorList;
    }

    public void setDomainProcessorList(Map<String, BaseProcessor> domainProcessorList) {
        this.domainProcessorList = domainProcessorList;
    }
}
