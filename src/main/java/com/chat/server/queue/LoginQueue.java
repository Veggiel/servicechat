package com.chat.server.queue;

import com.chat.server.entity.MsgEntity;

/**
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.server.queue</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/21 0021 10:14</dd>
 */
public class LoginQueue extends BaseQueue<MsgEntity>{

    private static final LoginQueue INSTANCE = new LoginQueue();

    private LoginQueue() {
    }

    public static LoginQueue getInstance() {
        return INSTANCE;
    }
}
