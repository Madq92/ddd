package cc.mikaka.ddd.common.sequence.impl.mysql.persistent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface PartitionFormatter {

    /**
     * 按年份分区
     */
    PartitionFormatter ANNUALLY = () -> LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));

    /**
     * 按月份分区
     */
    PartitionFormatter MONTHLY = () -> LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

    /**
     * 按日期分区
     */
    PartitionFormatter DAILY = () -> LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    /**
     * 不分区，一个分区
     */
    PartitionFormatter NONE = () -> "NONE";

    String get();
}
