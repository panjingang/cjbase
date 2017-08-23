package com.xcz.afcs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 核心代码就是毫秒级时间42位+机器ID10位+毫秒内序列12位
 * */

public class IdNoGenerateUtil {

    private static final Logger LOG = LoggerFactory.getLogger(AfbpProperties.class);

    private static long sequence = 0L;
    private static long twepoch = 1288834974657L;

    //12bit随机数
    private static long sequenceBits = 12L;
    private static long sequenceMask = -1L ^ (-1L << sequenceBits);

    private static long shareIdBits  = 10L;
    private static long maxShareId = -1L ^ (-1L << shareIdBits);
    private static long shardIdShift = sequenceBits;
    private static long timestampLeftShift = sequenceBits + shareIdBits;

    private static long lastTimestamp = -1L;

    public static synchronized String nextIdStr() {
        return String.valueOf(nextId());
    }

    public static synchronized long nextId() {
       return nextId(1L);
    }

    public static synchronized long nextId(long shardId) {
        if (shardId > maxShareId) {
            throw new IllegalArgumentException("业务分片编号不能大于"+maxShareId);
        }
        long timestamp = currentTimestamp();
        if (timestamp < lastTimestamp) {
            LOG.warn("系统时间修改存在产生重复ID风险...");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (shardId << shardIdShift) | sequence;
    }

    protected static long tilNextMillis(long lastTimestamp) {
        long timestamp = currentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimestamp();
        }
        return timestamp;
    }

    private static long currentTimestamp() {
        return System.currentTimeMillis();
    }

}