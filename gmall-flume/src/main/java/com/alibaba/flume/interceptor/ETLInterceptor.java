package com.alibaba.flume.interceptor;

import com.alibaba.flume.util.LogUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zwq
 * @date 2019/11/4 16:51
 */
public class ETLInterceptor implements Interceptor {


    /**
     * 初始化方法
     */
    @Override
    public void initialize() {

    }

    /**
     * 单Event处理
     *
     * @param event
     * @return
     */
    @Override
    public Event intercept(Event event) {

        // 1 获取数据
        byte[] body = event.getBody();
        String log = new String(body, Charset.forName("UTF-8"));

        // 2 判断数据类型并向Header中赋值
        if (log.contains("start")) {
            if (LogUtils.validateStart(log)) {
                return event;
            }
        } else {
            if (LogUtils.validateEvent(log)) {
                return event;
            }
        }

        // 3 返回校验结果
        return null;
    }

    /**
     * 多Event处理
     *
     * @param events
     * @return
     */
    @Override
    public List<Event> intercept(List<Event> events) {
        // 1. 声明空容器
        ArrayList<Event> interceptors = new ArrayList<Event>();
        // 2. 循环调用单Event处理
        for (Event event : events) {
            Event resultIntercept = intercept(event);
            // 3. 如果结果不为空则加入容器中
            if (resultIntercept != null) {
                interceptors.add(resultIntercept);
            }
        }
        // 返回
        return interceptors;
    }

    /**
     * 关闭
     */
    @Override
    public void close() {

    }

    /**
     * 静态内部类构建
     */
    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new ETLInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}