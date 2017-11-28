package com.chat.server.handler;

import com.chat.proto.DemoProto;
import com.chat.proto.DemoProto.SayHelloResp;
import com.chat.server.ConstantHandler;
import com.chat.server.cache.NettyCache;
import com.chat.server.constants.CmdConstant;
import com.chat.server.entity.MsgEntity;
import com.chat.server.entity.PlayerEntity;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.Channel;

import java.util.List;

/**
 * <dd>Description:类描述</dd>
 * <dd>Copyright: 版权归杭州京歌科技有限公司所有</dd>
 * <dd>Company: 杭州京歌科技有限公司</dd>
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.server.handler</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/21 0021 13:03</dd>
 */
public class MsgHandlerDemo extends ConstantHandler {
    @Override
    public void handlerMsg(MsgEntity msgEntity, List<MsgEntity> list) {
        switch (msgEntity.getCmdCode()) {// 根据命令码对应找到对应处理方法
            case CmdConstant.NAME_CHECK:
                handleNameCheck(msgEntity, list);
                break;
            case CmdConstant.SAY_HELLO:
                handleSayHello(msgEntity, list);
                break;
            default:
                System.out.println("找不到对应的命令码");
        }
    }
    private void handleNameCheck(MsgEntity msgEntity, List<MsgEntity> list) {
        DemoProto.NameCheckReq req = null;
        try {
            req = DemoProto.NameCheckReq.parseFrom(msgEntity.getData());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return;
        }
        String name = req.getName();
        if (name==null||name.isEmpty()){
            return;
        }
        boolean isExist = NettyCache.CheckName(name);
        if (!isExist){
            NettyCache.addNewUser(name, msgEntity.getChannel());// 由于是单线程操作,无需加锁.参考:实战1的第2条
            if (msgEntity.getStatus() == 0 && !NettyCache.SERVICE_SET.contains(msgEntity.getChannel())){
                NettyCache.SERVICE_SET.add(msgEntity.getChannel());
                System.out.println(name+"登录了");
            }else if (msgEntity.getStatus()==1 && !NettyCache.SERVICE_SET.isEmpty()){
                for (Channel c: NettyCache.SERVICE_SET) { //因为是demo，所以此处只有一个客服
                    NettyCache.CLIENT_MAP.put(c,msgEntity.getChannel());
                    System.out.println(name+"登录了");
                    break;
                }
            }
        }
    DemoProto.NameCheckResp.Builder resq = DemoProto.NameCheckResp.newBuilder();
        if(!isExist){
     SayHelloResp sayHelloResp = SayHelloResp.newBuilder().setContent("欢迎"+name+"的到来").setSpeaker("系统").build();;
        MsgEntity  helloMsg = new MsgEntity();
            helloMsg.setCmdCode(CmdConstant.SAY_HELLO);
            helloMsg.setData(sayHelloResp.toByteArray());
            helloMsg.setChannel(msgEntity.getChannel());
            NettyCache.sendServerMsg(helloMsg);
        }
    }
    private void handleSayHello(MsgEntity msgEntity, List<MsgEntity> list) {
       DemoProto.SayHelloReq req = null;
        try {
            req = DemoProto.SayHelloReq.parseFrom(msgEntity.getData());
        } catch (InvalidProtocolBufferException e) {
            System.err.println("protobuf解码错误");
            e.printStackTrace();
            return;
        }
        // 关键词过滤
        // 发言频率检测
        int playerId = NettyCache.get(msgEntity.getChannel());
        PlayerEntity pe = NettyCache.getPlayerById(playerId);
        if (pe != null) {
          SayHelloResp resp = SayHelloResp.newBuilder().setContent(req.getContent()).setSpeaker("系统").build();;
            msgEntity.setData(resp.toByteArray());
            NettyCache.sendMsg(msgEntity);
        } else {
            System.err.println("玩家不存在");
        }

    }
}
