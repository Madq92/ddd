package cc.mikaka.ddd.common.sequence.impl.mysql.persistent.provider;


import cc.mikaka.ddd.common.sequence.impl.mysql.core.exceptions.SeqException;
import cc.mikaka.ddd.common.sequence.impl.mysql.persistent.SeqSynchronizer;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * MySql支持
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020/7/1
 * @since 1.0
 */
@Slf4j
public class MySqlSynchronizer extends AbstractSqlStatementProvider implements SeqSynchronizer {

    // @formatter:off

	private final static String MYSQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "seq_name VARCHAR ( 255 ) NOT NULL," +
                "seq_partition VARCHAR ( 255 ) NOT NULL," +
                "seq_next_value BIGINT NOT NULL," +
                "seq_create_time TIMESTAMP NOT NULL," +
                "seq_update_time TIMESTAMP NULL," +
                "PRIMARY KEY ( `seq_name`, `seq_partition` ) " +
            ")ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='序列ID生成表'";

	private final static String MYSQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME";

	private final static String MYSQL_INSERT_IGNORE =
			"INSERT IGNORE INTO $TABLE_NAME" +
					"(seq_name,seq_partition,seq_next_value,seq_create_time)" +
					" VALUES (?,?,?,?)";

	private final static String MYSQL_UPDATE_VALUE =
			"UPDATE $TABLE_NAME SET seq_next_value=?,seq_update_time=? " +
					"WHERE seq_name=? AND seq_partition=? AND seq_next_value=?";

	private final static String MYSQL_SELECT_VALUE =
			"SELECT seq_next_value FROM $TABLE_NAME WHERE seq_name=? AND seq_partition=?";

    // @formatter:on

    private final String tableName = "seq_registry";

    private final DataSource dataSource;

    public MySqlSynchronizer(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    @Override
    protected Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new SeqException(e.getMessage(), e);
        }
    }

    @Override
    protected String getCreateTableSql() {
        return MYSQL_CREATE_TABLE.replace("$TABLE_NAME", tableName);
    }

    @Override
    protected String getDropTableSql() {
        return MYSQL_DROP_TABLE.replace("$TABLE_NAME", tableName);
    }

    @Override
    protected String getCreateSeqSql() {
        return MYSQL_INSERT_IGNORE.replace("$TABLE_NAME", tableName);
    }

    @Override
    protected String getSelectSeqSql() {
        return MYSQL_SELECT_VALUE.replace("$TABLE_NAME", tableName);
    }

    @Override
    protected String getUpdateSeqSql() {
        return MYSQL_UPDATE_VALUE.replace("$TABLE_NAME", tableName);
    }

}
