package com.chat.server.queue;

import com.chat.server.entity.MsgEntity;

/**
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.server.queue</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/21 0021 10:04</dd>
 */
public class CommonQueue extends BaseQueue<MsgEntity> {
    private static final CommonQueue INSTANCE = new CommonQueue();

    private CommonQueue() {
    }

    public static CommonQueue getInstance() {
        return INSTANCE;
    }

    public void putMsg(MsgEntity msg){
        put(msg);
    }
}
