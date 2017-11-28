package com.chat.server.entity;

import io.netty.channel.Channel;

public class PlayerEntity {
	private int playerId;
	private String name;
	private Channel ch;
	private int clientState;

	public int getClientState() {
		return clientState;
	}

	public void setClientState(int clientState) {
		this.clientState = clientState;
	}

	public Channel getCh() {
		return ch;
	}

	public void setCh(Channel ch) {
		this.ch = ch;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PlayerEntity [playerId=" + playerId + ", name=" + name
				+ ", ch=" + ch + "]";
	}

}
