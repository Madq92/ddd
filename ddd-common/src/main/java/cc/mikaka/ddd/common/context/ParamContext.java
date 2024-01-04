package cc.mikaka.ddd.common.context;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

public class ParamContext {
    private ConcurrentMap<String, Object> extMap = Maps.newConcurrentMap();

    public void put(String key, Object value) {
        extMap.put(key, value);
    }
}
