package com.chat.server.run;

import com.chat.server.ConstantHandler;
import com.chat.server.entity.MsgEntity;
import com.chat.server.handler.MsgHandlerDemo;
import com.chat.server.queue.BaseQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.server.run</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/21 0021 11:13</dd>
 */
public class CmdRunnableImpl extends AbstractCmdRunnable {

    public static ConstantHandler handler = new MsgHandlerDemo();
    public CmdRunnableImpl(BaseQueue<MsgEntity> baseQueue) {
        super(baseQueue);
    }

    @Override
    public void handMsg(MsgEntity msgEntity) {
        List<MsgEntity> list = new ArrayList<MsgEntity>();
        handler.handlerMsg(msgEntity,list);
        if (list != null && list.size()>0){
            for (MsgEntity msg:list) {
                System.out.println("服务器的动作指令："+msg.getCmdCode());
                msg.getChannel().writeAndFlush(msg);// 发送消息
            }
        }
        list.clear();
        list = null;
    }
}
