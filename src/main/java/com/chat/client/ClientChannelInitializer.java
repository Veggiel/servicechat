package com.chat.client;

import com.chat.proto.DemoProto.SayHelloResp;
import com.chat.proto.DemoProto.NameCheckResp;
import com.chat.server.constants.CmdConstant;
import com.chat.server.entity.MsgEntity;
import com.chat.server.netty.NettyMsgDecoder;
import com.chat.server.netty.NettyMsgEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <dd>Description:类描述</dd>
 * <dd>Copyright: 版权归杭州京歌科技有限公司所有</dd>
 * <dd>Company: 杭州京歌科技有限公司</dd>
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.client</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/18 0018 13:52</dd>
 */
public class ClientChannelInitializer extends ChannelInitializer {
    private Client client;

    public ClientChannelInitializer(Client client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast("protobufDecoder",new NettyMsgDecoder())
                .addLast("protobufEncoder",new NettyMsgEncoder())
                .addLast("handler",new ClientHanlder());
    }

    class ClientHanlder extends SimpleChannelInboundHandler<MsgEntity>{
        @Override
        protected void messageReceived(ChannelHandlerContext ctx, MsgEntity msg) throws Exception {
            if (msg.getCmdCode() == CmdConstant.NAME_CHECK){
                NameCheckResp rsq = NameCheckResp.parseFrom(msg.getData());
                if (rsq.getIsExist()){
                    System.out.println("该用户已存在，请换一个用户");
                }else {
                    client.sendCommonMsg("How are you ?");
                }
            }else if(msg.getCmdCode()==CmdConstant.SAY_HELLO){
                SayHelloResp resp = SayHelloResp.parseFrom(msg.getData());
                System.out.println("speaker:"+resp.getSpeaker()+" content:"+resp.getContent());
            }
        }


    }
}
