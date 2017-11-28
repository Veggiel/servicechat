package com.chat.client;

import com.chat.proto.DemoProto.SayHelloReq;
import com.chat.proto.DemoProto.NameCheckReq;
import com.chat.server.constants.CmdConstant;
import com.chat.server.entity.MsgEntity;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <dd>Description:类描述</dd>
 * <dd>Copyright: 版权归杭州京歌科技有限公司所有</dd>
 * <dd>Company: 杭州京歌科技有限公司</dd>
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.client</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/18 0018 13:38</dd>
 */
public class Client {
    private Channel sendChannel = null;
    private final static String HOST = "localhost";
    private final static int PORT = 9090;
//    public static final Logger log = Logger.getLogger(Client.class);

    /**
     * 建立与服务器的连接
     *  Establish a connection to the server
     * @throws InterruptedException InterruptedException
     */
    public void startEngine(){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).handler(new ClientChannelInitializer(this));
        ChannelFuture future = null;
        try {
            future = b.connect(HOST,PORT).sync();
            sendChannel = future.channel();
            if (future.isDone()){
                System.out.println("已连接到服务器-Connected to the server");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();//连接失败
        }

    }

    /**
     *  登录的时候发送名称检查
     *  Send a name check when login in
     * @param name name
     */
    public void sendSignatureVerification(String name){
        NameCheckReq req = NameCheckReq.newBuilder().setName(name).build();//给请求设置名称
        MsgEntity msgEntity = new MsgEntity();
        msgEntity.setCmdCode(CmdConstant.NAME_CHECK);
        msgEntity.setData(req.toByteArray());
        msgEntity.setStatus(0);
        sendMsg(msgEntity);
    }

    /**
     * 发送普通消息
     * @param content content
     */
    public void sendCommonMsg(String content){
        SayHelloReq req = SayHelloReq.newBuilder().setContent(content).build();
        MsgEntity msgEntity = new MsgEntity();
        msgEntity.setCmdCode(CmdConstant.SAY_HELLO);
        msgEntity.setData(req.toByteArray());
        msgEntity.setStatus(1);
        sendMsg(msgEntity);

    }
    /**
     *  发送信息给服务器
     *  Send information to the server
     * @param msgEntity msgEntity
     */
    public void sendMsg(MsgEntity msgEntity) {
        sendChannel.writeAndFlush(msgEntity);
        System.out.println("消息发送成功，命令码是:\t" + msgEntity.getCmdCode());
    }
}
