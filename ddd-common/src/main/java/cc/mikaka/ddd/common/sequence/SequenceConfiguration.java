package cc.mikaka.ddd.common.sequence;

import cc.mikaka.ddd.common.sequence.impl.UuidSequenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SequenceConfiguration {
    @Bean
    public SequenceService sequenceService() {
        return new UuidSequenceService();
    }
}