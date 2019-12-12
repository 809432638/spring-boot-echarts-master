package com.alibaba.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zwq
 * @date 2019/11/4 18:09
 */
public class TypeInterceptor implements Interceptor {

    /**
     * 初始化
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

        // 1 获取body数据
        byte[] body = event.getBody();
        String log = new String(body, Charset.forName("UTF-8"));

        // 2 获取header
        Map<String, String> headers = event.getHeaders();

        // 3 判断数据类型并向Header中赋值
        if (log.contains("start")) {
            headers.put("topic", "topic_start");
        } else {
            headers.put("topic", "topic_event");
        }
        // 4. 返回Event
        return event;
    }

    /**
     * 多Event处理
     *
     * @param events
     * @return
     */
    @Override
    public List<Event> intercept(List<Event> events) {
        // 1. 声明容器
        ArrayList<Event> interceptors = new ArrayList<Event>();
        // 2. 循环调用
        for (Event event : events) {
            Event resultIntercept = intercept(event);
            // 3. 将结果添加到容器内
            interceptors.add(resultIntercept);
        }
        // 4. 返回
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
            return new TypeInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
