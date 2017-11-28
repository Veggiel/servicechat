package com.chat.server.entity;

import io.netty.channel.Channel;

import java.util.Arrays;

/**
 * 后台处理逻辑的核心实体类
 */
public class MsgEntity {
	private short cmdCode;// 储存命令码
	private byte[] data;// 存放实际数据,用于protobuf解码成对应message
	private Channel channel;// 当前玩家的channel
	private int status;
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}



	public short getCmdCode() {
		return cmdCode;
	}

	public void setCmdCode(short cmdCode) {
		this.cmdCode = cmdCode;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "MsgEntity [cmdCode=" + cmdCode + ", data="
				+ Arrays.toString(data) + ", channel=" + channel + "]";
	}
	
}
