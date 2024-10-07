package com.xiuxian.xiuxianserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.xiuxian.xiuxianserver.dto.Greeting;

/**
 * WebSocket 控制器，处理 WebSocket 消息的接收和响应。
 */
@Controller
public class WebSocketController {

    /**
     * 处理从客户端接收的消息，并将响应发送到 "/topic/greetings" 主题。
     *
     * @param name 客户端发送的名称
     * @return 返回封装的问候消息
     * @throws Exception 模拟延迟以展示异步处理
     */
    @MessageMapping("/hello")  // 处理 "/app/hello" 发送的消息
    @SendTo("/topic/greetings")  // 将响应消息广播到 "/topic/greetings"
    public Greeting greeting(String name) throws Exception {
        // 模拟延迟1秒
        Thread.sleep(1000);
        // 返回封装的问候信息
        return new Greeting("Hello, " + name + "!");
    }
}
