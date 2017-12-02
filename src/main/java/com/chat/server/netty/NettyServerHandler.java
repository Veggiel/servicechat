package com.chat.server.netty;

import com.chat.server.entity.MsgEntity;
import com.chat.server.queue.CommonQueue;
import com.chat.server.queue.LoginQueue;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.server.netty</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/21 0021 9:09</dd>
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MsgEntity> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, MsgEntity msg) throws Exception {
        if (msg == null){
            return;
        }
        msg.setChannel(ctx.channel());
        String msgStr = new String(msg.getData(),"UTF-8");
        //传过来的消息带了一个换行符和空格，这里给去掉了
        System.out.println("客户端发送的数据="+msgStr.substring(2));
//        System.out.println("length="+(new String(msg.getData()).length()));

        // int playerid = ServerCache.get(ctx.channel());

        int csCommondCode = msg.getCmdCode();
        if (csCommondCode < 100) {// 100以内暂时不用

        } else if (csCommondCode >= 100 && csCommondCode < 200) { // 100-200用于注册
            LoginQueue.getInstance().put(msg);

        } else {// 消息可能较多,可以分几个队列,这里先放一个
            CommonQueue.getInstance().put(msg);
        }
    }
}
