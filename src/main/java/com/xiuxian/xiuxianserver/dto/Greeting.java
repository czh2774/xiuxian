package com.xiuxian.xiuxianserver.dto;

/**
 * Greeting 数据传输对象 (DTO)，封装服务器发送的问候信息。
 */
public class Greeting {

    private String content;  // 问候消息内容

    // 无参构造函数
    public Greeting() {
    }

    // 带参构造函数
    public Greeting(String content) {
        this.content = content;
    }

    // 获取消息内容
    public String getContent() {
        return content;
    }

    // 设置消息内容
    public void setContent(String content) {
        this.content = content;
    }
}
