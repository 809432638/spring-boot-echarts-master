package com.alibaba.flume.util;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author zwq
 * @date 2019/11/4 18:05
 */
public class LogUtils {

    /**
     * 启动日志校验类
     *
     * @param log
     * @return
     */
    public static boolean validateEvent(String log) {

        // 1 切割
        String[] logContents = log.split("\\|");

        // length  = 2

        // 2 校验
        if (logContents.length != 2) {
            return false;
        }

        //3 校验服务器时间
        if (logContents[0].length() != 13 || !NumberUtils.isDigits(logContents[0])) {
            return false;
        }

        // 4 校验json
        if (!logContents[1].trim().startsWith("{") || !logContents[1].trim().endsWith("}")) {
            return false;
        }

        return true;
    }

    /**
     * 事件日志校验类
     *
     * @param log
     * @return
     */
    public static boolean validateStart(String log) {

        // 1. 判断数据是否为null
        if (log == null) {
            return false;
        }

        // 校验json
        if (!log.trim().startsWith("{") || !log.trim().endsWith("}")) {
            return false;
        }

        return true;
    }
}
