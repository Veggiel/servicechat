package com.chat.server;

import com.chat.server.entity.MsgEntity;

import java.util.List;

/**
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.server</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/21 0021 13:04</dd>
 */
public abstract class ConstantHandler {
    public abstract void handlerMsg(MsgEntity msgEntity, List<MsgEntity> list);
}
