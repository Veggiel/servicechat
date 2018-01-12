package com.chat.server.cache;

import com.chat.server.constants.CmdConstant;
import com.chat.server.entity.MsgEntity;
import com.chat.server.entity.PlayerEntity;
import gnu.trove.impl.sync.TSynchronizedObjectIntMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import io.netty.channel.Channel;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.server.cache</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/23 0023 8:42</dd>
 */
public class NettyCache {
    /**
     * 这里采用Trove,更省内存和额外的使用方法,甚至有类似lambda的forEach()方法
     */
    public final static TObjectIntMap<Channel> CHANNEL_PLAYER = new TSynchronizedObjectIntMap<Channel>(new TObjectIntHashMap<Channel>());
    public final static TIntObjectHashMap<PlayerEntity> PLAYERS_MAP = new TIntObjectHashMap<PlayerEntity>();
    public final static TObjectIntHashMap<String> NAME_MAPS = new TObjectIntHashMap<String>();
    public final static Set<Channel> SERVICE_SET = new LinkedHashSet<Channel>();
    public final static Map<Channel, Channel> CLIENT_MAP = new LinkedHashMap<Channel, Channel>();
    public final static Map<String, Channel> CLIENT_LIST = new HashMap<String, Channel>();

    public static int get(Channel channel) {
        return CHANNEL_PLAYER.get(channel);
    }

    public static boolean CheckName(String name) {
        return NAME_MAPS.contains(name);
    }

    public static PlayerEntity getPlayerById(int playerId) {
        return PLAYERS_MAP.get(playerId);
    }

    public static int userId = 0;

    public static void addNewUser(String name, Channel channel){
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setCh(channel);
        playerEntity.setName(name);
        playerEntity.setPlayerId(++userId);
        CHANNEL_PLAYER.put(channel, userId);
        PLAYERS_MAP.put(playerEntity.getPlayerId(), playerEntity);
        NAME_MAPS.put(name, 1);
    }

    /**
     * 给用户发消息
     * @param msgEntity
     */
    public static void sendMsg(MsgEntity msgEntity){

    }

    /**
     * 推送登录消息
     * @param msgEntity
     */
    public static void sendServerMsg(MsgEntity msgEntity){
        msgEntity.getChannel().writeAndFlush(msgEntity);
    }
    //这是一个比较low的向指定某一个用户发消息，用户客户端之间的聊天，本demo中没有使用
    public static void sendTheUserMsg(MsgEntity msgEntity) throws UnsupportedEncodingException {
        if (msgEntity.getCmdCode() != CmdConstant.NAME_CHECK) {
            if (CLIENT_MAP != null) {
                if (CLIENT_MAP.get(msgEntity.getChannel()) != null) {
                    CLIENT_MAP.get(msgEntity.getChannel()).writeAndFlush(msgEntity);
                } else {
                    for (Channel c : SERVICE_SET) {
                        if (msgEntity.getChannel().equals(c)) {
                            String str = new String(msgEntity.getData(), "UTF-8");
                            String veg = str.replace("\n", "").substring(5, 8);
                            for (String st : CLIENT_LIST.keySet()) {
                                if (veg.equals(st)) {
                                    CLIENT_LIST.get(st).writeAndFlush(msgEntity);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    }
