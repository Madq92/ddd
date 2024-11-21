package cc.mikaka.ddd.common.sequence;


import cc.mikaka.ddd.common.sequence.impl.DbSequenceService;
import cc.mikaka.ddd.common.sequence.impl.mysql.persistent.provider.MySqlSynchronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SequenceConfiguration {
    @Autowired
    DataSource dataSource;

    @Bean
    public SequenceService sequenceService() {
        MySqlSynchronizer mySqlSynchronizer = new MySqlSynchronizer(dataSource);
        // mySqlSynchronizer.init();
        return new DbSequenceService(mySqlSynchronizer);
    }
}