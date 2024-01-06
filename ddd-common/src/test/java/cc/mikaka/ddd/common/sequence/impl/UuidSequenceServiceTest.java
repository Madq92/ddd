package cc.mikaka.ddd.common.sequence.impl;

import cc.mikaka.ddd.common.sequence.SequenceIdEnum;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class UuidSequenceServiceTest {

    @Test
    void buildSequenceId() {
        UuidSequenceService uuidSequenceService = new UuidSequenceService();

        String uuid = uuidSequenceService.buildSequenceId(SequenceIdEnum.USER);
        log.info("uuid = {}",uuid);
    }
}